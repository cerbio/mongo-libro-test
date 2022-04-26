package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;

public class FirmatarioVerificaDTO implements Serializable{

	private static final long serialVersionUID = -8390416681366760765L;
	
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String tipologiaFirma;
	private String motivo;
	private Boolean firmaValida;
	private Date dataFirma;
	private Date dataInizioValiditaCertificato;
	private Date dataFineValiditaCertificato;
	private String statoDelCertificato;	
	private String algoritmoDigest;
	private String enteCertificatore;
	
	
	public FirmatarioVerificaDTO() {
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

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String cf) {
		this.codiceFiscale = cf;
	}

	public String getTipologiaFirma() {
		return tipologiaFirma;
	}

	public void setTipologiaFirma(String tipoFirma) {
		this.tipologiaFirma = tipoFirma;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Boolean isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(Boolean firmaValida) {
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
