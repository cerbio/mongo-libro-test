package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class CondividiDocumentiDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Lista Documenti")
	private List<String> listaDocumenti;

	@Schema(description = "Invito alla firma")
	private boolean invitoFirma;
	
	@Schema(description = "Priorità")
	private boolean priorita;

	@Schema(description = "Lista Cf Utenti")
	private List<String> listaUtenti;
	
	public CondividiDocumentiDTO() {
		super();
	}

	public List<String> getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(List<String> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}

	public List<String> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<String> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

	public boolean getInvitoFirma() {
		return invitoFirma;
	}

	public void setInvitoFirma(boolean invitoFirma) {
		this.invitoFirma = invitoFirma;
	}

	public boolean getPriorita() {
		return priorita;
	}

	public void setPriorita(boolean priorita) {
		this.priorita = priorita;
	}
	

}
