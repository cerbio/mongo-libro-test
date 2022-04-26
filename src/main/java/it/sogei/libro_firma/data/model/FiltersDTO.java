package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class FiltersDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Schema(description = "Codice applicazione filtrato")
	private String codiceApplicazione;
	
	@Schema(description = "Lista stati documento filtrati")
	private List<String> statoDocumento;
	
	@Schema(description = "Parametro di ricerca")
	private String param;
	
	public FiltersDTO() {
		super();
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public List<String> getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(List<String> statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
