package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

public class FileDocumentoModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idEcm;
	private String nomeFile;
	private String contentType;
	private String tipo;
	private boolean daFirmare;
	private List<VersioneModel> versioni;
	private String idFileFed;
	private String tipoFileFascicolo;
	
	public FileDocumentoModel() {
		super();
	}

	public String getIdEcm() {
		return idEcm;
	}

	public void setIdEcm(String idEcm) {
		this.idEcm = idEcm;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isDaFirmare() {
		return daFirmare;
	}

	public void setDaFirmare(boolean daFirmare) {
		this.daFirmare = daFirmare;
	}

	public List<VersioneModel> getVersioni() {
		return versioni;
	}

	public void setVersioni(List<VersioneModel> versioni) {
		this.versioni = versioni;
	}

	public String getIdFileFed() {
		return idFileFed;
	}

	public void setIdFileFed(String idFileFed) {
		this.idFileFed = idFileFed;
	}

	public String getTipoFileFascicolo() {
		return tipoFileFascicolo;
	}

	public void setTipoFileFascicolo(String tipoFileFascicolo) {
		this.tipoFileFascicolo = tipoFileFascicolo;
	}
	
}
