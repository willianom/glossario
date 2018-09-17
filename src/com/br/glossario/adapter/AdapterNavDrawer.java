package com.br.glossario.adapter;


import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.glossario.R;
import com.br.glossario.bean.NavDrawerBean;
import com.br.glossario.utils.Session;

public class AdapterNavDrawer extends BaseAdapter{
	
	private Context context;
	private List<NavDrawerBean> listNavDrawer;
	
	private Session session;
	
	public AdapterNavDrawer(Context context, List<NavDrawerBean> listNavDrawer){
		this.context = context;
		this.listNavDrawer = listNavDrawer;
	}

	@Override
	public int getCount() {
		return listNavDrawer.size();
	}

	@Override
	public Object getItem(int position) {
		return listNavDrawer.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		int layout = 0;
		session = new Session(context);		
		
		String nomeUsuario = session.getKey("USER", "");
		String idUsuario = session.getKey("ID_USER", "");
		
		NavDrawerBean beanNavDrawer = listNavDrawer.get(position);	
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (beanNavDrawer.getIsHeader().equals("1")){
			layout = R.layout.dn_item_usuario_main;
		} else 
			if(beanNavDrawer.getIsHeader().equals("4")){
				layout = R.layout.dn_item_opcoes;
				}else 
					if(beanNavDrawer.getIsHeader().equals("5")){
						layout = R.layout.dn_item_status;
					}
		
		View view = inflater.inflate(layout, null);
		
		ImageView imageItem = (ImageView) view.findViewById(R.id.menurow_icon);
		TextView txtTitle = (TextView) view.findViewById(R.id.menurow_title);
		
		TextView txtLoginTec = (TextView) view.findViewById(R.id.txtLoginTecnico_Nav);	
		
		TextView txtCodTec = (TextView) view.findViewById(R.id.txtCodTecnico_Nav);
		
		if(!beanNavDrawer.getIsHeader().equals("1")){
			txtTitle.setText(beanNavDrawer.getTitle());
		}
					
		if(beanNavDrawer.getIsHeader().equals("1")){
			
			txtLoginTec.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
			txtCodTec.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
			txtLoginTec.setText(nomeUsuario);
			txtCodTec.setText(idUsuario);
		}
		
		if(beanNavDrawer.getIsHeader().equals("2") || beanNavDrawer.getIsHeader().equals("4") 
				|| beanNavDrawer.getIsHeader().equals("5")){
			
			Class<?> Rdrawable;
			try {
				Rdrawable = Class.forName("iatecam.eservice.org.br.R$drawable");
				Field fieldIcon   = Rdrawable.getField(beanNavDrawer.getIcon());
				
				imageItem.setBackgroundResource(fieldIcon.getInt(null));		
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
	        } catch (NoSuchFieldException e) {
				e.printStackTrace();
	        } catch (IllegalArgumentException e) {
				e.printStackTrace();
	        } catch (IllegalAccessException e) {
				e.printStackTrace();
	        }  		
			
		}	
		
		return view;
	}
}
