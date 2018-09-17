package com.br.glossario.bean;

import java.io.Serializable;

public class LoginBean  implements Serializable{

	private static final long serialVersionUID = 643922163167386709L;
	
	private String usuario;
	private String senha;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
