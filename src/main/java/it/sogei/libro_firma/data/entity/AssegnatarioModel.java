package it.sogei.libro_firma.data.entity;

import java.io.Serializable;

public class AssegnatarioModel implements Serializable{

	private static final long serialVersionUID = -7966028680340214214L;

	private String utente;
	private Integer ordine;
	private boolean active;
	
	public AssegnatarioModel() {
		super();
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
