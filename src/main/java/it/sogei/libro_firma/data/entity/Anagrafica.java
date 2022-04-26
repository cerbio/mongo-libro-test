package it.sogei.libro_firma.data.entity;

import java.io.Serializable;

public class Anagrafica implements Serializable{

	private static final long serialVersionUID = -4668255060827504507L;

	private String nome;
	private String cognome;
	private String cf;
	private String email;
	
	public Anagrafica() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
