package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Documento")
public class DocumentoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String nomeDocumento;
	private String tipoDocumento;
	private String utenteCreatore;
	private String statoDocumento;
	private String appProduttore;
	private String nomeAppProduttore;
	private Anagrafica mittente;
	private Date dataCreazione;
	private Date dataCaricamento;
	private Date dataInvio;
	private boolean urgente = false;
	private boolean scadenza = false;
	private Date dataScadenza;
	private boolean scaduto = false;
	private List<OperazioneModel> operazioni;
	private List<AclModel> acl;
	private int numeroDocumenti;
	private List<FileDocumentoModel> documenti;
	private List<TagDocumentoModel> tags;
	private List<FirmaLFUModel> firmeLfuApplicate;
	private String tipoFirmaDaApplicare;
	private Boolean verificato;
	private String idRichiesta;
	private AssegnazioneModel assegnazione;
	private List<Anagrafica> destinatari;
//	private Lock lock;
	private DatiProcedimentoModel datiProcedimento;
	private FascicoloModel fascicolo;
	
	public DocumentoModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeFile) {
		this.nomeDocumento = nomeFile;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public String getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public String getAppProduttore() {
		return appProduttore;
	}

	public void setAppProduttore(String appProduttore) {
		this.appProduttore = appProduttore;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<OperazioneModel> getOperazioni() {
		return operazioni;
	}

	public void setOperazioni(List<OperazioneModel> operazioni) {
		this.operazioni = operazioni;
	}

	public List<AclModel> getAcl() {
		return acl;
	}

	public void setAcl(List<AclModel> acl) {
		this.acl = acl;
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

	public List<FileDocumentoModel> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<FileDocumentoModel> documenti) {
		this.documenti = documenti;
	}

	public List<TagDocumentoModel> getTags() {
		return tags;
	}

	public void setTags(List<TagDocumentoModel> tags) {
		this.tags = tags;
	}

	public boolean isScaduto() {
		return scaduto;
	}

	public void setScaduto(boolean scaduto) {
		this.scaduto = scaduto;
	}

	public boolean isScadenza() {
		return scadenza;
	}

	public void setScadenza(boolean scadenza) {
		this.scadenza = scadenza;
	}

	public Anagrafica getMittente() {
		return mittente;
	}

	public void setMittente(Anagrafica mittente) {
		this.mittente = mittente;
	}

	public List<FirmaLFUModel> getFirmeLfuApplicate() {
		return firmeLfuApplicate;
	}

	public void setFirmeLfuApplicate(List<FirmaLFUModel> firmeLfuApplicate) {
		this.firmeLfuApplicate = firmeLfuApplicate;
	}

	public String getTipoFirmaDaApplicare() {
		return tipoFirmaDaApplicare;
	}

	public void setTipoFirmaDaApplicare(String tipoFirmaDaApplicare) {
		this.tipoFirmaDaApplicare = tipoFirmaDaApplicare;
	}

	public int getNumeroDocumenti() {
		return numeroDocumenti;
	}

	public void setNumeroDocumenti(int numeroDocumenti) {
		this.numeroDocumenti = numeroDocumenti;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getVerificato() {
		return verificato;
	}

	public void setVerificato(Boolean verificato) {
		this.verificato = verificato;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getNomeAppProduttore() {
		return nomeAppProduttore;
	}

	public void setNomeAppProduttore(String nomeAppProduttore) {
		this.nomeAppProduttore = nomeAppProduttore;
	}

	public AssegnazioneModel getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(AssegnazioneModel assegnatari) {
		this.assegnazione = assegnatari;
	}
	
	public List<Anagrafica> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<Anagrafica> destinatari) {
		this.destinatari = destinatari;
	}

//	public Lock getLock() {
//		return lock;
//	}
//
//	public void setLock(Lock lock) {
//		this.lock = lock;
//	}

	public DatiProcedimentoModel getDatiProcedimento() {
		return datiProcedimento;
	}

	public void setDatiProcedimento(DatiProcedimentoModel datiProcedimento) {
		this.datiProcedimento = datiProcedimento;
	}

	public FascicoloModel getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(FascicoloModel fascicolo) {
		this.fascicolo = fascicolo;
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
