package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;
import it.sogei.libro_firma.data.model.enums.TipoFirmaEnum;

public class FirmaDocumentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del documento")
	private String idDocumento;
	
	@Schema(description = "Motivo")
	private String motivo;

	@Schema(description = "Tipo di firma applicata sul documento")
	private FirmaEnum tipoFirmaApplicata;
	
	@Schema(description = "Modalità di firma attuata")
	private TipoFirmaEnum modalitaFirmaAttuata;
	
	@Schema(description = "Modalità di firma attuata")
	private List<FileDocumentoDTO> listaFile;
	
	public FirmaDocumentoDTO() {
		super();
	}
	
	public List<FileDocumentoDTO> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<FileDocumentoDTO> listaFile) {
		this.listaFile = listaFile;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public FirmaEnum getTipoFirmaApplicata() {
		return tipoFirmaApplicata;
	}

	public void setTipoFirmaApplicata(FirmaEnum tipoFirmaApplicata) {
		this.tipoFirmaApplicata = tipoFirmaApplicata;
	}

	public TipoFirmaEnum getModalitaFirmaAttuata() {
		return modalitaFirmaAttuata;
	}

	public void setModalitaFirmaAttuata(TipoFirmaEnum modalitaFirmaAttuata) {
		this.modalitaFirmaAttuata = modalitaFirmaAttuata;
	}
	
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}
