package com.br.glossario.bean;

import java.io.Serializable;

public class ComentarioBean implements Serializable{

	private static final long serialVersionUID = 1149954608623482164L;
	private String usuario;
	private String comentario;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
}
