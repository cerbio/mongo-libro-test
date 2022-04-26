package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class TagsResponseDTO implements Serializable {

	private static final long serialVersionUID = 6669465522189492050L;

	@Schema(description = "ilista dei tag  tag")
	private List<TagResponseDTO> tags;

	@Schema(description = "numero Pagina")
	private int numeroPagina;

	@Schema(description = "numero Elementi Pagina")
	private int numeroElementiPagina;
	
	@Schema(description = "numero Elementi totali")
	private int numeroTotaleElementi;

	public TagsResponseDTO() {
		super();
	}

	public List<TagResponseDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagResponseDTO> tags) {
		this.tags = tags;
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

	public int getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(int numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

}
