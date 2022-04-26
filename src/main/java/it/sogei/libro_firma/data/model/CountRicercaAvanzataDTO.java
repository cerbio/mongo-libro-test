package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.FiltriEnum;

public class CountRicercaAvanzataDTO implements Serializable {

	private static final long serialVersionUID = 7844949848459028362L;

	@Schema(description = "Filtri utilizzati per la ricerca")
	private FiltriEnum tipoDiFiltri;
	
	@Schema(description = "Codice applicazione")
	private String codiceApplicazione;
	
	@Schema(description = "Id gruppo filtri")
	private String idGruppo;
	
	@Schema(description = "Stringa da ricercare")
	private String param;
	
	@Schema(description = "Ricerca avanzata pagina di to work")
	private RicercaAvanzataDTO parametri;
	
	@Schema(description = "Lista filtri selezionabili")
	private List<String> ricercaFiltri;
	
	public CountRicercaAvanzataDTO() {
		super();
	}

	public FiltriEnum getTipoDiFiltri() {
		return tipoDiFiltri;
	}

	public void setTipoDiFiltri(FiltriEnum tipoDiFiltri) {
		this.tipoDiFiltri = tipoDiFiltri;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public String getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public RicercaAvanzataDTO getParametri() {
		return parametri;
	}

	public void setParametri(RicercaAvanzataDTO parametri) {
		this.parametri = parametri;
	}

	public List<String> getRicercaFiltri() {
		return ricercaFiltri;
	}

	public void setRicercaFiltri(List<String> ricercaFiltri) {
		this.ricercaFiltri = ricercaFiltri;
	}

}
