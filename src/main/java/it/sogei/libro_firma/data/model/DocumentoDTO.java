package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;

public class DocumentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Nome del file")
	@NotBlank
	private String nomeDocumento;
	
	@Schema(description = "Id del file assegnato dall'ECM")
	@NotBlank
	private String idEcm;

	@Schema(description = "Id del file assegnato da MongoDB")
	private String idDocumento;
	
	@Schema(description = "Content-type del file")
	private String contentType;
	
	@Schema(description = "Applicazione che ha prodotto il documento")
	@NotBlank
	private String appProduttore;
	
	@Schema(description = "Nome applicazione che ha prodotto il documento")
	@NotBlank
	private String nomeAppProd;
	
	@Schema(description = "Stato documento")
	private StatoDocumentoEnum statoDocumento;
	
	public DocumentoDTO() {
		super();
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

	public StatoDocumentoEnum getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(StatoDocumentoEnum statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public String getAppProduttore() {
		return appProduttore;
	}

	public void setAppProduttore(String appProduttore) {
		this.appProduttore = appProduttore;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getNomeAppProd() {
		return nomeAppProd;
	}

	public void setNomeAppProd(String nomeAppProd) {
		this.nomeAppProd = nomeAppProd;
	}

}
