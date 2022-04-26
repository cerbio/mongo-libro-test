package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class MittenteDTO implements Serializable{

	private static final long serialVersionUID = -7919128529525094713L;

	@Schema(description = "nome del mittente")
	private String nome;
	
	@Schema(description = "cognome del mittente")
	private String cognome;
	
	@Schema(description = "codice fiscale del mittente")
	private String cf;
	
	@Schema(description = "email del mittente")
	private String email;
	
	public MittenteDTO() {
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