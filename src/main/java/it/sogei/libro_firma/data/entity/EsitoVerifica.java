package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EsitoVerifica implements Serializable{

	private static final long serialVersionUID = -8415905728742524113L;
	
	private Date dataVerifica;
	private Boolean esito;
	private String utenteCreatore;
	private List<FileVerificaModel> listaFile;
	
	public EsitoVerifica() {
		super();
	}

	public Date getDataVerifica() {
		return dataVerifica;
	}

	public void setDataVerifica(Date dataVerifica) {
		this.dataVerifica = dataVerifica;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esitoVerifica) {
		this.esito = esitoVerifica;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public List<FileVerificaModel> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<FileVerificaModel> listaFile) {
		this.listaFile = listaFile;
	}

}
