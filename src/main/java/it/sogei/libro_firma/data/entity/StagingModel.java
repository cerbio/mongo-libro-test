package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "StagingFirmaLocale")
public class StagingModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String utenteCreatore;
	private List<FileDocumentoModel> documentiFirmati;
	private List<FileDocumentoModel> documentiNonFirmati;
	
	public StagingModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public List<FileDocumentoModel> getDocumentiFirmati() {
		return documentiFirmati;
	}

	public void setDocumentiFirmati(List<FileDocumentoModel> documentiFirmati) {
		this.documentiFirmati = documentiFirmati;
	}

	public List<FileDocumentoModel> getDocumentiNonFirmati() {
		return documentiNonFirmati;
	}

	public void setDocumentiNonFirmati(List<FileDocumentoModel> documentiNonFirmati) {
		this.documentiNonFirmati = documentiNonFirmati;
	}

}
