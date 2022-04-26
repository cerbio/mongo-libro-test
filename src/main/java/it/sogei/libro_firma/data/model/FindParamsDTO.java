package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;

public class FindParamsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Stringa da cercare")
	@NotEmpty
	private String param = "";
	
	@Schema(description = "Attiva la visualizzazione preview (limite per stato documento)")
	private boolean preview;
	
	@Schema(description = "Numero limite di documenti per stato")
	private long limitePerStato = 2;
	
	@Schema(description = "codiceApplicazione")
	private String codiceApplicazione;
	
	public FindParamsDTO() {
		super();
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public boolean isPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

	public long getLimitePerStato() {
		return limitePerStato;
	}

	public void setLimitePerStato(long limitePerStato) {
		this.limitePerStato = limitePerStato;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
}
