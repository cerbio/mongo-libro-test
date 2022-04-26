package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class SearchResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Lista dei documenti")
	private List<DatiDocumentoDTO> listaDocumenti;
	
	@Schema(description = "Numero di pagine")
	private int numeroPagina;
	
	@Schema(description = "Numero di elementi per pagina")
	private int numeroElementiPagina;
	
	@Schema(description = "Numero totali di elementi")
	private int numeroElementiTotali;
	
	@Schema(description = "Filtri utilizzati")
	private FiltersDTO filtri;
	
	public SearchResultDTO() {
		super();
	}

	public List<DatiDocumentoDTO> getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(List<DatiDocumentoDTO> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public int getNumeroElementiPagina() {
		return numeroElementiPagina;
	}

	public void setNumeroElementiPagina(int numeroElementiPagina) {
		this.numeroElementiPagina = numeroElementiPagina;
	}

	public int getNumeroElementiTotali() {
		return numeroElementiTotali;
	}

	public void setNumeroElementiTotali(int numeroElementiTotali) {
		this.numeroElementiTotali = numeroElementiTotali;
	}

	public FiltersDTO getFiltri() {
		return filtri;
	}

	public void setFiltri(FiltersDTO filtri) {
		this.filtri = filtri;
	}
	
	
}
