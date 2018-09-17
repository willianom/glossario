package com.br.glossario;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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

public class ViewSinaisGlossario extends ActionBarActivity implements Response.Listener<String>, Response.ErrorListener, OnItemClickListener{

	private ImageView imgSinal;
	private ListView listItemSinal;
	private LinearLayout progressLayout;

	private AdapterListItemSinal itemSinaisAdapter;
	private List<ListItemSinalBean> listSinais;
	
	private Bundle bun = new Bundle();
	private String idItem;
	private Session session;
	
	ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list_sinais);
		
		session = new Session(getApplicationContext());
		
		imgSinal = (ImageView) findViewById(R.id.img_view_sinais);
		listItemSinal = (ListView) findViewById(R.id.list_view_sinais);
		progressLayout = (LinearLayout) findViewById(R.id.layout_progress_list);
		
		bun = getIntent().getExtras();
		
		if (bun != null) {
			
			idItem = (String) bun.getString("idItem");
			int icon = (int) bun.getInt("icon");			
			imgSinal.setImageResource(icon);
		}
		
		listItemSinal.setOnItemClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		session.setKey("ID_SINAL", idItem);
		String url = "http://glossariomobile.besaba.com/glossario/lista.php?id=" + idItem;
		
		progressLayout.setVisibility(View.VISIBLE);

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringObjRequest = new StringRequest(Request.Method.POST, url, this, this);
		queue.add(stringObjRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_view_glossario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_add_item) {

			Intent it = new Intent(this, AddGlossario.class);
			startActivity(it);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		progressLayout.setVisibility(View.GONE);
	}

	@Override
	public void onResponse(String response) {
		
		System.out.println(response.toString());
		progressLayout.setVisibility(View.GONE);
		
		Gson gson = new Gson();
		listSinais = new ArrayList<ListItemSinalBean>();
		
		ListItemSinalBean[] person = gson.fromJson(response, ListItemSinalBean[].class);
		
		if (person.length > 0) {
						
			for (int i = 0; i < person.length; i++) {			
				listSinais.add(person[i]);
			}			
			
			if(listSinais != null){		
				itemSinaisAdapter = new AdapterListItemSinal(this, listSinais);
				listItemSinal.setAdapter(itemSinaisAdapter);			
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		ListItemSinalBean beanSinal = (ListItemSinalBean) parent.getAdapter().getItem(position);
		
		Bundle bun = new Bundle();
		bun.putSerializable("beanSinal", beanSinal);		
		bun.putInt("flagMenu", 0);
		
		Intent it = new Intent(getApplicationContext(), ViewInformSinaisGlossario.class);
		it.putExtras(bun);
		startActivity(it);		
	}
}
