package com.br.glossario.bean;

import java.io.Serializable;

public class ListItemSinalBean implements Serializable{
	
	private static final long serialVersionUID = 7733060119417978943L;
	private String id;
	private String idconfigmao;
	private String conceito;
	private String descricao;
	private String imagem;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdconfigmao() {
		return idconfigmao;
	}
	public void setIdconfigmao(String idconfigmao) {
		this.idconfigmao = idconfigmao;
	}
	public String getConceito() {
		return conceito;
	}
	public void setConceito(String conceito) {
		this.conceito = conceito;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
}
