package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class TagResponseDTO implements Serializable {

	private static final long serialVersionUID = 6669465522189492050L;
	
	@Schema(description = "id del tag")
	private String idTag;
	
	@Schema(description = "nome del tag")
	private String nomeTag;
	
	@Schema(description = "Flag che indica se l'utente è il creatore")
	private Boolean creatore;
	
	@Schema(description = "Utente creatore")
	private String utenteCreatore;

	public TagResponseDTO() {
		super();
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getNomeTag() {
		return nomeTag;
	}

	public void setNomeTag(String nomeTag) {
		this.nomeTag = nomeTag;
	}

	public Boolean getCreatore() {
		return creatore;
	}

	public void setCreatore(Boolean creatore) {
		this.creatore = creatore;
	}
	
	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

}
