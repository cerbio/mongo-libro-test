package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;

public class AclModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String utente;
	private String tipo;
	private Date dataCreazione;
	private boolean active;
	private String statoDocumento;
	
	public AclModel() {
		super();
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

}
