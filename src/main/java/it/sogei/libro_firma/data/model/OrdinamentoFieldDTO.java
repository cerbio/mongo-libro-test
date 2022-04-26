package it.sogei.libro_firma.data.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.CampoOrdinamentoEnum;
import it.sogei.libro_firma.data.model.enums.TipoOrdinamentoEnum;

public class OrdinamentoFieldDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Nome campo da ordinare")
	private CampoOrdinamentoEnum nomeCampo;
	
	@Schema(description = "Tipo ordinamento")
	private TipoOrdinamentoEnum tipo;
	
	public OrdinamentoFieldDTO() {
		super();
	}

	public CampoOrdinamentoEnum getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(CampoOrdinamentoEnum nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public TipoOrdinamentoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoOrdinamentoEnum tipo) {
		this.tipo = tipo;
	}
	
}
