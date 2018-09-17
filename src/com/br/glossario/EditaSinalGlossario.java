package com.br.glossario;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.glossario.bean.ListItemSinalBean;
import com.br.glossario.utils.VolleySingleton;

public class EditaSinalGlossario extends ActionBarActivity implements OnClickListener, Response.Listener<String>, Response.ErrorListener{
	
	private NetworkImageView imgSinal;
	private EditText edtConceito;
	private EditText edtDescricao;
	private Button btnSalvar;
	private Button btnCancelar;
	private LinearLayout layoutProgress;
	
	private Bundle bun = new Bundle();
	private ListItemSinalBean beanSinal = new ListItemSinalBean();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_editar_glossario);
		
		imgSinal = (NetworkImageView) findViewById(R.id.imgSinal_editar);
		edtConceito = (EditText) findViewById(R.id.edt_sinal_conceito_editar);
		edtDescricao = (EditText) findViewById(R.id.edt_sinal_descricao_editar);
		btnSalvar = (Button) findViewById(R.id.btn_sinal_add_editar);
		btnCancelar = (Button) findViewById(R.id.btn_sinal_cancelar_editar);
		layoutProgress = (LinearLayout) findViewById(R.id.layout_progress_foto_editar);
		
		btnSalvar.setOnClickListener(this);
		btnCancelar.setOnClickListener(this);	
		
		bun = getIntent().getExtras();
		
		if (bun != null) {
			beanSinal = (ListItemSinalBean) bun.getSerializable("beanSinalEditar");
		}
		
		preencheCampos(beanSinal);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}	

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getApplicationContext(), "Erro na comunicação. Tente novamente...", Toast.LENGTH_LONG).show();		
	}

	@Override
	public void onResponse(String response) {
		
		layoutProgress.setVisibility(View.VISIBLE);
		 finish();
		
	}

	@Override
	public void onClick(View v) {
		if (v == btnSalvar) {
			
			String url;
			
			try {
				url = "http://glossariomobile.besaba.com/glossario/editar.php?conceito=" + URLEncoder.encode(edtConceito.getText().toString(), "UTF-8")
						+ "&descricao=" + URLEncoder.encode(edtDescricao.getText().toString(), "UTF-8")
						+ "&id=" + URLEncoder.encode(beanSinal.getId(), "UTF-8");
				
				RequestQueue queue = Volley.newRequestQueue(this);
				StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
				queue.add(stringObjRequest);	
				
				layoutProgress.setVisibility(View.VISIBLE);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (v == btnCancelar) {
			finish();
		}
	}

	public void preencheCampos(ListItemSinalBean beanSinal){
		
		String nomeImg = "ic_config" + beanSinal.getIdconfigmao();	
		
		imgSinal.setImageUrl("http://glossariomobile.besaba.com/glossario/uploads/" + beanSinal.getImagem(), VolleySingleton.getInstance(this).getImageLoader());
		edtConceito.setText(beanSinal.getConceito());
		edtDescricao.setText(beanSinal.getDescricao());		
	}
}
