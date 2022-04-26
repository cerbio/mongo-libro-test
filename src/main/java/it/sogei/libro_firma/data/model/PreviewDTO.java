package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class PreviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del gruppo")
	private String idGruppo;
	
	@Schema(description = "Label dello stato del documento")
	private String labelStatoDocumento;

	@Schema(description = "Lista documenti")
	private List<DatiDocumentoDTO> listaDocumenti;
	
	@Schema(description = "Numero documenti totale")
	private int numeroDoc;
	
	public PreviewDTO() {
		super();
	}

	public List<DatiDocumentoDTO> getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(List<DatiDocumentoDTO> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}

	public int getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(int numeroDoc) {
		this.numeroDoc = numeroDoc;
	}


	public String getIdGruppo() {
		return idGruppo;
	}


	public void setIdGruppo(String id) {
		this.idGruppo = id;
	}

	public String getLabelStatoDocumento() {
		return labelStatoDocumento;
	}

	public void setLabelStatoDocumento(String labelStatoDocumento) {
		this.labelStatoDocumento = labelStatoDocumento;
	}
	
}
