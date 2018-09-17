package com.br.glossario;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.br.glossario.adapter.GridViewAdapter;

public class GridSinaisLibras extends Fragment {

	public static final String TAG = "glossarioGridSinaisFragment";
	
	private GridViewAdapter mAdapter;
	private ArrayList<String> listId;
	private ArrayList<Integer> listFlag;

	private GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.grid_view_main_glossario, container, false);
		
		prepareList();
		
		mAdapter = new GridViewAdapter(getActivity(), listId, listFlag);
		
		gridView = (GridView) view. findViewById(R.id.grid_sinal_glossario);
		gridView.setAdapter(mAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Bundle bun = new Bundle();
				bun.putString("idItem", listId.get(position));
				bun.putInt("icon", listFlag.get(position));
					
				Intent it = new Intent(getActivity(), ViewSinaisGlossario.class);
				it.putExtras(bun);
				startActivity(it);
			}
		});
		return view;
	}
	
	public void prepareList()
    {
          listId = new ArrayList<String>(); 
          listId.add("1");
          listId.add("2");
          listId.add("3");
          listId.add("4");
          listId.add("5");
          listId.add("6");
          listId.add("7");
          listId.add("8");
          listId.add("9");
          listId.add("10");
          listId.add("11");
          listId.add("12");
          listId.add("13");
          listId.add("14");
          listId.add("15");
          listId.add("16");
          listId.add("17");
          listId.add("18");
          listId.add("19");
          listId.add("20");
          listId.add("21");
          listId.add("22");
          listId.add("23");
          listId.add("24");
          listId.add("25");
          listId.add("26");
          listId.add("27");
          listId.add("28");
          listId.add("29");
          listId.add("30");
          listId.add("31");
          listId.add("32");
          listId.add("33");
          listId.add("34");
          listId.add("35");
          listId.add("36");
          listId.add("37");
          listId.add("38");
          listId.add("39");
          listId.add("40");
          listId.add("41");
          listId.add("42");
          listId.add("43");
          listId.add("44");
          listId.add("45");
          listId.add("46");
          listId.add("47");
          listId.add("48");
          listId.add("49");
          listId.add("50");
          listId.add("51");
          listId.add("52");
          listId.add("53");
          listId.add("54");
          listId.add("55");
          listId.add("56");
          listId.add("57");
          listId.add("58");
          listId.add("59");
          listId.add("60");
          listId.add("61");
 
          listFlag = new ArrayList<Integer>();
          listFlag.add(R.drawable.ic_config1);
          listFlag.add(R.drawable.ic_config2);
          listFlag.add(R.drawable.ic_config3);
          listFlag.add(R.drawable.ic_config4);
          listFlag.add(R.drawable.ic_config5);
          listFlag.add(R.drawable.ic_config6);
          listFlag.add(R.drawable.ic_config7);
          listFlag.add(R.drawable.ic_config8);
          listFlag.add(R.drawable.ic_config9);
          listFlag.add(R.drawable.ic_config10);
          listFlag.add(R.drawable.ic_config11);
          listFlag.add(R.drawable.ic_config12);
          listFlag.add(R.drawable.ic_config13);
          listFlag.add(R.drawable.ic_config14);
          listFlag.add(R.drawable.ic_config15);
          listFlag.add(R.drawable.ic_config16);
          listFlag.add(R.drawable.ic_config17);
          listFlag.add(R.drawable.ic_config18);
          listFlag.add(R.drawable.ic_config19);
          listFlag.add(R.drawable.ic_config20);
          listFlag.add(R.drawable.ic_config21);
          listFlag.add(R.drawable.ic_config22);
          listFlag.add(R.drawable.ic_config23);
          listFlag.add(R.drawable.ic_config24);
          listFlag.add(R.drawable.ic_config25);
          listFlag.add(R.drawable.ic_config26);
          listFlag.add(R.drawable.ic_config27);
          listFlag.add(R.drawable.ic_config28);
          listFlag.add(R.drawable.ic_config29);
          listFlag.add(R.drawable.ic_config30);
          listFlag.add(R.drawable.ic_config31);
          listFlag.add(R.drawable.ic_config32);
          listFlag.add(R.drawable.ic_config33);
          listFlag.add(R.drawable.ic_config34);
          listFlag.add(R.drawable.ic_config35);
          listFlag.add(R.drawable.ic_config36);
          listFlag.add(R.drawable.ic_config37);
          listFlag.add(R.drawable.ic_config38);
          listFlag.add(R.drawable.ic_config39);
          listFlag.add(R.drawable.ic_config40);
          listFlag.add(R.drawable.ic_config41);
          listFlag.add(R.drawable.ic_config42);
          listFlag.add(R.drawable.ic_config43);
          listFlag.add(R.drawable.ic_config44);
          listFlag.add(R.drawable.ic_config45);
          listFlag.add(R.drawable.ic_config46);
          listFlag.add(R.drawable.ic_config47);
          listFlag.add(R.drawable.ic_config48);
          listFlag.add(R.drawable.ic_config49);
          listFlag.add(R.drawable.ic_config50);
          listFlag.add(R.drawable.ic_config51);
          listFlag.add(R.drawable.ic_config52);
          listFlag.add(R.drawable.ic_config53);
          listFlag.add(R.drawable.ic_config54);
          listFlag.add(R.drawable.ic_config55);
          listFlag.add(R.drawable.ic_config56);
          listFlag.add(R.drawable.ic_config57);
          listFlag.add(R.drawable.ic_config58);
          listFlag.add(R.drawable.ic_config59);
          listFlag.add(R.drawable.ic_config60);
          listFlag.add(R.drawable.ic_config61);
    }
}
