package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class FascicoloDTO implements Serializable {

	private static final long serialVersionUID = 7378537858489212891L;

	@Schema(description = "Nome del fascicolo")
	private String nomeFascicolo;
	
	@Schema(description = "Id del fascicolo")
	private String idFascicolo;
	
	@Schema(description = "Lista dei procedimenti all'interno del fascicolo")
	private List<DatiProcedimento> procedimenti;
	
	@Schema(description = "Lista dei documenti non appartenenti al fascicolo")
	private List<FileDocumentoDTO> documenti;

	public FascicoloDTO() {
		super();
	}
	
	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public List<DatiProcedimento> getProcedimenti() {
		return procedimenti;
	}

	public void setProcedimenti(List<DatiProcedimento> procedimenti) {
		this.procedimenti = procedimenti;
	}

	public List<FileDocumentoDTO> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<FileDocumentoDTO> documenti) {
		this.documenti = documenti;
	}
}
