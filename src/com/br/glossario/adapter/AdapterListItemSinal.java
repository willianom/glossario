package com.br.glossario.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.br.glossario.R;
import com.br.glossario.bean.ListItemSinalBean;
import com.br.glossario.utils.VolleySingleton;

public class AdapterListItemSinal extends BaseAdapter {

	private Context context;
	private List<ListItemSinalBean> listItemLista;

	public AdapterListItemSinal(Context context, List<ListItemSinalBean> listItemLista) {
		this.context = context;
		this.listItemLista = listItemLista;
	}

	@Override
	public int getCount() {
		return listItemLista.size();
	}

	@Override
	public Object getItem(int position) {
		return  listItemLista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ListItemSinalBean lista = listItemLista.get(position);
				
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_list_sinais, null);

		NetworkImageView imgListaSinal = (NetworkImageView) view.findViewById(R.id.img_item_sinal);
		imgListaSinal.setImageUrl("http://glossariomobile.besaba.com/glossario/uploads/" + lista.getImagem(), VolleySingleton.getInstance(context).getImageLoader());

		TextView txtConceitoSinal = (TextView) view.findViewById(R.id.txt_item_conceito);
		txtConceitoSinal.setText(lista.getConceito());
		
		TextView txtComentarioSinal = (TextView) view.findViewById(R.id.txt_item_comentario);
		txtComentarioSinal.setText(lista.getDescricao());
		
		return view;
	}
}
