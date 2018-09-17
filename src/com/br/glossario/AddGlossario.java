package com.br.glossario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.glossario.async.AsyncEnviaFotoGlossario;
import com.br.glossario.interfaces.ResponseFotoListener;
import com.br.glossario.utils.FileManagement;
import com.br.glossario.utils.Session;
import com.br.glossario.utils.Utils;
import com.br.glossario.utils.UtilsDateTime;
import com.br.glossario.utils.UtilsMidia;

public class AddGlossario extends ActionBarActivity implements OnClickListener, Response.Listener<String>, Response.ErrorListener{
	
	private ImageView imgSinal;
	private EditText edtConceito;
	private EditText edtDescricao;
	private Button btnSalvar;
	private Button btnCancelar;
	private LinearLayout layoutProgress;
	
	private Session session;
	private FileManagement fileManag;
	private File glossarioFotoDir;
	private File pathFoto;
	private File f;
	private File[] fileImage;
	
	private static Uri uriFoto = null;
	private static final int ACTION_TAKE_PHOTO = 1;
	private static final int IMAGE_INTERN = 2	;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_glossario);
		
		session = new Session(getApplicationContext());		
		fileManag = new FileManagement(getApplicationContext());
		glossarioFotoDir  = fileManag.openOrCreateDirectoryExternal("glossario");	
		
		imgSinal = (ImageView) findViewById(R.id.imgSinal);
		edtConceito = (EditText) findViewById(R.id.edt_sinal_conceito);
		edtDescricao = (EditText) findViewById(R.id.edt_sinal_descricao);
		btnSalvar = (Button) findViewById(R.id.btn_sinal_add);
		btnCancelar = (Button) findViewById(R.id.btn_sinal_cancelar);
		layoutProgress = (LinearLayout) findViewById(R.id.layout_progress_foto);
		
		btnSalvar.setOnClickListener(this);
		btnCancelar.setOnClickListener(this);		
	}
	
	@Override
	protected void onStart() {
			
		
		if (glossarioFotoDir.listFiles().length == 1) {

			fileImage = glossarioFotoDir.listFiles();
			f = fileImage[0];			
			
			Bitmap Image = UtilsMidia.decodeSampledBitmapFromResource(
					glossarioFotoDir.getPath() + "/" + f.getName(), 200, 200);
			imgSinal.setImageBitmap(Image);
		}
		
		super.onStart();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_glossario, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_foto) {
			
			if (glossarioFotoDir.listFiles().length > 0) {
				fileImage = glossarioFotoDir.listFiles();
				
				for(int i=0; i < fileImage.length; i++){
					fileImage[i].delete();
				}
			}
			
			System.out.println("Size Dir: " + glossarioFotoDir.length());
			
			String dataHora = UtilsDateTime.sendFormatDate(String.valueOf(System.currentTimeMillis()));
			
			String imageFileName =  session.getKey("ID_SINAL", "") + "_" + dataHora + ".png";	
			
			pathFoto = new File(glossarioFotoDir.getPath(), imageFileName);					
			uriFoto = Uri.fromFile(pathFoto);
			
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
			startActivityForResult(cameraIntent, ACTION_TAKE_PHOTO);						
		
			return true;
		}
		
		if (id == R.id.action_galeria) {
			

			if (glossarioFotoDir.listFiles().length > 0) {
				fileImage = glossarioFotoDir.listFiles();
				
				for(int i=0; i < fileImage.length; i++){
					fileImage[i].delete();
				}
			}
			
			System.out.println("Size Dir: " + glossarioFotoDir.length());
			
			Intent intentGalery = new Intent(Intent.ACTION_GET_CONTENT);
			intentGalery.setType("image/*");
			startActivityForResult(intentGalery, IMAGE_INTERN);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			
		if (requestCode == IMAGE_INTERN) {
			if (resultCode == RESULT_OK) {
				
				Uri imgSelecionada = data.getData();
				String[] colunas = {MediaStore.Images.Media.DATA};
				
				Cursor cursor = getContentResolver().query(imgSelecionada, colunas, null, null, null);
				cursor.moveToFirst();
				
				int indexColuna = cursor.getColumnIndex(colunas[0]);
				String pathImage = cursor.getString(indexColuna);
				cursor.close();
				
				Bitmap bitmap = BitmapFactory.decodeFile(pathImage);
				imgSinal.setImageBitmap(bitmap);				
			}
		}
		
		if (requestCode == ACTION_TAKE_PHOTO) {
			if (resultCode == RESULT_OK) {
				
				if (data != null) {

					getContentResolver().notifyChange(data.getData(), null);
					ContentResolver cr = getContentResolver();

					Bitmap photo = null;
					try {
						
						photo = android.provider.MediaStore.Images.Media
								.getBitmap(cr, data.getData());
						
						imgSinal.setImageBitmap(photo);
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (resultCode == RESULT_CANCELED) {	}				
		}		
	}

	@Override
	public void onClick(View v) {
		
		if (v == btnSalvar) {
			
			if (validaCampos()) {
				mostraVazio();
			
			} else{
				if(Utils.isNetworkAvailable(getApplicationContext())){
					
					session.setKey("NOMEFOTO",f.getName());
										
					AsyncEnviaFotoGlossario asyncEnviaFoto = new AsyncEnviaFotoGlossario(getApplicationContext());			
					asyncEnviaFoto.setOnClickedListener(new ResponseFotoListener() {
						
						@Override
						public <T> T onResponseSendPhoto() {
							
							if (glossarioFotoDir.listFiles().length > 0) {
								fileImage = glossarioFotoDir.listFiles();
								
								for(int i=0; i < fileImage.length; i++){
									fileImage[i].delete();
								}
							}
							
							String url = " ";
							try {
								url = "http://glossariomobile.besaba.com/glossario/inserir.php"
										+ "?idconfigmao=" + URLEncoder.encode(session.getKey("ID_SINAL", " "), "UTF-8") 
										+ "&conceito=" + URLEncoder.encode(edtConceito.getText().toString(), "UTF-8")
										+ "&descricao=" + URLEncoder.encode(edtDescricao.getText().toString(), "UTF-8")
										+ "&imagem=" + URLEncoder.encode(session.getKey("NOMEFOTO", " "), "UTF-8")
										+ "&idusuario=" + URLEncoder.encode(session.getKey("ID_USER", " "), "UTF-8");
								
								RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
								StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, AddGlossario.this, AddGlossario.this);
								queue.add(stringObjRequest);	
								
							} catch (UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
							
							return null;
						}
					});
					
					layoutProgress.setVisibility(View.VISIBLE);
					asyncEnviaFoto.execute();
					
				} else{
					Toast.makeText(getApplicationContext(), "Sem sinal de internet.", Toast.LENGTH_LONG).show();
				}
			}			
		}
		
		if (v == btnCancelar) {
			finish();
		}		
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		layoutProgress.setVisibility(View.INVISIBLE);		
	}

	@Override
	public void onResponse(String response) {
		
		Toast.makeText(getApplicationContext(), "Enviado como sucesso.", Toast.LENGTH_SHORT).show();	
		layoutProgress.setVisibility(View.INVISIBLE);
		finish();
	}
	
	private boolean validaCampos(){
		
		if(edtConceito.getText().toString().equals("") ||
				edtDescricao.getText().toString().equals("")){
			return true;
			
		} else{
			return false;
		}
	}
	
	private void mostraVazio(){
		
		if(edtConceito.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "Preencha campo Login.", Toast.LENGTH_SHORT).show();
			edtConceito.requestFocus();
			
		} else { 
			Toast.makeText(getApplicationContext(), "Preencha campo Senha.", Toast.LENGTH_SHORT).show();
			edtDescricao.requestFocus();
		}	
	}	

	public void copiaImagem(String pathImagem){
		
		File image = new File(pathImagem);
		
		String dataHora = UtilsDateTime.sendFormatDate(String.valueOf(System.currentTimeMillis()));		
		String imageFileName =  session.getKey("ID_SINAL", "") + "_" + dataHora + ".png";
		
		try {
			File imageDestino = new File(glossarioFotoDir.getPath(), imageFileName);
			FileManagement.copy(image, imageDestino, true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
