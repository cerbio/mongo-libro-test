package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.entity.Filtro;

public class FiltroAvanzatoDTO implements Serializable {

	private static final long serialVersionUID = 1696121880249685873L;
	
	@Schema(description = "Numero Riga")
	private String idRiga;
	
	@Schema(description = "Lista dei filtri")
	private List<Filtro> filtri;
	
	public FiltroAvanzatoDTO() {
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
