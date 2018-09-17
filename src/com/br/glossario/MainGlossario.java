package com.br.glossario;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.br.glossario.adapter.AdapterNavDrawer;
import com.br.glossario.bean.NavDrawerBean;
import com.br.glossario.parserjson.JsonNavDrawer;
import com.google.gson.Gson;

public class MainGlossario extends ActionBarActivity {

	private DrawerLayout dnLayout;
	private ListView listDrawer;
	private List<NavDrawerBean> listNavDrawer;

	private JsonNavDrawer jsonVanDrawer;
	private String strNavDrawer;

	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;
	
	private GridSinaisLibras gridSinaisActivity;
	private Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_glossario);

		gson = new Gson();
		
		prepareNavigationDrawer();
		showGridSinaisFragment();
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			
			NavDrawerBean modelDrawer = (NavDrawerBean) adapterView.getAdapter().getItem(position);			
			String menuTitle = modelDrawer.getTitle();
			
			if(menuTitle.equals("Meus Itens")){					
				Intent it = new Intent(getApplicationContext(), ViewSinaisUserGlossario.class);
				startActivity(it);				
			}			
			
			if(menuTitle.equals("Sobre")){					
				Intent it = new Intent(getApplicationContext(), SobreGlossario.class);
				startActivity(it);				
			}		
		}		
	} 

	private void prepareNavigationDrawer() {

		dnLayout = (DrawerLayout) findViewById(R.id.drawer_glossario_layout);
		listDrawer = (ListView) findViewById(R.id.list_drawer_glossario);

		mDrawerToggle = new ActionBarDrawerToggle(this, dnLayout, R.drawable.ic_drawer, R.string.ns_menu_open, 0) {

			public void onDrawerClosed(View drawerView) {
				supportInvalidateOptionsMenu();
			};

			@Override
			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu();

				AdapterNavDrawer adapterNav = new AdapterNavDrawer(getApplicationContext(), listNavDrawer);
				listDrawer.setAdapter(adapterNav);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);

				dnLayout.bringChildToFront(drawerView);
				dnLayout.requestLayout();
			}
		};
		
	/*	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(false);*/
		getSupportActionBar().setDisplayShowTitleEnabled(true);

		dnLayout.setDrawerListener(mDrawerToggle);
		dnLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowCustomEnabled(true);

		strNavDrawer = getResources().getString(R.string.dn_menu_principal);
		jsonVanDrawer = gson.fromJson(strNavDrawer, JsonNavDrawer.class);
		listNavDrawer = jsonVanDrawer.getMenu();

		AdapterNavDrawer adapterNav = new AdapterNavDrawer(getApplicationContext(), listNavDrawer);
		listDrawer.setAdapter(adapterNav);

		listDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}
	
	private void showGridSinaisFragment(){				
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		gridSinaisActivity = (GridSinaisLibras) getSupportFragmentManager().
				      findFragmentByTag(GridSinaisLibras.TAG);		
		
		if (gridSinaisActivity == null) {
			gridSinaisActivity = new GridSinaisLibras();
			ft.add(R.id.frame_main_glossario, gridSinaisActivity);
			
			ft.commit();		
		}	
	}
	
	@Override
	public void onBackPressed() {
		
		AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		
		alerta.setTitle("Sair da Aplicação");
		alerta.setMessage("O aplicativo será fechado.");

		alerta.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(getApplicationContext(), LoginGlossario.class);
				it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				it.putExtra("exit", true);
				startActivity(it);
				finish();
			}
		});
				
		alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {			
				dialog.dismiss();
			}
		});

		alerta.show();
	}
}
