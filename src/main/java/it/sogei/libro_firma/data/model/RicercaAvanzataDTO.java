package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public class RicercaAvanzataDTO implements Serializable {

	private static final long serialVersionUID = 8462358854847358248L;

	@Schema(description = "Nome Documento")
	private String nomeDocumento;

	@Schema(description = "Nome applicazione")
	private String nomeApplicazione;

	@Schema(description = "urgente")
	private Boolean urgente;

	@Schema(description = "Data caricamento da")
	private Date dataCaricamentoDa;

	@Schema(description = "Data caricamento a")
	private Date dataCaricamentoA;

	@Schema(description = "Data firma da")
	private Date dataFirmaDa;

	@Schema(description = "Data firma a")
	private Date dataFirmaA;

	@Schema(description = "Mittente")
	private String mittente;

	@Schema(description = "Stato documento")
	private String statoDocumento;

	@Schema(description = "Data scadenza da")
	private Date dataScadenzaDa;

	@Schema(description = "Data scadenza a")
	private Date dataScadenzaA;
	
	@Schema(description = "Assegnatario")
	private String assegnatario;
	
	@Schema(description = "Collaboratore")
	private String collaboratore;
	
	@Schema(description = "Id/numero protocollo")
	private String protocollo;
	
	@Schema(description = "Nome fascicolo")
	private String fascicolo;

	public RicercaAvanzataDTO() {
		super();
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getNomeApplicazione() {
		return nomeApplicazione;
	}

	public void setNomeApplicazione(String nomeApplicazione) {
		this.nomeApplicazione = nomeApplicazione;
	}

	public Boolean getUrgente() {
		return urgente;
	}

	public void setUrgente(Boolean urgente) {
		this.urgente = urgente;
	}

	public Date getDataCaricamentoDa() {
		return dataCaricamentoDa;
	}

	public void setDataCaricamentoDa(Date dataCaricamentoDa) {
		this.dataCaricamentoDa = dataCaricamentoDa;
	}

	public Date getDataCaricamentoA() {
		return dataCaricamentoA;
	}

	public void setDataCaricamentoA(Date dataCaricamentoA) {
		this.dataCaricamentoA = dataCaricamentoA;
	}

	public Date getDataFirmaDa() {
		return dataFirmaDa;
	}

	public void setDataFirmaDa(Date dataFirmaDa) {
		this.dataFirmaDa = dataFirmaDa;
	}

	public Date getDataFirmaA() {
		return dataFirmaA;
	}

	public void setDataFirmaA(Date dataFirmaA) {
		this.dataFirmaA = dataFirmaA;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public Date getDataScadenzaDa() {
		return dataScadenzaDa;
	}

	public void setDataScadenzaDa(Date dataScadenzaDa) {
		this.dataScadenzaDa = dataScadenzaDa;
	}

	public Date getDataScadenzaA() {
		return dataScadenzaA;
	}

	public void setDataScadenzaA(Date dataScadenzaA) {
		this.dataScadenzaA = dataScadenzaA;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public String getCollaboratore() {
		return collaboratore;
	}

	public void setCollaboratore(String collaboratore) {
		this.collaboratore = collaboratore;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(String fascicolo) {
		this.fascicolo = fascicolo;
	}

}
