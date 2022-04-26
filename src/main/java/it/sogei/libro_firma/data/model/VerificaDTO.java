package it.sogei.libro_firma.data.model;

import java.io.Serializable;

public class VerificaDTO implements Serializable{

	private static final long serialVersionUID = 1954089567939008461L;
	
	private String idDocumento;
	private boolean verificaValida;
	private EsitoVerificaDTO esito;
	
	public VerificaDTO() {
		super();
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String id) {
		this.idDocumento = id;
	}

	public boolean isVerificaValida() {
		return verificaValida;
	}

	public void setVerificaValida(boolean verificaFirma) {
		this.verificaValida = verificaFirma;
	}

	public EsitoVerificaDTO getEsito() {
		return esito;
	}

	public void setEsito(EsitoVerificaDTO esito) {
		this.esito = esito;
	}

	

}
