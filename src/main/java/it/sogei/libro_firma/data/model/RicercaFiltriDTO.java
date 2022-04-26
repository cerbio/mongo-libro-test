package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class RicercaFiltriDTO implements Serializable {

	private static final long serialVersionUID = 8462358854847358248L;

	
	@Schema(description = "urgente")
	private Boolean urgente;
	
	@Schema(description = "Scaduto")
	private boolean scaduto;
	
	@Schema(description = "in scadenza")
	private boolean inScadenza;
	
	@Schema(description = "verificato")
	private Boolean verificato;
	
	@Schema(description = "Condiviso")
	private Boolean condiviso;

	@Schema(description = "lista Stato documento")
	private List<String> listaStatiDocumento;


	public RicercaFiltriDTO() {
		super();
		listaStatiDocumento = new ArrayList<String>();
	}

	public Boolean getUrgente() {
		return urgente;
	}

	public void setUrgente(Boolean urgente) {
		this.urgente = urgente;
	}

	public boolean getScaduto() {
		return scaduto;
	}

	public void setScaduto(boolean scaduto) {
		this.scaduto = scaduto;
	}

	public boolean getInScadenza() {
		return inScadenza;
	}

	public void setInScadenza(boolean inScadenza) {
		this.inScadenza = inScadenza;
	}

	public List<String> getListaStatiDocumento() {
		return listaStatiDocumento;
	}

	public void setListaStatiDocumento(List<String> listaStatiDocumento) {
		this.listaStatiDocumento = listaStatiDocumento;
	}

	public Boolean getVerificato() {
		return verificato;
	}

	public void setVerificato(Boolean verificato) {
		this.verificato = verificato;
	}

	public Boolean getCondiviso() {
		return condiviso;
	}

	public void setCondiviso(Boolean condiviso) {
		this.condiviso = condiviso;
	}

}
