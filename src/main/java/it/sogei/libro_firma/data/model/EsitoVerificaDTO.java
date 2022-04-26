package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EsitoVerificaDTO implements Serializable {

	private static final long serialVersionUID = -3466932313837001376L;
	
	private Date dataVerifica;
	private boolean esito;
	private String utenteCreatore;
	private List<FileVerificaDTO> listaFile;
	
	public EsitoVerificaDTO() {
		super();
	}

	public Date getDataVerifica() {
		return dataVerifica;
	}

	public void setDataVerifica(Date dataVerifica) {
		this.dataVerifica = dataVerifica;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public List<FileVerificaDTO> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<FileVerificaDTO> listaFile) {
		this.listaFile = listaFile;
	}

	
}
