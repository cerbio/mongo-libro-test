package it.sogei.libro_firma.data.entity;

import java.io.Serializable;

public class TagDocumentoModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idTag;
	private String nome;
	
	public TagDocumentoModel() {
		super();
	}
	
	public String getIdTag() {
		return idTag;
	}
	
	public void setIdTag(String id) {
		this.idTag = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
