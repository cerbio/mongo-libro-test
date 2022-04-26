package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;
import it.sogei.libro_firma.data.model.enums.StatoAssegnazioneEnum;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;
import it.sogei.libro_firma.data.model.enums.TipoFirmaEnum;
import it.sogei.libro_firma.data.model.enums.TipoUtenteEnum;

public class DatiDocumentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Nome del documento")
	private String nomeDocumento;
	
	@Schema(description = "Id mongo del documento")
	private String idDocumento;
	
	@Schema(description = "Id assegnato dall'ecm del documento")
	private String idEcm;
	
	@Schema(description = "Content type documento")
	private String contentType;
	
	@Schema(description = "Data creazione del documento")
	private Date dataCreazione;
	
	@Schema(description = "Data invio del documento")
	private Date dataInvio;
	
	@Schema(description = "Stato del documento")
	private StatoDocumentoEnum statoDocumento;
	
	@Schema(description = "Applicazione che ha prodotto il documento")
	private String codiceApplicazione;
	
	@Schema(description = "Nome applicazione che ha prodotto il documento")
	private String nomeApplicazione;
	
	@Schema(description = "Utente creatore del documento")
	private String utenteCreatore;
	
	@Schema(description = "Mittente per appFed")
	private MittenteDTO mittente;
	
	@Schema(description = "Data di scadenza del documento")
	private Date dataScadenza;
	
	@Schema(description = "Data di firma del documento")
	private Date dataFirma;
	
	@Schema(description = "Utente firmatario del documento")
	private String firmatario;
	
	@Schema(description = "Flag file o cartella")
	private boolean singleFile = true;
	
	@Schema(description = "Numero file se cartella")
	private long numeroFile = 0L;
	
	@Schema(description = "Flag urgente")
	private boolean urgente;
	
	@Schema(description = "Azione principale utente")
	private AzioneEnum azioneUtente;
	
	@Schema(description = "Altre azioni utente")
	private List<AzioneEnum> altreAzioniUtente;
	
	@Schema(description = "Ruolo utente loggato per il documento")
	private TipoUtenteEnum ruoloUtenteDocumento;
	
	@Schema(description = "File disponibili per un documento")
	private List<FileDocumentoDTO> documenti;
	
	@Schema(description = "Lista di tag associati al documento")
	private List<String> tags;
	
	@Schema(description = "Modalita firma attuata")
	private TipoFirmaEnum modFirmaAttuata;
	
	@Schema(description = "Tipo firma applicata")
	private FirmaEnum tipFirmaApplicata;
	
	@Schema(description = "Tipo firma da applicare (per APP federate)")
	private FirmaEnum tipoFirmaDaApplicare;
	
	@Schema(description = "Lista assegnatari documento")
	private List<String> assegnatari;
	
	@Schema(description = "Lista collaboratori documento")
	private List<String> collaboratori;
	
	@Schema(description = "Flag verifica effettuata (true=verificaOK, false=verificaKO, null=non verificato)")
	private Boolean verificato;
	
	@Schema(description = "flag assegnazione prioritaria")
	private Boolean assegnazionePrioritaria;
	
	@Schema(description = "Lista destinatari")
	private List<MittenteDTO> destinatari;
	
	@Schema(description = "Stato dell'assegnazione")
	private StatoAssegnazioneEnum statoAssegnazione;
	
	@Schema(description = "Dati del procedimento")
	private DatiProcedimento datiProcedimento = new DatiProcedimento();

	public DatiDocumentoDTO() {
		super();
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeFile) {
		this.nomeDocumento = nomeFile;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getIdEcm() {
		return idEcm;
	}

	public void setIdEcm(String idEcm) {
		this.idEcm = idEcm;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public StatoDocumentoEnum getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(StatoDocumentoEnum statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public AzioneEnum getAzioneUtente() {
		return azioneUtente;
	}

	public void setAzioneUtente(AzioneEnum azioneUtente) {
		this.azioneUtente = azioneUtente;
	}

	public boolean isSingleFile() {
		return singleFile;
	}

	public void setSingleFile(boolean singleFile) {
		this.singleFile = singleFile;
	}

	public long getNumeroFile() {
		return numeroFile;
	}

	public void setNumeroFile(long numeroFile) {
		this.numeroFile = numeroFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;

	}
		
	public List<FileDocumentoDTO> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<FileDocumentoDTO> documenti) {
		this.documenti = documenti;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public TipoFirmaEnum getModFirmaAttuata() {
		return modFirmaAttuata;
	}

	public void setModFirmaAttuata(TipoFirmaEnum modFirmaAttuata) {
		this.modFirmaAttuata = modFirmaAttuata;
	}

	public FirmaEnum getTipFirmaApplicata() {
		return tipFirmaApplicata;
	}

	public void setTipFirmaApplicata(FirmaEnum tipFirmaApplicata) {
		this.tipFirmaApplicata = tipFirmaApplicata;
	}

	public List<String> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<String> assegnatari) {
		this.assegnatari = assegnatari;
	}

	public List<String> getCollaboratori() {
		return collaboratori;
	}

	public void setCollaboratori(List<String> collaboratori) {
		this.collaboratori = collaboratori;
	}

	public List<AzioneEnum> getAltreAzioniUtente() {
		return altreAzioniUtente;
	}

	public void setAltreAzioniUtente(List<AzioneEnum> altreAzioniUtente) {
		this.altreAzioniUtente = altreAzioniUtente;
	}

	public Boolean getVerificato() {
		return verificato;
	}

	public void setVerificato(Boolean verificato) {
		this.verificato = verificato;
	}

	public FirmaEnum getTipoFirmaDaApplicare() {
		return tipoFirmaDaApplicare;
	}

	public void setTipoFirmaDaApplicare(FirmaEnum tipoFirmaDaApplicare) {
		this.tipoFirmaDaApplicare = tipoFirmaDaApplicare;
	}

	public String getNomeApplicazione() {
		return nomeApplicazione;
	}

	public void setNomeApplicazione(String nomeApplicazione) {
		this.nomeApplicazione = nomeApplicazione;
	}

	public MittenteDTO getMittente() {
		return mittente;
	}

	public void setMittente(MittenteDTO mittente) {
		this.mittente = mittente;
	}

	public Boolean getAssegnazionePrioritaria() {
		return assegnazionePrioritaria;
	}

	public void setAssegnazionePrioritaria(Boolean assegnazionePrioritaria) {
		this.assegnazionePrioritaria = assegnazionePrioritaria;
	}
	
	public List<MittenteDTO> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<MittenteDTO> destinatari) {
		this.destinatari = destinatari;
	}

	public StatoAssegnazioneEnum getStatoAssegnazione() {
		return statoAssegnazione;
	}

	public void setStatoAssegnazione(StatoAssegnazioneEnum statoAssegnazione) {
		this.statoAssegnazione = statoAssegnazione;
	}

	public DatiProcedimento getDatiProcedimento() {
		return datiProcedimento;
	}

	public void setDatiProcedimento(DatiProcedimento datiProcedimento) {
		this.datiProcedimento = datiProcedimento;
	}
	
	public TipoUtenteEnum getRuoloUtenteDocumento() {
		return ruoloUtenteDocumento;
	}

	public void setRuoloUtenteDocumento(TipoUtenteEnum ruoloUtenteDocumento) {
		this.ruoloUtenteDocumento = ruoloUtenteDocumento;
	}
	
	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	
}
