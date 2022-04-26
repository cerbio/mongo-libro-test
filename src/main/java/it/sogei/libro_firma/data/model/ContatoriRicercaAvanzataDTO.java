package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class ContatoriRicercaAvanzataDTO implements Serializable {

	private static final long serialVersionUID = -1494292274324113299L;

	@Schema(description = "Label del contatore")
	private String label;
	
	@Schema(description = "Codice Be dell'etichetta")
	private String codice;
	
	@Schema(description = "Numero elementi trovati per il contatore")
	private long numeroElementi;
	
	public ContatoriRicercaAvanzataDTO() {
		super();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public long getNumeroElementi() {
		return numeroElementi;
	}

	public void setNumeroElementi(long numeroElementi) {
		this.numeroElementi = numeroElementi;
	}
}
