package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

public class FascicoloModel implements Serializable {

	private static final long serialVersionUID = 6070789730329916103L;

	private String nomeFascicolo;
	private String idFascicolo;
	private List<DatiProcedimentoModel> procedimenti;
	private List<FileDocumentoModel> altriDocumenti;
	
	public FascicoloModel() {
		super();
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public List<DatiProcedimentoModel> getProcedimenti() {
		return procedimenti;
	}

	public void setProcedimenti(List<DatiProcedimentoModel> procedimenti) {
		this.procedimenti = procedimenti;
	}

	public List<FileDocumentoModel> getAltriDocumenti() {
		return altriDocumenti;
	}

	public void setAltriDocumenti(List<FileDocumentoModel> documenti) {
		this.altriDocumenti = documenti;
	}
}
