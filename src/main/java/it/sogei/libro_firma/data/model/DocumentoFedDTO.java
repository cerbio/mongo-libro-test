package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.sogei.libro_firma.data.model.enums.AzioneEnum;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;
import it.sogei.libro_firma.data.model.enums.StatoOperazioneEnum;


public class DocumentoFedDTO implements Serializable {

	private static final long serialVersionUID = 6069621594871363673L;
	
	private MittenteDTO mittente;
	private String nomeDocumento;
	private List<FileFedDTO> listaFile;
	private String idDocumento;
	private String utenteCreatore;
	private String utenteFirmatario;
	private String appProduttore;
	private String nomeAppProduttore;
	private StatoDocumentoEnum statoDocumento;
	private FirmaEnum tipoFirmaDaApplicare;
	private AzioneEnum azioneDaEffettuare;
	private boolean urgente;
	private Date dataScadenza;
	private Date dataOperazione;
	private String idRichiesta;
	private StatoOperazioneEnum statoOperazione;
	private List<MittenteDTO> destinatari;
	private DatiProcedimentoFedDTO datiProcedimento;
	private FascicoloFedDTO fascicolo;
	private Date dataCaricamento;
	private Date dataInvio;
	
	public DocumentoFedDTO() {
		super();
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public String getAppProduttore() {
		return appProduttore;
	}

	public void setAppProduttore(String appProduttore) {
		this.appProduttore = appProduttore;
	}

	public StatoDocumentoEnum getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(StatoDocumentoEnum statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public FirmaEnum getTipoFirmaDaApplicare() {
		return tipoFirmaDaApplicare;
	}

	public void setTipoFirmaDaApplicare(FirmaEnum tipoFirmaDaApplicare) {
		this.tipoFirmaDaApplicare = tipoFirmaDaApplicare;
	}

	public AzioneEnum getAzioneDaEffettuare() {
		return azioneDaEffettuare;
	}

	public void setAzioneDaEffettuare(AzioneEnum azioneDaEffettuare) {
		this.azioneDaEffettuare = azioneDaEffettuare;
	}

	public Boolean getUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public MittenteDTO getMittente() {
		return mittente;
	}

	public void setMittente(MittenteDTO mittente) {
		this.mittente = mittente;
	}

	public List<FileFedDTO> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<FileFedDTO> listaFile) {
		this.listaFile = listaFile;
	}

	public String getUtenteFirmatario() {
		return utenteFirmatario;
	}

	public void setUtenteFirmatario(String utenteFirmatario) {
		this.utenteFirmatario = utenteFirmatario;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Date getDataOperazione() {
		return dataOperazione;
	}

	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}

	public StatoOperazioneEnum getStatoOperazione() {
		return statoOperazione;
	}

	public void setStatoOperazione(StatoOperazioneEnum statoOperazione) {
		this.statoOperazione = statoOperazione;
	}

	public String getNomeAppProduttore() {
		return nomeAppProduttore;
	}

	public void setNomeAppProduttore(String nomeAppProduttore) {
		this.nomeAppProduttore = nomeAppProduttore;
	}

	public List<MittenteDTO> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<MittenteDTO> destinatari) {
		this.destinatari = destinatari;
	}

	public FascicoloFedDTO getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(FascicoloFedDTO fascicolo) {
		this.fascicolo = fascicolo;
	}

	public DatiProcedimentoFedDTO getDatiProcedimento() {
		return datiProcedimento;
	}

	public void setDatiProcedimento(DatiProcedimentoFedDTO datiProcedimento) {
		this.datiProcedimento = datiProcedimento;
	}
	
	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

}
