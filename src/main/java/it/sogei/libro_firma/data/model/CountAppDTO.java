package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class CountAppDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Codice applicazione")
	private String codiceApplicazione;
	
	@Schema(description = "Numero documenti applicazione")
	private long countDocumenti;
	
	@Schema(description = "Numero documenti prioritari dell' applicazione")
	private long countDocumentiPrioritari;
	
	public CountAppDTO() {
		super();
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public long getCountDocumenti() {
		return countDocumenti;
	}

	public void setCountDocumenti(long countDocumenti) {
		this.countDocumenti = countDocumenti;
	}

	public long getCountDocumentiPrioritari() {
		return countDocumentiPrioritari;
	}

	public void setCountDocumentiPrioritari(long countDocumentiPrioritari) {
		this.countDocumentiPrioritari = countDocumentiPrioritari;
	}
	
}
