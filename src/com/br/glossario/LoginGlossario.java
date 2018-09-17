package com.br.glossario;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.glossario.utils.Session;
import com.br.glossario.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginGlossario extends ActionBarActivity implements OnClickListener, Response.Listener<String>,
		Response.ErrorListener {

	private EditText edtUser;
	private EditText edtSenha;
	private Button btnEntrar;
	private LinearLayout linearProgress;
	private LinearLayout linearEditText;
	
	private Session session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_glossario);
		
		session = new Session(getApplicationContext());

		btnEntrar = (Button) findViewById(R.id.btnLogin);
		edtUser = (EditText) findViewById(R.id.edtTextUsuario);
		edtSenha = (EditText) findViewById(R.id.edtTextSenha);
		linearProgress = (LinearLayout) findViewById(R.id.layout_progress_login);
		linearEditText = (LinearLayout) findViewById(R.id.linearTextLogin);

		btnEntrar.setOnClickListener(this);
		
		if(getIntent().getBooleanExtra("exit", false) == true){
			finish();			
			session.deleteKey();
		}

		getSupportActionBar().hide();
	}

	@Override
	public void onClick(View v) {

		if (v == btnEntrar) {
			
			if(validaCampos()){					
				mostraVazio();
				
			} else{						
				if(Utils.isNetworkAvailable(getApplicationContext())){
					
					trocaFormLogin(true);					
					String url = "http://glossariomobile.besaba.com/glossario/login.php?user=\"" +edtUser.getText().toString() + "\"&pass=\"" + edtSenha.getText().toString() +"\"";

					RequestQueue queue = Volley.newRequestQueue(this);
					StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
					queue.add(stringObjRequest);
					
				} else{
					Toast.makeText(getApplicationContext(), "Sem sinal de internet.", Toast.LENGTH_LONG).show();
				}							
			}			
			Utils.closeVirtualKeyboard(getApplicationContext(), v);
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(this, "Tente novamente!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResponse(String response) {
		
		JsonArray entries = (JsonArray) new JsonParser().parse(response);
		JsonElement elementStatus = ((JsonObject)entries.get(0)).get("status"); 
		
		String status = elementStatus.toString();
			
		if(status.equals("true")){
			
			JsonElement elementId = ((JsonObject)entries.get(0)).get("id");
			int id = elementId.getAsInt();
			
			session.setKey("USER", edtUser.getText().toString());
			session.setKey("ID_USER", String.valueOf(id));
									
			Intent it = new Intent(this, MainGlossario.class);
			startActivity(it);
			
		} else {
			
			trocaFormLogin(false);
			Toast.makeText(this, "Login Inválido.", Toast.LENGTH_SHORT).show();
		}	
	}
	
	public void trocaFormLogin(boolean status){
		
		if(status == true){
			linearEditText.setVisibility(View.GONE);
			linearProgress.setVisibility(View.VISIBLE);
			btnEntrar.setVisibility(View.GONE);
		} else{
			linearEditText.setVisibility(View.VISIBLE);
			linearProgress.setVisibility(View.GONE);
			btnEntrar.setVisibility(View.VISIBLE);
			
			Toast.makeText(getApplicationContext(), "Usuário ou Senha inválida.", Toast.LENGTH_LONG).show();
		}
	}	
	
	private boolean validaCampos(){
		
		if(edtUser.getText().toString().equals("") ||
				edtSenha.getText().toString().equals("")){
			return true;
			
		} else{
			return false;
		}
	}
	
	private void mostraVazio(){
		
		if(edtUser.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "Preencha campo Usuário.", Toast.LENGTH_SHORT).show();
			edtUser.requestFocus();
			
		} else { 
			Toast.makeText(getApplicationContext(), "Preencha campo Senha.", Toast.LENGTH_SHORT).show();
			edtSenha.requestFocus();
		}	
	}	
}
