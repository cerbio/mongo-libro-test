package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import it.sogei.libro_firma.data.model.enums.TipoFileEnum;


public class FileFedDTO implements Serializable{

	private static final long serialVersionUID = -7203497465400783279L;
	
	private String nomeFile;
	private String idEcm;
	private String contentType;
	private TipoFileEnum tipoFileFascicolo;
	private String idFileFed;
	private boolean daFirmare;

	public FileFedDTO() {
		super();
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getIdEcm() {
		return idEcm;
	}

	public void setIdEcm(String idEcm) {
		this.idEcm = idEcm;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public TipoFileEnum getTipoFileFascicolo() {
		return tipoFileFascicolo;
	}

	public void setTipoFileFascicolo(TipoFileEnum tipoFileFascicolo) {
		this.tipoFileFascicolo = tipoFileFascicolo;
	}

	public String getIdFileFed() {
		return idFileFed;
	}

	public void setIdFileFed(String idFileFed) {
		this.idFileFed = idFileFed;
	}

	public boolean isDaFirmare() {
		return daFirmare;
	}

	public void setDaFirmare(boolean daFirmare) {
		this.daFirmare = daFirmare;
	}
}
