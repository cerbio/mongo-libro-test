package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.TipoFileEnum;

public class FileDocumentoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id ecm dell'ultima versione del file")
	private String idEcm;
	
	@Schema(description = "Id ecm originale")
	private String idEcmOriginale;
	
	@Schema(description = "nome del File")
	private String nomeFile;
	
	@Schema(description = "content type del file")
	private String contentType;
	
	@Schema(description = "")
	private String tipo;
	
	@Schema(description = "check sullo stato della firma del documento")
	private boolean daFirmare;
	
	@Schema(description = "Tipologia file")
	private TipoFileEnum tipoFile;
	
	public FileDocumentoDTO() {
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

	public TipoFileEnum getTipoFile() {
		return tipoFile;
	}

	public void setTipoFile(TipoFileEnum tipoFile) {
		this.tipoFile = tipoFile;
	}
	
}
