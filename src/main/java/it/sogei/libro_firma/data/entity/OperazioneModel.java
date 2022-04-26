package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;

public class OperazioneModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private String utente;
	private String stato;
	private String motivo;
	private Date dataCreazione;
	private Date dataCompletamento;
	private String utenteRicevente;
	private boolean active;
	
	public OperazioneModel() {
		super();
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getUtente() {
		return utente;
	}
	
	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public Date getDataCreazione() {
		return dataCreazione;
	}
	
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	public Date getDataCompletamento() {
		return dataCompletamento;
	}
	
	public void setDataCompletamento(Date dataCompletamento) {
		this.dataCompletamento = dataCompletamento;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUtenteRicevente() {
		return utenteRicevente;
	}

	public void setUtenteRicevente(String utenteRicevente) {
		this.utenteRicevente = utenteRicevente;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
