package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class FascicoloFedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "id del Fascicolo")
	private String idFascicolo;

	@Schema(description = "nome del Fascicolo")
	private String nomeFascicolo;

	@Schema(description = "altri documenti")
	private List<FileFedDTO> fileAltriDocumenti;

	@Schema(description = "altri documenti")
	private List<DatiProcedimentoFedDTO> datiProcedimento;

	public FascicoloFedDTO() {
		super();
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public List<FileFedDTO> getFileAltriDocumenti() {
		return fileAltriDocumenti;
	}

	public void setFileAltriDocumenti(List<FileFedDTO> fileAltriDocumenti) {
		this.fileAltriDocumenti = fileAltriDocumenti;
	}

	public List<DatiProcedimentoFedDTO> getDatiProcedimento() {
		return datiProcedimento;
	}

	public void setDatiProcedimento(List<DatiProcedimentoFedDTO> datiProcedimento) {
		this.datiProcedimento = datiProcedimento;
	}

	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

}
