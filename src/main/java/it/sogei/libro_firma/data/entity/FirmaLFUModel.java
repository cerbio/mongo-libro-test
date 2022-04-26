package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;

public class FirmaLFUModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String utente;
	private Date dataFirma;
	private String modalitaFirma;
	private String tipoFirma;
	
	public FirmaLFUModel() {
		super();
	}
	
	public String getUtente() {
		return utente;
	}
	
	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	public Date getDataFirma() {
		return dataFirma;
	}
	
	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}
	
	public String getModalitaFirma() {
		return modalitaFirma;
	}
	
	public void setModalitaFirma(String modalitaFirma) {
		this.modalitaFirma = modalitaFirma;
	}
	
	public String getTipoFirma() {
		return tipoFirma;
	}
	
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	
}
