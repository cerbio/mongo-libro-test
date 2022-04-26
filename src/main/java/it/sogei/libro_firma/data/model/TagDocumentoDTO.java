package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class TagDocumentoDTO implements Serializable{

	private static final long serialVersionUID = -2558666120678047019L;

	@Schema(description = "Lista di tag da aggiungere sul documento")
	private List<String> tags;
	
	@Schema(description = "Id del documento su cui inserire i tags")
	private String idDocumento;
	
	@Schema(description = "Lista di gruppi dell'utente")
	private List<String> codiciGruppoUtente;
	
	public TagDocumentoDTO() {
		super();
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public List<String> getCodiciGruppoUtente() {
		return codiciGruppoUtente;
	}

	public void setCodiciGruppoUtente(List<String> codiciGruppoUtente) {
		this.codiciGruppoUtente = codiciGruppoUtente;
	}
}
