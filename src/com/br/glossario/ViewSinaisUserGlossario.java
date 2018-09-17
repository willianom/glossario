package com.br.glossario;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.glossario.adapter.AdapterListItemSinal;
import com.br.glossario.bean.ListItemSinalBean;
import com.br.glossario.utils.Session;
import com.google.gson.Gson;

public class ViewSinaisUserGlossario extends ActionBarActivity implements Response.Listener<String>, Response.ErrorListener, OnItemClickListener{
	
	private ListView listItemSinalUser;
	private LinearLayout progressLayout;
	
	private AdapterListItemSinal itemSinaisAdapter;
	private List<ListItemSinalBean> listSinais;
	private ListItemSinalBean listaSinalBean;
		
	private String idUsuario;
	private Session session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list_sinais_user);		
		
		listItemSinalUser = (ListView) findViewById(R.id.list_user_sinais);
		progressLayout = (LinearLayout) findViewById(R.id.layout_progress_list_user);
		
		session = new Session(getApplicationContext());
		
		listItemSinalUser.setOnItemClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		idUsuario = session.getKey("ID_USER", " ");
		String url = "http://glossariomobile.besaba.com/glossario/lista.php?idusuario=" + idUsuario;
		
		progressLayout.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
		queue.add(stringObjRequest);		
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		progressLayout.setVisibility(View.GONE);
	}

	@Override
	public void onResponse(String response) {
		
		progressLayout.setVisibility(View.GONE);
		
		Gson gson = new Gson();
		listSinais = new ArrayList<ListItemSinalBean>();
		
		ListItemSinalBean[] person = gson.fromJson(response, ListItemSinalBean[].class);
		
		System.out.println("Lista Meus Itens: " + response);
		
		if (person.length > 0) {
						
			for (int i = 0; i < person.length; i++) {			
				listSinais.add(person[i]);
			}			
			
			if(listSinais != null){		
				itemSinaisAdapter = new AdapterListItemSinal(this, listSinais);
				listItemSinalUser.setAdapter(itemSinaisAdapter);
			}		
		} else{
			itemSinaisAdapter = new AdapterListItemSinal(this, listSinais);
			listItemSinalUser.setAdapter(itemSinaisAdapter);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		ListItemSinalBean beanSinal = (ListItemSinalBean) parent.getAdapter().getItem(position);
		
		Bundle bun = new Bundle();
		bun.putSerializable("beanSinal", beanSinal);		
		bun.putInt("flagMenu", 1);
		
		Intent it = new Intent(getApplicationContext(), ViewInformSinaisGlossario.class);
		it.putExtras(bun);
		startActivity(it);		
	}
}
