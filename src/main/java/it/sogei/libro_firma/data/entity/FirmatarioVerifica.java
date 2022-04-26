package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;

public class FirmatarioVerifica implements Serializable {

	private static final long serialVersionUID = 2225529961663318263L;
	
	private String nome;
	private String cognome;
	private String cf;
	private String tipoFirma;
	private String motivo;
	private String firmaValida;
	private Date dataFirma;
	private Date dataInizioValiditaCertificato;
	private Date dataFineValiditaCertificato;
	private String statoDelCertificato;
	private String algoritmoDigest;
	private String enteCertificatore;
		
	public FirmatarioVerifica() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(String firmaValida) {
		this.firmaValida = firmaValida;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}

	public Date getDataInizioValiditaCertificato() {
		return dataInizioValiditaCertificato;
	}

	public void setDataInizioValiditaCertificato(Date dataInizioValiditaCertificato) {
		this.dataInizioValiditaCertificato = dataInizioValiditaCertificato;
	}

	public Date getDataFineValiditaCertificato() {
		return dataFineValiditaCertificato;
	}

	public void setDataFineValiditaCertificato(Date dataFineValiditaCertificato) {
		this.dataFineValiditaCertificato = dataFineValiditaCertificato;
	}

	public String getStatoDelCertificato() {
		return statoDelCertificato;
	}

	public void setStatoDelCertificato(String statoDelCertificato) {
		this.statoDelCertificato = statoDelCertificato;
	}

	public String getAlgoritmoDigest() {
		return algoritmoDigest;
	}

	public void setAlgoritmoDigest(String algoritmoDigest) {
		this.algoritmoDigest = algoritmoDigest;
	}

	public String getEnteCertificatore() {
		return enteCertificatore;
	}

	public void setEnteCertificatore(String enteCertificatore) {
		this.enteCertificatore = enteCertificatore;
	}

}
