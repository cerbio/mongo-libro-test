package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class DatiProcedimentoFedDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "ID del procedimento")
	private String id;
	
	@Schema(description = "Numero del protocollo")
	private String numero;
	
	@Schema(description = "Tipologia procedimento")
	private String tipologia;
	
	@Schema(description = "Nome del procedimento")
	private String nomeProcedimento;
	
	@Schema(description = "Lista dei file presenti nel procedimento")
	private List<FileFedDTO> listaFileProcedimento;
	
	public DatiProcedimentoFedDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getNomeProcedimento() {
		return nomeProcedimento;
	}

	public void setNomeProcedimento(String nomeProcedimento) {
		this.nomeProcedimento = nomeProcedimento;
	}

	public List<FileFedDTO> getListaFileProcedimento() {
		return listaFileProcedimento;
	}

	public void setListaFileProcedimento(List<FileFedDTO> documenti) {
		this.listaFileProcedimento = documenti;
	}
	
	
	
}
