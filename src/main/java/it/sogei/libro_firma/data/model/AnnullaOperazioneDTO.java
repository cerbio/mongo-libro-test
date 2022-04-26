package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

public class AnnullaOperazioneDTO implements Serializable{

	private static final long serialVersionUID = 191177479040466301L;
	
	private String idDocumento;
	private List<String> utenti;
	private boolean invitoFirma;
	
	public AnnullaOperazioneDTO(){
		super();
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public List<String> getUtenti() {
		return utenti;
	}

	public void setUtenti(List<String> utenti) {
		this.utenti = utenti;
	}

	public boolean isInvitoFirma() {
		return invitoFirma;
	}

	public void setInvitoFirma(boolean invitoFirma) {
		this.invitoFirma = invitoFirma;
	}
	
}
