package it.sogei.libro_firma.data.entity;

import java.io.Serializable;

public class Filtro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codice;
	private String label;
	private String descrizione;
	
	public Filtro() {
		super();
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
