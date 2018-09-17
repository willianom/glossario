package com.br.glossario.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
	
	private static final String IDIOMA = "GLOSSARIO";
	private SharedPreferences share;
	private SharedPreferences.Editor editor;
	
	public Session(Context context){
		share  = context.getSharedPreferences(IDIOMA,0);
		editor = share.edit();		
	}
	
	public void setKey(String key ,String value){
		editor.putString(key, value);
	    editor.commit();
	}
	
	public String getKey(String key,String valueDefault){
		return  share.getString(key, valueDefault);
	}
		
	public void setKeyInt(String key ,int value){
		editor.putInt(key, value);
	    editor.commit();
	}
	
	public int getKeyInt(String key, int valueDefault){
		return  share.getInt(key, valueDefault);
	}
	
	public void deleteKey(){
		editor.clear();
		editor.commit();
	}
	
}
