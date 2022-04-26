package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

public class FiltroAvanzato implements Serializable{

	private static final long serialVersionUID = 7027811503650316957L;

	private String idRiga;
	private List<Filtro> filtri;
	
	public FiltroAvanzato() {
		super();
	}

	public String getIdRiga() {
		return idRiga;
	}

	public void setIdRiga(String idRiga) {
		this.idRiga = idRiga;
	}

	public List<Filtro> getFiltri() {
		return filtri;
	}

	public void setFiltri(List<Filtro> filtri) {
		this.filtri = filtri;
	}
}
