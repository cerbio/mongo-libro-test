package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.OrdinamentoEnum;

public class FindParamsByGroupDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del gruppo per la ricerca")
	private String idGruppo;
	
	@Schema(description = "Parametro di ricerca")
	private String param;
	
	@Schema(description = "Numero pagina", defaultValue = "1")
	private Integer numeroPagina = 1;
	
	@Schema(description = "Numero elementi per pagina", defaultValue = "10")
	private Integer numeroElementiPagina = 10;
	
	@Schema(description = "Parametri di ricerca avanzata")
	private RicercaAvanzataDTO ricercaAvanzata;
	
	@Schema(description = "Parametri di ricerca filtri")
	private List<String> ricercaFiltri;
	
	@Schema(description = "Campo da ordinare")
	private OrdinamentoEnum ordinamento;
	
	public FindParamsByGroupDTO() {
		super();
	}

	public String getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	
	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public Integer getNumeroElementiPagina() {
		return numeroElementiPagina;
	}

	public void setNumeroElementiPagina(Integer numeroElementiPagina) {
		this.numeroElementiPagina = numeroElementiPagina;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public RicercaAvanzataDTO getRicercaAvanzata() {
		return ricercaAvanzata;
	}

	public void setRicercaAvanzata(RicercaAvanzataDTO ricercaAvanzata) {
		this.ricercaAvanzata = ricercaAvanzata;
	}

	public List<String> getRicercaFiltri() {
		return ricercaFiltri;
	}

	public void setRicercaFiltri(List<String> ricercaFiltri) {
		this.ricercaFiltri = ricercaFiltri;
	}

	public OrdinamentoEnum getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(OrdinamentoEnum ordinamento) {
		this.ordinamento = ordinamento;
	}

}
