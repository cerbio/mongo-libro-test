package it.sogei.libro_firma.data.model;

import java.util.List;

import it.sogei.libro_firma.data.model.enums.TipoFileEnum;

public class FileVerificaDTO {
	
	private String idEcm;
	private String idEcmOriginale;
	private TipoFileEnum tipologiaFile;
	private boolean esito;
	private List<FirmatarioVerificaDTO> firmatari;
	
	public FileVerificaDTO() {
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

	
	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public List<FirmatarioVerificaDTO> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<FirmatarioVerificaDTO> firmatari) {
		this.firmatari = firmatari;
	}

	public TipoFileEnum getTipologiaFile() {
		return tipologiaFile;
	}

	public void setTipologiaFile(TipoFileEnum tipologiaFile) {
		this.tipologiaFile = tipologiaFile;
	}
	
	
	
	

}
