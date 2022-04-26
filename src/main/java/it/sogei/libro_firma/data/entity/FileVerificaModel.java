package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

public class FileVerificaModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idEcm;
	private String idEcmOriginale;
	private String tipologiaFile;
	private boolean esito;
	private List<FirmatarioVerifica> firmatari;
	
	public FileVerificaModel() {
		super();
	}

	public String getIdEcm() {
		return idEcm;
	}

	public void setIdEcm(String idEcm) {
		this.idEcm = idEcm;
	}

	public String getIdEcmOriginale() {
		return idEcmOriginale;
	}

	public void setIdEcmOriginale(String idEcmOriginale) {
		this.idEcmOriginale = idEcmOriginale;
	}

	public String getTipologiaFile() {
		return tipologiaFile;
	}

	public void setTipologiaFile(String tipologiaFile) {
		this.tipologiaFile = tipologiaFile;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public List<FirmatarioVerifica> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<FirmatarioVerifica> firmatari) {
		this.firmatari = firmatari;
	}
	
	

	

}
