package com.br.glossario;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.glossario.adapter.AdapterComentario;
import com.br.glossario.bean.ComentarioBean;
import com.br.glossario.bean.ListItemSinalBean;
import com.br.glossario.utils.Session;
import com.br.glossario.utils.Utils;
import com.br.glossario.utils.VolleySingleton;
import com.google.gson.Gson;

public class ViewInformSinaisGlossario extends ActionBarActivity implements Response.Listener<String>, Response.ErrorListener, OnClickListener{
	
	private Bundle bun = new Bundle();
	private ListItemSinalBean beanSinal = new ListItemSinalBean();
	
	private NetworkImageView imgFotoSinal;
	private TextView txtNomeSinal;
	private ImageView imgViewSinal;
	private TextView txtDescSinal;
	private ListView listComentario;
	private EditText edtComentario;
	private ImageButton btnComentario;
	
	private int flagComent = 0;
	private int flagMenu;
	private List<ComentarioBean> listViewComent;
	private AdapterComentario comentarioAdapter;
	
	private Session session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_inform_sinal_glossario);
		
		session = new Session(getApplicationContext());
		
		imgFotoSinal = (NetworkImageView) findViewById(R.id.img_foto_sinal);
		txtNomeSinal = (TextView) findViewById(R.id.txt_nome_inf_sinal);
		imgViewSinal = (ImageView) findViewById(R.id.img_view_sinal);
		txtDescSinal = (TextView) findViewById(R.id.txt_descricao_inf_sinal);
		listComentario = (ListView) findViewById(R.id.list_coment_inf_sinal);
		edtComentario = (EditText) findViewById(R.id.edit_coment_inf_sinal);
		btnComentario = (ImageButton) findViewById(R.id.btn_coment_inf_sinal);
	
		btnComentario.setOnClickListener(this);
		
		bun = getIntent().getExtras();
		
		if (bun != null) {
			beanSinal = (ListItemSinalBean) bun.getSerializable("beanSinal");
			flagMenu = (Integer) bun.getInt("flagMenu");
			
			System.out.println("flagMenu: " + flagMenu);
		}	
	
		preencheCampos(beanSinal);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		populaListaComentario();		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		if (flagMenu == 1) {
			getMenuInflater().inflate(R.menu.menu_editar_deletar_glossario, menu);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_editar) {

			Bundle bun = new Bundle();
			bun.putSerializable("beanSinalEditar", beanSinal);		
			
			Intent it = new Intent(getApplicationContext(), EditaSinalGlossario.class);
			it.putExtras(bun);
			startActivity(it);
			finish();
			
			return true;
		}
		
		if (id == R.id.action_deletar) {
			
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			
			alerta.setTitle("Deletar Registro");
			alerta.setMessage("Deseja deletar o registro atual?");

			alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {					
					deletaRegistro();					
				}
			});
					
			alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {			
					dialog.dismiss();
				}
			});
			alerta.show();		

			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void preencheCampos(ListItemSinalBean beanSinal){
		
		String nomeImg = "ic_config" + beanSinal.getIdconfigmao();	
		
		imgFotoSinal.setImageUrl("http://glossariomobile.besaba.com/glossario/uploads/" + beanSinal.getImagem(), VolleySingleton.getInstance(this).getImageLoader());
		txtNomeSinal.setText(beanSinal.getConceito());
		imgViewSinal.setBackgroundResource(serializeDrawableSinal(nomeImg));
		txtDescSinal.setText(beanSinal.getDescricao());
	}
	
	public int serializeDrawableSinal(String nome){
		
		Class<?> Rdrawable;
		int refDrawable = 0;
		
		try {
			Rdrawable = Class.forName("com.br.glossario.R$drawable");
			
			 Field fieldIcon  = Rdrawable.getField(nome);
			 refDrawable = fieldIcon.getInt(null);
			 
		} catch (ClassNotFoundException e) { 			
				e.printStackTrace();
        }  catch (SecurityException e) {
				e.printStackTrace();
        } catch (NoSuchFieldException e) {
				e.printStackTrace();
        } catch (IllegalArgumentException e) {
				e.printStackTrace();
        } catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		return refDrawable;
	}	

	@Override
	public void onErrorResponse(VolleyError error) {
		
		Toast.makeText(getApplicationContext(), "Erro ao enviar mensagem. Tente novamente!", Toast.LENGTH_LONG).show();		
	}

	@Override
	public void onResponse(String response) {
		
		if(flagComent == 1){
			
			Gson gson = new Gson();
			listViewComent = new ArrayList<ComentarioBean>();
			
			ComentarioBean[] person = gson.fromJson(response, ComentarioBean[].class);
			
			if (person.length > 0) {							
				for (int i = 0; i < person.length; i++) {			
					listViewComent.add(person[i]);
				}			
				
				if(listViewComent != null){		
					comentarioAdapter = new AdapterComentario(this, listViewComent);
					listComentario.setAdapter(comentarioAdapter);			
				}
			}
			
		} else if (flagComent == 2) {
			
			edtComentario.setText("");			
			populaListaComentario();
			
		} else{
			finish();
		}				
	}

	@Override
	public void onClick(View v) {

		if (v == btnComentario) {
			
			if(validaCampos()){					
				mostraVazio();
				
			} else{						
				if(Utils.isNetworkAvailable(getApplicationContext())){
					
					String url;
					try {
						url = "http://glossariomobile.besaba.com/glossario/inserir_comentario.php?iditem=" + beanSinal.getId()
										+ "&idusuario=" + URLEncoder.encode(session.getKey("ID_USER", " "), "UTF-8")
										+ "&comentario=" + URLEncoder.encode(edtComentario.getText().toString(), "UTF-8");
						
						RequestQueue queue = Volley.newRequestQueue(this);
						StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
						queue.add(stringObjRequest);
						
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else{
					Toast.makeText(getApplicationContext(), "Sem sinal de internet.", Toast.LENGTH_LONG).show();
				}							
			}			
			Utils.closeVirtualKeyboard(getApplicationContext(), v);
			    
			flagComent = 2;			
		}		
	}
	
	public void populaListaComentario(){
		
		String url = "http://glossariomobile.besaba.com/glossario/comentario.php?id=" + beanSinal.getId();

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
		queue.add(stringObjRequest);	
		
		flagComent = 1;
	}
	
	public void deletaRegistro(){
		
		//Inserir progress ao iniciar conexão...
		
		String url = "http://glossariomobile.besaba.com/glossario/remover.php?id=" + beanSinal.getId();

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
		queue.add(stringObjRequest);	
		
		flagComent = 0;		
	}
	
	public void hideKeyboard(Context context, View editText) {
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(edtComentario.getWindowToken(), 0);
	}
	
	private boolean validaCampos(){
		
		if(edtComentario.getText().toString().equals("")){
			return true;
			
		} else{
			return false;
		}
	}
	
	private void mostraVazio(){
		
		if(edtComentario.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "Digite o comentário.", Toast.LENGTH_SHORT).show();
			edtComentario.requestFocus();			
		} 
	}	
}
