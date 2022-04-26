package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

public class UpdateDocumentoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String idDocumento;
	
	private Boolean urgente;
	
	private Date dataScadenza;
	
	public UpdateDocumentoDTO() {
		super();
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(Boolean urgente) {
		this.urgente = urgente;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
}
