package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class TagRequestDTO implements Serializable{

	private static final long serialVersionUID = 6669465522189492050L;
	
	@Schema(description = "Nome del tag")
	private String nomeTag;
	
	@Schema(description = "Gruppo tag")
	private String idGruppo;

	@Schema(description = "numero Pagina")
	private Integer numeroPagina;
	
	@Schema(description = "numero Elementi Pagina")
	private Integer numeroElementiPagina;
	
	public TagRequestDTO() {
		super();
	}

	public String getNomeTag() {
		return nomeTag;
	}

	public void setNomeTag(String nomeTag) {
		this.nomeTag = nomeTag;
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
	
	public String getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	
}
