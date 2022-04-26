package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

public class DatiProcedimentoModel implements Serializable {

	private static final long serialVersionUID = -1851961459818614434L;

	private String id;	
	private String numero;	
	private String tipologia;
	private String nomeProcedimento;
	private List<FileDocumentoModel> documenti;
	
	public DatiProcedimentoModel() {
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
	public List<FileDocumentoModel> getDocumenti() {
		return documenti;
	}
	public void setDocumenti(List<FileDocumentoModel> documenti) {
		this.documenti = documenti;
	}
	
}
