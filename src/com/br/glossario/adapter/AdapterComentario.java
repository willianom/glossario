package com.br.glossario.adapter;

import java.util.List;

import com.android.volley.toolbox.NetworkImageView;
import com.br.glossario.R;
import com.br.glossario.bean.ComentarioBean;
import com.br.glossario.bean.ListItemSinalBean;
import com.br.glossario.utils.VolleySingleton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterComentario extends BaseAdapter {
	
	private Context context;
	private List<ComentarioBean> listComentario;

	public AdapterComentario(Context context, List<ComentarioBean> listComentario) {
		this.context = context;
		this.listComentario = listComentario;
	}

	@Override
	public int getCount() {
		return listComentario.size();
	}

	@Override
	public Object getItem(int position) {
		return  listComentario.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ComentarioBean itemComentario = listComentario.get(position);
		
		System.out.println("");
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_comentario, null);

		TextView txtUsuarioComent = (TextView) view.findViewById(R.id.txt_usuario_coment);
		txtUsuarioComent.setText(itemComentario.getUsuario());
		
		TextView txtComentario = (TextView) view.findViewById(R.id.txt_comentario);
		txtComentario.setText(itemComentario.getComentario());
		
		return view;
	}

}
