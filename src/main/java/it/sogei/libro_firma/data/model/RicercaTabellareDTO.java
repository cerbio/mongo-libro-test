package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;

public class RicercaTabellareDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Parametro di ricerca")
	private String param;
	
	@Schema(description = "Parametro di ricerca aggiuntivo")
	private String utente;
	
	@Schema(description = "Stato documento")
	private StatoDocumentoEnum statoDocumento;
	
	@Schema(description = "Campi da ordinare")
	private List<OrdinamentoFieldDTO> ordinamento;
	
	@Schema(description = "Numero pagina")
	private int numeroPagina;
	
	@Schema(description = "Numero elementi per pagina")
	private int numeroElementiPerPagina;

	public RicercaTabellareDTO() {
		super();
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public StatoDocumentoEnum getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(StatoDocumentoEnum statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public List<OrdinamentoFieldDTO> getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(List<OrdinamentoFieldDTO> ordinamento) {
		this.ordinamento = ordinamento;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	
	public int getNumeroElementiPerPagina() {
		return numeroElementiPerPagina;
	}

	public void setNumeroElementiPerPagina(int numeroElementiPerPagina) {
		this.numeroElementiPerPagina = numeroElementiPerPagina;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}
	
}
