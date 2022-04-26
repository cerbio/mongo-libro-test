package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TagDTO implements Serializable {

	private static final long serialVersionUID = -6931235137687401411L;

	private String nome;
	private List<String> gruppi;
	private String utenteCreatore;
	private Date dataCreazione;
	private Date ultimaModifica;
		
	public TagDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUtenteCreatore() {
		return utenteCreatore;
	}
	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<String> getGruppi() {
		return gruppi;
	}

	public void setGruppi(List<String> gruppi) {
		this.gruppi = gruppi;
	}

	public Date getUltimaModifica() {
		return ultimaModifica;
	}

	public void setUltimaModifica(Date ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}
}
