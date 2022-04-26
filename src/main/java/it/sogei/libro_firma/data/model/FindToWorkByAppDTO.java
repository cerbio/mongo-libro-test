package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.OrdinamentoEnum;

public class FindToWorkByAppDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "codiceApplicazione")
	private String codiceApplicazione;
	
	@Schema(description = "Numero pagina", defaultValue = "1")
	private Integer numeroPagina = 1;
	
	@Schema(description = "Numero elementi per pagina", defaultValue = "10")
	private Integer numeroElementiPagina = 10;
	
	@Schema(description = "Lista documenti")
	private List<DatiDocumentoDTO> listaDocumenti;
	
	@Schema(description = "Parametri di ricerca filtri")
	private List<String> ricercaFiltri;
	
	@Schema(description = "Campo da ordinare")
	private OrdinamentoEnum ordinamento;
	
	public List<DatiDocumentoDTO> getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(List<DatiDocumentoDTO> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}

	public FindToWorkByAppDTO() {
		super();
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

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
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
