package it.sogei.libro_firma.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.sogei.libro_firma.data.entity.AclModel;
import it.sogei.libro_firma.data.entity.AssegnatarioModel;
import it.sogei.libro_firma.data.entity.AssegnazioneModel;
import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.entity.FileDocumentoModel;
import it.sogei.libro_firma.data.entity.Filtro;
import it.sogei.libro_firma.data.entity.FirmaLFUModel;
import it.sogei.libro_firma.data.entity.Lock;
import it.sogei.libro_firma.data.entity.OperazioneModel;
import it.sogei.libro_firma.data.entity.StagingModel;
import it.sogei.libro_firma.data.entity.VerificaModel;
import it.sogei.libro_firma.data.entity.VersioneModel;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.AnnullaOperazioneDTO;
import it.sogei.libro_firma.data.model.CondividiDocumentiDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoFedDTO;
import it.sogei.libro_firma.data.model.FascicoloDTO;
import it.sogei.libro_firma.data.model.FileDocumentoDTO;
import it.sogei.libro_firma.data.model.FileFedDTO;
import it.sogei.libro_firma.data.model.FiltroAvanzatoDTO;
import it.sogei.libro_firma.data.model.FirmaDocumentoDTO;
import it.sogei.libro_firma.data.model.FirmaLFUDto;
import it.sogei.libro_firma.data.model.LockDTO;
import it.sogei.libro_firma.data.model.MittenteDTO;
import it.sogei.libro_firma.data.model.NotificaDTO;
import it.sogei.libro_firma.data.model.StoricoOperazioniDTO;
import it.sogei.libro_firma.data.model.UpdateDocumentoDTO;
import it.sogei.libro_firma.data.model.VerificaDTO;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;
import it.sogei.libro_firma.data.model.enums.FiltriEnum;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;
import it.sogei.libro_firma.data.model.enums.StatoOperazioneEnum;
import it.sogei.libro_firma.data.model.enums.TipoOperazioneEnum;
import it.sogei.libro_firma.data.model.enums.TipoPermessoEnum;
import it.sogei.libro_firma.data.repository.DocumentoRepository;
import it.sogei.libro_firma.data.repository.LockRepository;
import it.sogei.libro_firma.data.repository.StagingRepository;
import it.sogei.libro_firma.data.repository.VerificaRepository;
import it.sogei.libro_firma.data.service.ILibroFirmaDataDocumentiService;
import it.sogei.libro_firma.data.service.impl.client.fed.LibroFirmaFedClient;
import it.sogei.libro_firma.data.service.impl.client.org.LibroFirmaOrgClient;
import it.sogei.libro_firma.data.util.DocumentoMapper;
import it.sogei.libro_firma.data.util.VerificaMapper;

@Service
public class LibroFirmaDataDocumentiService implements ILibroFirmaDataDocumentiService {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaDataDocumentiService.class);
	
	@Autowired
	private DocumentoRepository docRepository;
	
	@Autowired
	private VerificaRepository verificaRepository;
	
	@Autowired
	private LockRepository lockRepository;
	
	@Autowired
	private LibroFirmaOrgClient orgClient;
	
	@Autowired
	private LibroFirmaFedClient fedClient;
	
	@Autowired
	private StagingRepository stagingRepository;
	
	@Autowired
	private LibroFirmaDataFiltriService filtriService;
	
	@Autowired
	private LibroFirmaDataPermessiUtil permessiUtils;
	
	
	public LibroFirmaDataDocumentiService() {
		super();
	}
	
	/**
	 * 
	 * @param documentoDto
	 * @return
	 */
	@Override
	public String insertDocumento(DocumentoDTO documentoDto, String utenteCreatore) {
		log.info("insertDocumento: START - fileName={}, utenteCreatore={}", documentoDto.getNomeDocumento(), utenteCreatore);
		DocumentoModel docModel = new DocumentoModel();
		docModel.setNomeDocumento(documentoDto.getNomeDocumento());
		docModel.setStatoDocumento(documentoDto.getStatoDocumento().name());
		docModel.setUtenteCreatore(utenteCreatore);
		docModel.setAppProduttore(documentoDto.getAppProduttore());
		docModel.setNomeAppProduttore(documentoDto.getNomeAppProd());
		docModel.setDataCreazione(new Date());
		docModel.setDataCaricamento(docModel.getDataCreazione());
//		--docModel.setDataInvio(docModel.getDataCreazione());--
		
		docModel.setNumeroDocumenti(1);
		docModel.setTipoDocumento(getTipoDocumento(documentoDto.getNomeDocumento()));
		
		FileDocumentoModel fileModel = new FileDocumentoModel();
		fileModel.setContentType(documentoDto.getContentType());
		fileModel.setIdEcm(documentoDto.getIdEcm());
		fileModel.setNomeFile(documentoDto.getNomeDocumento());
		fileModel.setTipo("ORIGINALE");	
		fileModel.setDaFirmare(docModel.getStatoDocumento().equals("DA_FIRMARE"));
			
		docModel.setDocumenti(Arrays.asList(fileModel));
		
		// creo l'operazione di creazione e la completo
		addOperazione(docModel, TipoOperazioneEnum.CREAZIONE, utenteCreatore, null);
		completeOperazione(docModel, TipoOperazioneEnum.CREAZIONE, utenteCreatore);
		// creo l'operazione di firma
		// gestire condivisioni in firma
		addOperazione(docModel, TipoOperazioneEnum.FIRMA, utenteCreatore, null);
		// aggiungo l'utente alle ACL
		addAcl(docModel, TipoPermessoEnum.CREATORE, utenteCreatore, null);
		docRepository.insert(docModel);
		log.info("insertDocumento: END - fileName={}, utenteCreatore={}, id={}", documentoDto.getNomeDocumento(), utenteCreatore, docModel.getId());
		return docModel.getId();
	}
	
	/**
	 * 
	 * @param documento
	 * @return
	 */
	private String getTipoDocumento(String nomeDocumento) {
		try {
			return FilenameUtils.getExtension(nomeDocumento);
		}
		catch (Exception e) {
			log.error("DatiDocumentoMapper.getTipoDocumento: error - fileName={}", nomeDocumento, e);
		}
		return null;
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public String insertDocumentoFed(DocumentoFedDTO documentoDto, String username)	throws LibroFirmaDataServiceException {
		log.info("insertDocumentoFed: START - fileName={}, utenteCreatore={}", documentoDto.getNomeDocumento(), documentoDto.getUtenteCreatore());
		DocumentoModel docModel = new DocumentoModel();
		docModel.setNomeDocumento(documentoDto.getNomeDocumento());
		docModel.setStatoDocumento(documentoDto.getStatoDocumento().name());
		docModel.setUtenteCreatore(documentoDto.getUtenteCreatore());
		docModel.setAppProduttore(documentoDto.getAppProduttore());
		docModel.setNomeAppProduttore(documentoDto.getNomeAppProduttore());
		Date dataCreazione = new Date();
		docModel.setDataCaricamento(documentoDto.getDataCaricamento() != null ? documentoDto.getDataCaricamento() : dataCreazione);	
		docModel.setDataCreazione(dataCreazione);
		docModel.setDataInvio(documentoDto.getDataInvio() != null ? documentoDto.getDataInvio() : dataCreazione);
		docModel.setDataScadenza(documentoDto.getDataScadenza() != null ? documentoDto.getDataScadenza() : null);
		docModel.setUrgente(documentoDto.getUrgente());
		docModel.setMittente(DocumentoMapper.fromMittenteDtoToAnagrafica(documentoDto.getMittente()));
		docModel.setDatiProcedimento(DocumentoMapper.fromDatiProcedimentoFedDtoToDatiProcedimentoModel(documentoDto.getDatiProcedimento()));
		if(documentoDto.getDestinatari() != null && !documentoDto.getDestinatari().isEmpty()) {
			docModel.setDestinatari(new ArrayList<>());
			for(MittenteDTO destinatario : documentoDto.getDestinatari()) {
				docModel.getDestinatari().add(DocumentoMapper.fromMittenteDtoToAnagrafica(destinatario));
			}
		}
		List<FileDocumentoModel> listaFile = new ArrayList<>();
		docModel.setNumeroDocumenti(documentoDto.getListaFile().size());
		String nomeFile = docModel.getNumeroDocumenti() == 1 ? documentoDto.getListaFile().get(0).getNomeFile() : null;
		docModel.setTipoDocumento(StringUtils.isBlank(nomeFile) ? null : getTipoDocumento(nomeFile));
		for (FileFedDTO file : documentoDto.getListaFile()) {
			FileDocumentoModel fileModel = new FileDocumentoModel();
			fileModel.setContentType(file.getContentType());
			fileModel.setIdEcm(file.getIdEcm());
			fileModel.setNomeFile(file.getNomeFile());
			fileModel.setTipo("ORIGINALE");
			fileModel.setDaFirmare(file.isDaFirmare());
			fileModel.setIdFileFed(file.getIdFileFed());
			fileModel.setTipoFileFascicolo(file.getTipoFileFascicolo().name());
			listaFile.add(fileModel);
		}
		if(documentoDto.getFascicolo()!=null) {
			docModel.setFascicolo(DocumentoMapper.fromFascicoloFedDtoToFascicoloModel(documentoDto.getFascicolo()));
		}
		docModel.setIdRichiesta(documentoDto.getIdRichiesta());
		docModel.setDocumenti(listaFile);

		addOperazione(docModel, TipoOperazioneEnum.CREAZIONE, documentoDto.getUtenteCreatore(), null);
		completeOperazione(docModel, TipoOperazioneEnum.CREAZIONE, documentoDto.getUtenteCreatore());

		// aggiungo l'operazione di invito firma
		addOperazione(docModel, TipoOperazioneEnum.INVITO_FIRMA, documentoDto.getUtenteCreatore(), documentoDto.getUtenteFirmatario());
		completeOperazione(docModel, TipoOperazioneEnum.INVITO_FIRMA, documentoDto.getUtenteCreatore());
//		--OperazioneModel operazioneAperta = addOperazione(docModel, TipoOperazioneEnum.getByName(documentoDto.getAzioneDaEffettuare().name()) , documentoDto.getUtenteFirmatario());--

		addOperazione(docModel, TipoOperazioneEnum.getByName(documentoDto.getAzioneDaEffettuare().name()) , documentoDto.getUtenteFirmatario(), docModel.getUtenteCreatore());
		
		AssegnazioneModel assegnazione = new AssegnazioneModel();
		assegnazione.setPrioritario(false);
		AssegnatarioModel assegnatario = new AssegnatarioModel();
		assegnatario.setActive(true);
		assegnatario.setOrdine(1);
		assegnatario.setUtente(documentoDto.getUtenteFirmatario());
		List<AssegnatarioModel> assegnatari = new ArrayList<>();
		assegnatari.add(assegnatario);
		assegnazione.setAssegnatari(assegnatari);
		docModel.setAssegnazione(assegnazione);
		addAcl(docModel, TipoPermessoEnum.getByName(documentoDto.getAzioneDaEffettuare().name()), documentoDto.getUtenteFirmatario(), null);
		
		docModel.setTipoFirmaDaApplicare(documentoDto.getTipoFirmaDaApplicare() != null ? documentoDto.getTipoFirmaDaApplicare().name() : null);
		
		docRepository.insert(docModel);
		generaNotifica(docModel.getId(), documentoDto.getMittente(), documentoDto.getUtenteFirmatario(), AzioneEnum.INVITO_FIRMA);
		log.info("insertDocumentoFed: END - fileName={}, utenteCreatore={}, id={}", documentoDto.getNomeDocumento(), documentoDto.getUtenteCreatore(), docModel.getId());
		return docModel.getId();
	}

	/**
	 * 
	 * @param docModel
	 * @param tipoOperazione
	 * @param utenteCreatore
	 */
	private OperazioneModel completeOperazione(DocumentoModel docModel, TipoOperazioneEnum tipoOperazione, String utente) {
		for(OperazioneModel operazione : docModel.getOperazioni()) {
			if(tipoOperazione.name().equalsIgnoreCase(operazione.getTipo()) && utente.equalsIgnoreCase(operazione.getUtente()) && 
					StatoOperazioneEnum.APERTA.name().equalsIgnoreCase(operazione.getStato())) {
				operazione.setDataCompletamento(new Date());
				operazione.setStato(StatoOperazioneEnum.COMPLETATA.name());
				return operazione;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param docModel
	 * @param tipoPermesso
	 * @param utente
	 */
	private AclModel addAcl(DocumentoModel docModel, TipoPermessoEnum tipoPermesso, String utente, StatoDocumentoEnum statoDoc) {
		if(docModel.getAcl() == null) {
			docModel.setAcl(new ArrayList<>());
		}
		for(AclModel acl : docModel.getAcl()) {
			if(utente.equalsIgnoreCase(acl.getUtente())) {
				acl.setStatoDocumento(statoDoc != null ? statoDoc.name() : null);
				if(!TipoPermessoEnum.CREATORE.name().equalsIgnoreCase(acl.getTipo())) {
					acl.setTipo(tipoPermesso.name());
					acl.setActive(true);
				}
				return acl;
			}
		}
		AclModel aclNew = new AclModel();
		aclNew.setActive(true);
		aclNew.setDataCreazione(new Date());
		aclNew.setTipo(tipoPermesso.name());
		aclNew.setUtente(utente);
		aclNew.setStatoDocumento(statoDoc != null ? statoDoc.name() : null);
		docModel.getAcl().add(aclNew);
		return aclNew;
	}

	/**
	 * 
	 * @param docModel
	 * @param tipoOperazione
	 * @param ricevente 
	 * @param utenteCreatore
	 */
	private OperazioneModel addOperazione(DocumentoModel docModel, TipoOperazioneEnum tipoOperazione, String utente, String ricevente) {
		if(docModel.getOperazioni() == null) {
			docModel.setOperazioni(new ArrayList<>());
		}
		OperazioneModel operazione = new OperazioneModel();
		operazione.setActive(true);
		operazione.setDataCreazione(new Date());
		operazione.setTipo(tipoOperazione.name());
		operazione.setUtente(utente);
		operazione.setUtenteRicevente(ricevente);
		operazione.setStato(StatoOperazioneEnum.APERTA.name());
		docModel.getOperazioni().add(operazione);
		
		return operazione;
	}
	
	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void deleteDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.deleteDocumento: idDocumento={}, utente={}", idDocumento, username);
		DocumentoModel model = docRepository.findById(idDocumento).orElse(null);
		if(model == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		if(permessiUtils.getForDelete(username, model) || !model.getAppProduttore().equalsIgnoreCase("LFU")) {
			docRepository.delete(model);
			return;
		}
		throw new LibroFirmaDataServiceException("Azione non consentita sul documento " + model.getNomeDocumento(), HttpStatus.FORBIDDEN.value());
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void updateDocumento(UpdateDocumentoDTO updateDocumentoDTO, String username)	throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.updateDocumento: idDocumento={}, utente={}", updateDocumentoDTO.getIdDocumento(), username);
		Optional<DocumentoModel> optionalModel = docRepository.findById(updateDocumentoDTO.getIdDocumento());
		if (!optionalModel.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		DocumentoModel model = optionalModel.get();
		if (!permessiUtils.getForUpdate(username, model)) {
			throw new LibroFirmaDataServiceException("Azione non consentita sul documento " + model.getNomeDocumento(), HttpStatus.FORBIDDEN.value());
		}
		// setto le 23:59:59 alla data scadenza
		if(updateDocumentoDTO.getDataScadenza() != null) {
			updateDocumentoDTO.setDataScadenza(permessiUtils.getRangeDate(updateDocumentoDTO.getDataScadenza(), false));
		}
		model.setDataScadenza(updateDocumentoDTO.getDataScadenza());
		model.setScadenza(model.getDataScadenza() != null);
		model.setScaduto(model.getDataScadenza() != null && model.getDataScadenza().before(new Date()));
		if (updateDocumentoDTO.isUrgente() != null) {
			model.setUrgente(updateDocumentoDTO.isUrgente());
		}
		docRepository.save(model);
	}

	/** 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public DatiDocumentoDTO firmaDocumento(FirmaDocumentoDTO updateDocumentoDTO, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.firmaDocumento: idDocumento={}, utente={}", updateDocumentoDTO.getIdDocumento(), username);
		DocumentoModel model = docRepository.findById(updateDocumentoDTO.getIdDocumento()).orElse(null);
		if(model == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		// TODO commento da togliere quando il FE si allinea
//		if(model.getLock() == null) {
//			throw new LibroFirmaDataServiceException("Bloccare prima il documento", HttpStatus.FORBIDDEN.value());
//		}
		if(!permessiUtils.getForSign(username, model)) {
			throw new LibroFirmaDataServiceException("Firma non consentita sul documento " + model.getNomeDocumento(), HttpStatus.FORBIDDEN.value());
		}
		
		// Controllo che il numero dei file inviati siano effettivamente corrispondenti al numero di file sul documentos
		int numeroFile = model.getDocumenti().parallelStream().mapToInt(file -> file.isDaFirmare() ? 1 : 0).reduce((a,b) -> a+b).getAsInt();
		
		if (!updateDocumentoDTO.getTipoFirmaApplicata().equals(FirmaEnum.AUTOGRAFA) && updateDocumentoDTO.getListaFile().size() != numeroFile) {
			throw new LibroFirmaDataServiceException("Numero file firmati non corrispondente per il documento " + model.getNomeDocumento(), HttpStatus.FORBIDDEN.value());
		}
		
		elaboraFileFirma(updateDocumentoDTO, username, model);

		completeOperazione(model, TipoOperazioneEnum.FIRMA, username);
		abilitaSuccessivo(model, username);
		// set stato documento dopo aver completato l'operazione
		model.setStatoDocumento(checkOperazioniAperte(model, TipoOperazioneEnum.FIRMA) ? StatoDocumentoEnum.FIRMA_ATTESA.name() : StatoDocumentoEnum.FIRMATO.name());
		
		if(!username.equalsIgnoreCase(model.getUtenteCreatore())) {
			addAcl(model, TipoPermessoEnum.FIRMA, username, StatoDocumentoEnum.FIRMATO);
		}
		else {
			// se sta firmando il mittente, legge lo stato globale
			addAcl(model, TipoPermessoEnum.FIRMA, username, null);
		}
		
		// aggiungo la firma applicata
		if(model.getFirmeLfuApplicate() == null) {
			model.setFirmeLfuApplicate(new ArrayList<>());
		}
		FirmaLFUModel firmaApplicata = new FirmaLFUModel();
		firmaApplicata.setDataFirma(new Date());
		firmaApplicata.setUtente(username);
		if(updateDocumentoDTO.getModalitaFirmaAttuata()!=null) {
			firmaApplicata.setModalitaFirma(updateDocumentoDTO.getModalitaFirmaAttuata().name());
		}
		firmaApplicata.setTipoFirma(updateDocumentoDTO.getTipoFirmaApplicata().name());
		model.getFirmeLfuApplicate().add(firmaApplicata);
		model.setVerificato(null);
		//rimuovo un eveentuale lock del doc 
		lockRepository.deleteById(model.getId());
		docRepository.save(model);
		
		Optional<VerificaModel> checkVerifica = verificaRepository.findById(model.getId());
		if (checkVerifica.isPresent()) {
			checkVerifica.get().setVerificaValida(false);
			verificaRepository.save(checkVerifica.get());
		}
		
		// invio la notifica di avvenuta firma al mittente
		if(!username.equalsIgnoreCase(model.getUtenteCreatore())) {
			// mando la notifica di firma avvenuta solo se sono un assegnatario
			MittenteDTO mittente = new MittenteDTO();
			mittente.setCf(username);
			generaNotifica(updateDocumentoDTO.getIdDocumento(), mittente, model.getUtenteCreatore(), AzioneEnum.FIRMA);
		}
		
		// invio la notifica all'app federata
		notificaApplicazioneFederata(model, username);
		return DocumentoMapper.fromDocumentoModelToDatiDocumentoDTO(model, username);
	}

	/**
	 * @param updateDocumentoDTO
	 * @param username
	 * @param model
	 * @throws LibroFirmaDataServiceException
	 */
	private void elaboraFileFirma(FirmaDocumentoDTO updateDocumentoDTO, String username, DocumentoModel model)
			throws LibroFirmaDataServiceException {
		FileDocumentoModel filePrincipale = null;
		String nomeDoc = null;
		for(FileDocumentoDTO file : updateDocumentoDTO.getListaFile()) {
			if(StringUtils.isNoneBlank(file.getIdEcm())) {
				
				VersioneModel versione = DocumentoMapper.fromFileDocumentoDTOToVersioneModel(file);
								
				versione.setUtenteCreatore(username);
				versione.setTipo("FIRMATA");
				
				FileDocumentoModel fileModel = getFileOriginale(model, file);
				if (fileModel.getTipoFileFascicolo() != null && fileModel.getTipoFileFascicolo().equalsIgnoreCase("PRINCIPALE") || fileModel.getTipoFileFascicolo() == null) {
					filePrincipale = fileModel;
					nomeDoc = versione.getNomeFile();
				}
				if(fileModel.getVersioni() == null) {
					fileModel.setVersioni(new ArrayList<>());
				}
				fileModel.getVersioni().add(versione);
			}
		}
		
		if (filePrincipale != null && filePrincipale.getNomeFile().equalsIgnoreCase(model.getNomeDocumento())) {
			model.setNomeDocumento(nomeDoc);
			model.setTipoDocumento(getTipoDocumento(nomeDoc));
		}
	}

	/**
	 * @param model
	 * @param file
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	private FileDocumentoModel getFileOriginale(DocumentoModel model, FileDocumentoDTO file)
			throws LibroFirmaDataServiceException {
		Optional<FileDocumentoModel> fileOpt = model.getDocumenti().stream().filter(f -> {
			if (f.getIdEcm().equals(file.getIdEcmOriginale()))
				return true;
			if(f.getVersioni()==null) 
				return false;
			return f.getVersioni().parallelStream().anyMatch(v -> v.getIdEcm().equals(file.getIdEcmOriginale()));			
		}).findFirst();
		if(!fileOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("File non trovato", HttpStatus.NOT_FOUND.value());
		}
		FileDocumentoModel fileModel = fileOpt.get();
		return fileModel;
	}

	/**
	 * 
	 * @param model
	 * @throws LibroFirmaDataServiceException 
	 */
	private void abilitaSuccessivo(DocumentoModel model, String utente){

		if(model.getAssegnazione()== null || model.getAssegnazione().getAssegnatari() == null || model.getAssegnazione().getAssegnatari().isEmpty())
			return;
		
		if(Boolean.FALSE.equals(model.getAssegnazione().isPrioritario()))
			return;
			
		AssegnatarioModel utenteSuccessivo = null;
		boolean take = false;
		// Recupero l'utente successivo
		for (AssegnatarioModel ass : model.getAssegnazione().getAssegnatari()) {
			if (take) {
				utenteSuccessivo = ass;
				break;
			}
			if (ass.getUtente().equals(utente)) {
				take = true;
			}
		}
		
		// Se non esiste un utente successivo setto a null tutte le ACL (lo stato documento a FIRMATO viene settato in firmaDocumento())
		if (utenteSuccessivo == null) {
			model.getAcl().stream().forEach(aclTemp -> {
				if (!StatoDocumentoEnum.CONDIVISO.name().equals(aclTemp.getStatoDocumento()))
						aclTemp.setStatoDocumento(null);
			});
			return;
		}
		
		// Se l'utente che ha appena firmato è l'utente creatore setto lo stato dell'ACL a NULL 
		if (utente.equals(model.getUtenteCreatore())) {
//			model.getAcl().stream().filter(a -> a.getUtente().equals(utente)).findFirst().ifPresent(a -> a.setStatoDocumento(StatoDocumentoEnum.FIRMA_ATTESA.name()));
			model.getAcl().stream().filter(a -> a.getUtente().equals(utente)).findFirst().ifPresent(a -> a.setStatoDocumento(null));
		}
		
		
		String cfUtenteSuccessivo = utenteSuccessivo.getUtente();
		if(cfUtenteSuccessivo.equals(model.getUtenteCreatore())) {
			// se l'utente creatore è il successivo setto lo stato del documento a DA_FIRMARE
			model.getAcl().stream().filter(aclTemp -> aclTemp.getUtente().equals(cfUtenteSuccessivo)).findFirst().ifPresent(aclTemp -> aclTemp.setStatoDocumento(StatoDocumentoEnum.DA_FIRMARE.name()));
		} else {
			// Setto a true l'ACL dell'utente successivo
			model.getAcl().stream().filter(aclTemp -> aclTemp.getUtente().equals(cfUtenteSuccessivo)).findFirst().ifPresent(aclTemp -> aclTemp.setActive(true));
		}
	
		addOperazione(model, TipoOperazioneEnum.FIRMA, cfUtenteSuccessivo, model.getUtenteCreatore());
		MittenteDTO mittente = new MittenteDTO();
		mittente.setCf(model.getUtenteCreatore());
		generaNotifica(model.getId(), mittente, cfUtenteSuccessivo, AzioneEnum.INVITO_FIRMA);
	}

	/**
	 * 
	 * @param model
	 * @param username
	 */
	private void notificaApplicazioneFederata(DocumentoModel model, String username) {
		if(!"LFU".equalsIgnoreCase(model.getAppProduttore())) {
			/**
			OperazioneCompletataDTO operazioneCompletata = new OperazioneCompletataDTO();
			operazioneCompletata.setIdDocumento(model.getId());
			operazioneCompletata.setAzioneDaEffettuare(AzioneEnum.FIRMA.name());
			operazioneCompletata.setIdRichiesta(model.getIdRichiesta());
			operazioneCompletata.setStatoOperazione(StatoOperazioneEnum.COMPLETATA);
			operazioneCompletata.setUtenteAssegnatario(username);
			operazioneCompletata.setDataOperazione(new Date());
			operazioneCompletata.setFiles(getFilesFed(model.getDocumenti()));
			*/
			try {
//				--fedClient.notificaApplicazioneFederata(operazioneCompletata);--
				fedClient.notificaApplicazioneFederata(model.getId());
			}
			catch(Exception e) {
				log.error("LibroFirmaDataDocumentiService.notificaApplicazioneFederata: idDocumento={}", model.getId(), e);
			}
		}
	}

	/**
	 * 
	 * @param documenti
	 * @return
	 
	private List<FileFedDTO> getFilesFed(List<FileDocumentoModel> documenti) {
		List<FileFedDTO> listaFile = new ArrayList<>();
		for(FileDocumentoModel documento : documenti) {
			FileFedDTO file = getFileFed(documento);
			listaFile.add(file);
		}
		return listaFile;
	}
	*/

	/**
	 * 
	 * @param documento
	 * @return
	 
	private FileFedDTO getFileFed(FileDocumentoModel documento) {
		FileFedDTO file = new FileFedDTO();
		file.setIdFileFed(documento.getIdFileFed());
		file.setTipoFileFascicolo(TipoFileEnum.getByName(documento.getTipoFileFascicolo()));
		VersioneModel ultimaVersione = getUltimaVersione(documento);
		file.setDaFirmare(true);
		file.setContentType(ultimaVersione != null ? ultimaVersione.getContentType() : documento.getContentType());
		file.setIdEcm(ultimaVersione != null ? ultimaVersione.getIdEcm() : documento.getIdEcm());
		file.setNomeFile(ultimaVersione != null ? ultimaVersione.getNomeFile() : documento.getNomeFile());
		return file;
	}
	*/

	/**
	 * 
	 * @param documento
	 * @return
	 
	private VersioneModel getUltimaVersione(FileDocumentoModel documento) {
		if(documento.getVersioni() != null && !documento.getVersioni().isEmpty()) {
			return documento.getVersioni().get(documento.getVersioni().size() - 1);
		}
		return null;
	}
	*/

	/**
	 * Restituisce true se, per il documento, esiste almeno una operazione aperta del tipo indicato
	 * @param model
	 * @param firma
	 * @return
	 */
	private boolean checkOperazioniAperte(DocumentoModel model, TipoOperazioneEnum tipoOperazione) {
		for(OperazioneModel op : model.getOperazioni()) {
			if(tipoOperazione.name().equalsIgnoreCase(op.getTipo()) && StatoOperazioneEnum.APERTA.name().equalsIgnoreCase(op.getStato())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param idDocumento
	 * @return
	 */
	@Override
	public List<StoricoOperazioniDTO> getStoricoOperazioniDocumento(String idDocumento) throws LibroFirmaDataServiceException{
		log.info("getStoricoOperazioniDocumento: START - idDocumento={}", idDocumento);
		
		Optional<DocumentoModel> opDocumento = docRepository.findById(idDocumento);
		DocumentoModel documento;
		if (!opDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
			
		}
		documento = opDocumento.get();
		List<StoricoOperazioniDTO> storicoOperazioni = new ArrayList<>();
		for(OperazioneModel op : documento.getOperazioni()) {
			if(StatoOperazioneEnum.COMPLETATA.name().equalsIgnoreCase(op.getStato()) ||
					op.getDataCompletamento() != null) {
				// solo le operazioni completate vengono inserite nello storico operazioni
				StoricoOperazioniDTO dto = DocumentoMapper.fromOperazioneModelToStoricoOperazioniDTO(op);
				
				if(documento.getMittente() != null) {
					if(dto.getCf().equalsIgnoreCase(documento.getMittente().getCf())) {
						dto.setNome(documento.getMittente().getNome());
						dto.setCognome(documento.getMittente().getCognome());
					}
					if(dto.getRicevente() != null && dto.getRicevente().getCf().equalsIgnoreCase(documento.getMittente().getCf())) {
						dto.getRicevente().setNome(documento.getMittente().getNome());
						dto.getRicevente().setCognome(documento.getMittente().getCognome());
					}
				}
				
				storicoOperazioni.add(dto);
			}
		}
		Collections.sort(storicoOperazioni);
		return storicoOperazioni;
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public Boolean checkFirmaFed(String idDocumento, String appCode, String username) throws LibroFirmaDataServiceException{
		log.info("LibroFirmaDataDocumentiService.checkFirmaFed: idDocumento={}, appCode={}, username={}", idDocumento, appCode, username);
		Optional<DocumentoModel> opDocumento = docRepository.findById(idDocumento);
		DocumentoModel documento;
		if (!opDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
			
		}
		documento = opDocumento.get();
		if (!documento.getAppProduttore().equals(appCode))
			return false;
		else
			return documento.getOperazioni().stream().noneMatch(operazione -> (operazione.getTipo().equals("FIRMA") && operazione.getStato().equals("COMPLETATA")));
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public List<String> getAllIdEcm(String idDocumento, String username) throws LibroFirmaDataServiceException{
		log.info("LibroFirmaDataDocumentiService.getAllIdEcm: idDocumento={}, username={}", idDocumento, username);
		Optional<DocumentoModel> opDocumento = docRepository.findById(idDocumento);
		DocumentoModel documento;
		if (!opDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		documento = opDocumento.get();
		List<String> idsEcm = new ArrayList<>();
		
		documento.getDocumenti().stream().forEach(doc -> {
			idsEcm.add(doc.getIdEcm());
			if (doc.getVersioni() != null && !doc.getVersioni().isEmpty()) {
				doc.getVersioni().stream().forEach(versione -> idsEcm.add(versione.getIdEcm()));
			}
		});
		
		return idsEcm;
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public List<String> getAllIdEcmVerificaFirma(String idDocumento, String username) throws LibroFirmaDataServiceException{
		log.info("LibroFirmaDataDocumentiService.getAllIdEcmVerificaFirma: idDocumento={}, username={}", idDocumento, username);
		Optional<DocumentoModel> opDocumento = docRepository.findById(idDocumento);
		DocumentoModel documento;
		if (!opDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		documento = opDocumento.get();
		List<String> idsEcm = new ArrayList<>();
		documento.getDocumenti().stream().forEach(doc -> {
			if (doc.isDaFirmare()) {
				if (doc.getVersioni() != null && !doc.getVersioni().isEmpty()) {
					idsEcm.add(doc.getVersioni().get(doc.getVersioni().size()-1).getIdEcm());
				} else 
					idsEcm.add(doc.getIdEcm());
			}
		});
		
		return idsEcm;
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public Boolean checkFirmaApplicabile(String idDoc, FirmaEnum tipoFirma, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.checkFirmaFed: idDocumento={},tipoFirma={}, username={}", idDoc, tipoFirma, username);
		DocumentoModel documento;
		Optional<DocumentoModel> opDocumento = docRepository.findById(idDoc);
		if(!opDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		documento = opDocumento.get();
		return StringUtils.isBlank(documento.getTipoFirmaDaApplicare()) || 
				tipoFirma.name().equalsIgnoreCase(documento.getTipoFirmaDaApplicare());
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public DocumentoFedDTO getDocumentoFed(String idDocumento) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getDocumento: idDocumento={}", idDocumento);
		DocumentoModel documento;
		Optional<DocumentoModel> opDocumento = docRepository.findById(idDocumento);
		if(!opDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		documento = opDocumento.get();
		
		return DocumentoMapper.fromDocumentoModelToDocumentoFedDto(documento);
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public DocumentoFedDTO getDocumentoFed(String idDocumento,String idRichiesta) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getDocumento: idDocumento={}", idDocumento);
		DocumentoModel documento = docRepository.findByIdAndRichiesta(idDocumento,idRichiesta);
		if(documento == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		
		return DocumentoMapper.fromDocumentoModelToDocumentoFedDto(documento);
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public Boolean checkVerificaFirma(String idDoc, String cf) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.checkUtenteVerificaFirma: idDocumento={}, utente={}", idDoc,cf);
		
		DocumentoModel documentoModel = docRepository.findById(idDoc).orElse(null);
		if (documentoModel == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		
		return documentoModel.getFirmeLfuApplicate() != null && !documentoModel.getFirmeLfuApplicate().isEmpty();
//		--if--(documentoModel.getFirmeLfuApplicate() != null && !documentoModel.getFirmeLfuApplicate().isEmpty()) {--
//			--return true;--
//		--}--
//		--throw new LibroFirmaDataServiceException("Il documento con id " + idDoc + " non è stato ancora firmato");--
		/**
		if (docRepository.existsById(idDoc)) {
			boolean checkUtente = docRepository.checkUtenteVerificaFirma(idDoc,cf);
			if(!checkUtente){
				throw new LibroFirmaDataServiceException("Utente non abilitato alla firma del documento con id " + idDoc);
			}
			boolean checkFirma = docRepository.checkFirmaSuDocumento(idDoc);
			if (!checkFirma) {
				throw new LibroFirmaDataServiceException("Il documento con id " + idDoc + " non è stato ancora firmato");
			}
			return true;
		}
		throw new LibroFirmaDataServiceException("Documento non trovato");
		*/
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void insertVerifica(VerificaDTO verificaDTO, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.insertVerifica: idDocumento={}, utente={}", verificaDTO.getIdDocumento(),username);
		DocumentoModel documentoModel = docRepository.findById(verificaDTO.getIdDocumento()).orElse(null);
		if (documentoModel == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		VerificaModel model; 
		Optional<VerificaModel> checkModel = verificaRepository.findById(verificaDTO.getIdDocumento());
		if (checkModel.isPresent()) {
			model = checkModel.get();
			VerificaModel modelToAdd = VerificaMapper.fromVerificaDtoToVerificaModel(verificaDTO, username);
			model.getEsitiVerifica().addAll(modelToAdd.getEsitiVerifica());
//			model.setVerificaValida(modelToAdd.isVerificaValida());
			model.setVerificaValida(verificaDTO.isVerificaValida());
			verificaRepository.save(model);
		}
		else {
			model = VerificaMapper.fromVerificaDtoToVerificaModel(verificaDTO, username);
			verificaRepository.insert(model);
		}
		
		// salvo il documento come verificato (true=verificaOK, false=verificaKO)
		documentoModel.setVerificato(verificaDTO.isVerificaValida());
		docRepository.save(documentoModel);
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void condividiDocumenti(CondividiDocumentiDTO condividiDocumentiDto, String username) throws LibroFirmaDataServiceException {

		log.info("condividiDocumenti: START");

		for (String idDocumento : condividiDocumentiDto.getListaDocumenti()) {
			if (!condividiDocumentiDto.getInvitoFirma()) {
				condivisione(idDocumento, condividiDocumentiDto, username);
			} else {	
				invitoFirma(idDocumento, condividiDocumentiDto, username, condividiDocumentiDto.getPriorita());	
			}
		}
		log.info("condividiDocumenti: END");
	}
	
	/**
	 * 
	 * @param idDocumento
	 * @param condividiDocumentiDto
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	private void condivisione(String idDocumento, CondividiDocumentiDTO condividiDocumentiDto, String username) throws LibroFirmaDataServiceException {
		DocumentoModel model = docRepository.findById(idDocumento).orElse(null);
		if (model == null) {
			throw new LibroFirmaDataServiceException("Documento " + idDocumento + " non trovato", HttpStatus.NOT_FOUND.value());
		}
		
		// aggiungo i vari utenti nelle acl del documento con id IdDocumento
		for (String cfUtente : condividiDocumentiDto.getListaUtenti()) {
			addOperazione(model, TipoOperazioneEnum.CONDIVISIONE, username, cfUtente);
			completeOperazione(model, TipoOperazioneEnum.CONDIVISIONE, username);
			addAcl(model, TipoPermessoEnum.LETTURA, cfUtente, StatoDocumentoEnum.CONDIVISO);
			
			MittenteDTO mittente = new MittenteDTO();
			mittente.setCf(username);
			generaNotifica(idDocumento, mittente, cfUtente,AzioneEnum.CONDIVISIONE);
		}
		docRepository.save(model);
	}

	
	/**
	 * 
	 * @param idDocumento
	 * @param condividiDocumentiDto
	 * @param username
	 * @param priorita
	 * @throws LibroFirmaDataServiceException
	 */
	private void invitoFirma(String idDocumento, CondividiDocumentiDTO condividiDocumentiDto, String username, boolean priorita) throws LibroFirmaDataServiceException {
		DocumentoModel model = docRepository.findById(idDocumento).orElse(null);
		if (model == null) {
			throw new LibroFirmaDataServiceException("Documento " + idDocumento + " non trovato", HttpStatus.NOT_FOUND.value());
		}
		AssegnazioneModel assegnazione;
		if (model.getAssegnazione() == null) {
			assegnazione = new AssegnazioneModel();
			model.setAssegnazione(assegnazione);
			model.getAssegnazione().setAssegnatari(new ArrayList<>());
		}
		else {
			assegnazione = model.getAssegnazione();
		}
		if (assegnazione.isPrioritario() == null) {
			assegnazione.setPrioritario(priorita);
		}
		
		boolean primo;
		if (assegnazione.getAssegnatari().isEmpty() || assegnazione.getAssegnatari().get(assegnazione.getAssegnatari().size()-1).isActive()) {
			primo = true;
		}
		else {
			primo = false;
		}
		
		List<AssegnatarioModel> assegnatari = new ArrayList<>(assegnazione.getAssegnatari().size());
		for(String utente : condividiDocumentiDto.getListaUtenti()){
			AssegnatarioModel assModel = new AssegnatarioModel();
			assModel.setUtente(utente);
			if (priorita) {
				assModel.setOrdine(assegnazione.getAssegnatari().size() + 1 + assegnatari.size());
			} else {
				assModel.setOrdine(null);
			}
			assModel.setActive(true);
			assegnatari.add(assModel);
		}
		
		//Settiamo lo stato documento a FIRMA_ATTESA
		model.setStatoDocumento(StatoDocumentoEnum.FIRMA_ATTESA.name());
		
		//Settiamo lo stato dell'acl dell'utente creatore a null, così da prendere lo stato globale 
		setAclStatus(model, model.getUtenteCreatore(), null);
//		--model.getAcl().get(0).setStatoDocumento(StatoDocumentoEnum.FIRMA_ATTESA.name());--
		
		//Completiamo l'operazione di invito alla firma 
		completeInvitoFirma(username, priorita, model, primo, assegnatari);
		
		assegnazione.getAssegnatari().addAll(assegnatari);
		// lasciamo operazione di firma per l'utente creatore
		eliminaOperazione(model, TipoOperazioneEnum.FIRMA, username);
		// setto lo stato documento per il mittente come FIRMA_ATTESA
		docRepository.save(model);
	}

	/**
	 * 
	 * @param model
	 * @param utente
	 * @param statoDoc
	 */
	private void setAclStatus(DocumentoModel model, String utente, StatoDocumentoEnum statoDoc) {
		for(AclModel acl : model.getAcl()) {
			if(acl.getUtente().equalsIgnoreCase(utente)) {
				acl.setStatoDocumento(statoDoc != null ? statoDoc.name() : null);
			}
		}
	}

	/**
	 * @param username
	 * @param priorita
	 * @param model
	 * @param primo
	 * @param assegnatari
	 */
	private void completeInvitoFirma( String username, boolean priorita, DocumentoModel model,boolean primo, List<AssegnatarioModel> assegnatari) {
		for(AssegnatarioModel utente : assegnatari) {
			boolean utenteCreatore = false;
			addOperazione(model, TipoOperazioneEnum.INVITO_FIRMA, username,utente.getUtente());
			completeOperazione(model, TipoOperazioneEnum.INVITO_FIRMA, username);
			MittenteDTO mittente = new MittenteDTO();
			mittente.setCf(username);
			if (priorita) {
				if (utente.getUtente().equals(model.getUtenteCreatore()))
					utenteCreatore = true;
				
				if (!utenteCreatore)
					addAcl(model, TipoPermessoEnum.FIRMA, utente.getUtente(), StatoDocumentoEnum.DA_FIRMARE).setActive(primo);
				else
					model.getAcl().get(0).setStatoDocumento(StatoDocumentoEnum.FIRMA_ATTESA.name());
				
				if (primo) {
					addOperazione(model, TipoOperazioneEnum.FIRMA,utente.getUtente(),username).setActive(primo);
					if (utenteCreatore) {
						model.getAcl().get(0).setStatoDocumento(StatoDocumentoEnum.DA_FIRMARE.name());
					}
					else {
						generaNotifica(model.getId(), mittente, utente.getUtente(), AzioneEnum.INVITO_FIRMA);
					}
				}
				
				primo=false;
			}
			else {
				addAcl(model, TipoPermessoEnum.FIRMA, utente.getUtente(), StatoDocumentoEnum.DA_FIRMARE).setActive(primo);
				addOperazione(model, TipoOperazioneEnum.FIRMA, utente.getUtente(),username).setActive(true);
				generaNotifica(model.getId(), mittente, utente.getUtente(), AzioneEnum.INVITO_FIRMA);
			}
		}
	}
	
	/**
	 * 
	 * @param model
	 * @param firma
	 * @param username
	 */
	private void eliminaOperazione(DocumentoModel model, TipoOperazioneEnum tipoOperazione, String username) {
		int i = -1;
		for(OperazioneModel op : model.getOperazioni()) {
			if(tipoOperazione.name().equalsIgnoreCase(op.getTipo()) && StatoOperazioneEnum.APERTA.name().equalsIgnoreCase(op.getStato()) && username.equalsIgnoreCase(op.getUtente())) {
				i = model.getOperazioni().indexOf(op);
				break;
			}
		}
		if(i >= 0) {
			model.getOperazioni().remove(i);
		}
	}
	

	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @param cfUtente
	 * @param azione
	 * @throws LibroFirmaDataServiceException
	 */
	private void generaNotifica(String idDocumento, MittenteDTO utenteOperazione, String utenteDestinatario, AzioneEnum azione) {
		try {
			Optional<DocumentoModel> documento = docRepository.findById(idDocumento);
			if(!documento.isPresent()) {
				throw new LibroFirmaDataServiceException("documento non trovato");
			}
			DocumentoModel docModel = documento.get();
			NotificaDTO notificaDto = new NotificaDTO();
			notificaDto.setAzione(azione);
			notificaDto.setIdDocumento(idDocumento);
			notificaDto.setNomeDocumento(docModel.getNomeDocumento());
			notificaDto.setUtenteDestinatario(utenteDestinatario);
			notificaDto.setUtenteOperazione(utenteOperazione.getCf());
			notificaDto.setCognomeUtenteOperazione(utenteOperazione.getCognome());
			notificaDto.setNomeUtenteOperazione(utenteOperazione.getNome());
			notificaDto.setAppFed(!"LFU".equalsIgnoreCase(docModel.getAppProduttore()));
			orgClient.inserisciNotifica(notificaDto);
		}
		catch (LibroFirmaDataServiceException e) {
			log.error("Errore in inserimento notifica");
		}
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public VerificaDTO getEsitoVerificaFirma(String idDocumento, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getEsitoVerificaFirma: idDocumento={}, utente={}", idDocumento,username);
		Optional<VerificaModel> responseOpt = verificaRepository.findById(idDocumento);
		if(!responseOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("Esito non trovato");
		}
		VerificaModel response = responseOpt.get();
		return VerificaMapper.fromVerificaModelToVerificaDto(response);
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public DatiDocumentoDTO getDocumento(String id,String utente) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getDocumento: idDocumento={}", id);
		Optional<DocumentoModel> modelOpt = docRepository.findById(id);
		if(!modelOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		DocumentoModel model = modelOpt.get();
		return DocumentoMapper.fromDocumentoModelToDatiDocumentoDTO(model, utente);
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public List<FirmaLFUDto> getFirmeDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getFirmeDocumento: idDocumento={}, utente={}", idDocumento,username);
		Optional<DocumentoModel> modelOpt = docRepository.findById(idDocumento);
		if(!modelOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		DocumentoModel model = modelOpt.get();
		return DocumentoMapper.fromFirmaLFUModelListToFirmaLFUDtoList(model.getFirmeLfuApplicate());
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void annullaCondivisione(AnnullaOperazioneDTO operazioneDto, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getFirmeDocumento: idDocumento={}, utente={}", operazioneDto.getIdDocumento(),username);
		Optional<DocumentoModel> modelOpt = docRepository.findById(operazioneDto.getIdDocumento());
		if(!modelOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		DocumentoModel model = modelOpt.get();
		
		if (operazioneDto.isInvitoFirma()) {
			removeAssegnatari(operazioneDto.getUtenti(), model, username);
		}
		else {
			removeCollaboratori(operazioneDto.getUtenti(), model, username);
		}
		docRepository.save(model);
		
	}
	
	/**
	 * 
	 * @param utenti
	 * @param model
	 * @throws LibroFirmaDataServiceException 
	 */
	private void removeAssegnatari(List<String> utenti, DocumentoModel doc,String utenteCreatore) throws LibroFirmaDataServiceException {
		List<AclModel> listaAcl = doc.getAcl();
		for(String utente : utenti) {
			AclModel acl = null;
			Optional<AclModel> aclOpt = listaAcl.stream().filter(aclModel -> (aclModel.getUtente().equals(utente))).findFirst();
			if (aclOpt.isPresent())
				acl = aclOpt.get();
			else
				throw new LibroFirmaDataServiceException("Nessuna acl attiva dell'utente "+ utente + " trovata");
			
			if (acl.isActive() && acl.getStatoDocumento().equals(StatoDocumentoEnum.DA_FIRMARE.name()))
				abilitaSuccessivo(doc,utenteCreatore);
			
			acl.setStatoDocumento(null);
			acl.setActive(false);
		}
		List<OperazioneModel> listaOperazioni = doc.getOperazioni();
		for (String utente : utenti) {
			listaOperazioni.removeIf(op -> (op.getUtente().equalsIgnoreCase(utente) && !StatoOperazioneEnum.COMPLETATA.name().equalsIgnoreCase(op.getStato())));
			addOperazione(doc, TipoOperazioneEnum.ANNULLAMENTO_INVITO_FIRMA, utenteCreatore, utente);
			completeOperazione(doc, TipoOperazioneEnum.ANNULLAMENTO_INVITO_FIRMA, utenteCreatore);
			Optional<AssegnatarioModel> assegnatario = doc.getAssegnazione().getAssegnatari().stream()
					.filter(ass -> ass.getUtente().equalsIgnoreCase(utente) && ass.isActive()).findFirst();
			if(assegnatario.isPresent()) {
				assegnatario.get().setActive(false);
			}
		}
		
	}

	/**
	 * 
	 * @param utenti
	 * @param doc
	 * @throws LibroFirmaDataServiceException
	 */
	private void removeCollaboratori(List<String> utenti,DocumentoModel doc,String utenteCreatore) throws LibroFirmaDataServiceException {
		List<AclModel> listaAcl = doc.getAcl();
		for(String utente : utenti) {
			AclModel acl = null;
			Optional<AclModel> aclOpt = listaAcl.stream().filter(aclModel -> (aclModel.getUtente().equals(utente))).findFirst();
			if (aclOpt.isPresent())
				acl = aclOpt.get();
			else
				throw new LibroFirmaDataServiceException("Nessuna acl attiva dell'utente "+ utente + " trovata");
			
			acl.setActive(false);
			addOperazione(doc, TipoOperazioneEnum.ANNULLAMENTO_CONDIVISIONE, utenteCreatore, utente);
			completeOperazione(doc, TipoOperazioneEnum.ANNULLAMENTO_CONDIVISIONE, utenteCreatore);
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void rifiutoFirma(FirmaDocumentoDTO firmaDocumentoDto, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.rifiutoFirma: idDocumento={}, utente={}", firmaDocumentoDto.getIdDocumento(), username);
		Optional<DocumentoModel> modelOpt = docRepository.findById(firmaDocumentoDto.getIdDocumento());
		if(!modelOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		DocumentoModel model = modelOpt.get();
		
		Optional<AclModel> aclOpt = model.getAcl().stream().filter(aclModel -> (aclModel.getUtente().equals(username) && aclModel.isActive())).findFirst();
		if (!aclOpt.isPresent()) {
			throw new LibroFirmaDataServiceException("Nessuna acl attiva dell'utente "+ username + " trovata");
		}
		setRifiutato(model);
		// al rifiuto, la visibilità del documento da parte di collaboratori ed assegnatari rimane
//		--disattivaAcl(model);--
		
		List<OperazioneModel> listaOperazioni = model.getOperazioni();
		Optional<OperazioneModel> operazioneOpt = listaOperazioni.stream().filter(op -> (op.getUtente().equals(username) && op.getStato().equals(StatoOperazioneEnum.APERTA.name()))).findFirst();
		
		List<FiltroAvanzatoDTO> motiviRifiuto = filtriService.getFiltriAvanzati(FiltriEnum.MOTIVO_RIFIUTO);
		
		String motivoRifiuto = firmaDocumentoDto.getMotivo();
		
		if(motiviRifiuto != null && !motiviRifiuto.isEmpty() && motiviRifiuto.get(0).getFiltri() != null) {
			for(Filtro motivo : motiviRifiuto.get(0).getFiltri()) {
				if(motivo.getCodice().equalsIgnoreCase(firmaDocumentoDto.getMotivo())) {
					motivoRifiuto = motivo.getCodice() + "__" + motivo.getLabel() + "__" + motivo.getDescrizione();
					break;
				}
			}
		}
		if (operazioneOpt.isPresent()) {
			operazioneOpt.get().setStato(StatoOperazioneEnum.RIFIUTATA.name());
			operazioneOpt.get().setDataCompletamento(new Date());
			operazioneOpt.get().setMotivo(motivoRifiuto);
		}
		annullaOperazioniFirma(model);
		lockRepository.deleteById(model.getId());
		MittenteDTO utenteOperazione = new MittenteDTO();
		utenteOperazione.setCf(username);
		generaNotifica(model.getId(), utenteOperazione, model.getUtenteCreatore(), AzioneEnum.RIFIUTO);
		docRepository.save(model);
	}
	
	/**
	 * 
	 * @param model
	 
	private void disattivaAcl(DocumentoModel model) {
		model.getAcl().stream().filter(acl -> !TipoPermessoEnum.CREATORE.name().equals(acl.getTipo())).forEach(acl -> acl.setActive(false));
	}
	*/

	/**
	 * 
	 * @param model
	 */
	private void setRifiutato(DocumentoModel model) {
		model.getAcl().stream().filter(tmp -> !StatoDocumentoEnum.CONDIVISO.name().equals(tmp.getStatoDocumento())).forEach(tmp -> tmp.setStatoDocumento(null));
		model.setStatoDocumento(StatoDocumentoEnum.RIFIUTATO.name());
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void lockDocumento(LockDTO lockDto, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.lockDocumento: utente={}", username);
		
		lockDto.setDocumentiNonLockabili(new ArrayList<>());
		List<DocumentoModel> listaDocumenti = new ArrayList<>();
		for(String idDocumento : lockDto.getIdDocumenti()) {
			log.info("LibroFirmaDataDocumentiService.lockDocumento: utente={}, documento={}", username, idDocumento);
			DocumentoModel model = docRepository.findById(idDocumento).orElse(null);
			if(model == null) {
				throw new LibroFirmaDataServiceException("Documento non trovato: " + idDocumento);
			}
			listaDocumenti.add(model);
			if(permessiUtils.verificaLock(model, username)) {
				DocumentoDTO docNonLockabile = new DocumentoDTO();
				docNonLockabile.setIdDocumento(idDocumento);
				docNonLockabile.setNomeDocumento(model.getNomeDocumento());
				lockDto.getDocumentiNonLockabili().add(docNonLockabile);
			}
		}
		
		if(lockDto.getDocumentiNonLockabili() != null && !lockDto.getDocumentiNonLockabili().isEmpty()) {
			return;
		}
		
		for(DocumentoModel model : listaDocumenti) {
			createOrUpdateLock(model.getId(), username);
		}
	}

	/**
	 * 
	 * @param id
	 * @param username
	 */
	private void createOrUpdateLock(String id, String username) {
		Lock lock = lockRepository.findById(id).orElse(null);
		
		if(lock != null) {
			lockRepository.delete(lock);
		}
		lock = new Lock();
		lock.setId(id);
		lock.setData(new Date());
		lock.setUtenteCreatore(username);
		lockRepository.insert(lock);	
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public String unlockDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.unlockDocumento: idDocumento={}, utente={}", idDocumento, username);
//		--Optional<DocumentoModel> modelOpt = docRepository.findById(idDocumento);--
//		--if (!modelOpt.isPresent()) {--
//			--throw new LibroFirmaDataServiceException("Documento non trovato");--
//		--}--

		Optional<Lock> lockOpt = lockRepository.findById(idDocumento);
		if (!lockOpt.isPresent()) {
			log.info("LibroFirmaDataDocumentiService.unlockDocumento: NON TROVATO - idDocumento={}, utente={}", idDocumento, username);
			return null;
		}
		Lock lock = lockOpt.get();
		if (lock.getUtenteCreatore().equals(username)) {
			lockRepository.deleteById(idDocumento);
			return null;
		} 
		return lock.getId();
	}
    
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public DatiDocumentoDTO firmaDocumentoLocale(FirmaDocumentoDTO updateDocumentoDTO, String username) throws LibroFirmaDataServiceException {
		
		Optional<DocumentoModel> optDocumento = docRepository.findById(updateDocumentoDTO.getIdDocumento());
		if(!optDocumento.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		DocumentoModel documentoModel = optDocumento.get();
		
		// verifico se c'è un solo file da firmare (come per gli interni)
		int numeroFile = documentoModel.getDocumenti().parallelStream()
				.mapToInt(file -> file.isDaFirmare() ? 1 : 0)
				.reduce((a, b) -> a + b).getAsInt();
		if(numeroFile == 1) {
			// il documento contiene un solo file da firmare, procedo con la firma classica
			return firmaDocumento(updateDocumentoDTO, username);
		}
		
		StagingModel stagingModel = stagingRepository.findById(updateDocumentoDTO.getIdDocumento()).orElse(null);
		if(stagingModel == null) {
			// si sta firmando il primo file del documento
			stagingModel = initStaging(updateDocumentoDTO, username, documentoModel);
			stagingRepository.insert(stagingModel);
		}
		
		// modifico le liste di documenti
		elaboraStaging(updateDocumentoDTO, username, stagingModel);
		
		if(stagingModel.getDocumentiNonFirmati().isEmpty()) {
			// per il documento sono stati firmati tutti i files, procedo con la firma, dopo aver verificato che tutti i files sono stati creati dallo stesso utente
			checkUtenteCreatoreFirmaLocale(stagingModel);
//			--stagingRepository.delete(stagingModel);--
			stagingRepository.deleteById(stagingModel.getId());
			updateDocumentoDTO.setListaFile(DocumentoMapper.fromListFileDocumentoModelToListFileDocumentoDTOforStaging(stagingModel.getDocumentiFirmati()));
			return firmaDocumento(updateDocumentoDTO, username);
			
		}
		// almeno un file non è stato firmato, aggiorno l'utente creatore con l'ultimo a modificare
		stagingModel.setUtenteCreatore(username);
		stagingRepository.save(stagingModel);
		return DocumentoMapper.fromDocumentoModelToDatiDocumentoDTO(documentoModel, username);
	}

	/**
	 * Verifica che tutti i files dello staging siano stati creati dallo stesso utente
	 * @param stagingModel
	 * @throws LibroFirmaDataServiceException 
	 */
	private void checkUtenteCreatoreFirmaLocale(StagingModel stagingModel) throws LibroFirmaDataServiceException {
		String utenteCreatore = stagingModel.getUtenteCreatore();
		for(FileDocumentoModel file : stagingModel.getDocumentiFirmati()) {
			VersioneModel ultimaVersione = file.getVersioni().get(file.getVersioni().size() - 1);
			if(!ultimaVersione.getUtenteCreatore().equalsIgnoreCase(utenteCreatore)) {
				throw new LibroFirmaDataServiceException("Inconsistenza dati stagin per il documento con id=" + stagingModel.getId(), HttpStatus.FORBIDDEN.value());
			}
		}
	}

	/**
	 * 
	 * @param updateDocumentoDTO
	 * @param username
	 * @param modelOpt
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	private StagingModel elaboraStaging(FirmaDocumentoDTO updateDocumentoDTO, String username, StagingModel stagingModel) throws LibroFirmaDataServiceException {		
		String idEcmOriginale = updateDocumentoDTO.getListaFile().get(0).getIdEcmOriginale();
		
		FileDocumentoModel fileNonFirmato = getFileStagingFile(stagingModel.getDocumentiNonFirmati(), idEcmOriginale);
		FileDocumentoModel fileFirmato = getFileStagingFile(stagingModel.getDocumentiFirmati(), idEcmOriginale);
		
		if(fileNonFirmato == null && fileFirmato == null) {
			throw new LibroFirmaDataServiceException("File non trovato nei documenti da firmare", HttpStatus.NOT_FOUND.value());
		}
		if(fileFirmato != null && fileNonFirmato != null) {
			throw new LibroFirmaDataServiceException("Inconsistenza nei file da firmare per il documento con id=" + stagingModel.getId(), HttpStatus.NOT_FOUND.value());
		}
		VersioneModel versioneModel = DocumentoMapper.fromFileDocumentoDTOToVersioneModel(updateDocumentoDTO.getListaFile().get(0));
		versioneModel.setUtenteCreatore(username);
		if(fileNonFirmato != null) {
			// il file non è stato firmato precedentemente
			stagingModel.getDocumentiNonFirmati().remove(fileNonFirmato);
			if(fileNonFirmato.getVersioni() == null) {
				fileNonFirmato.setVersioni(new ArrayList<>());
			}
			fileNonFirmato.getVersioni().add(versioneModel);
			stagingModel.getDocumentiFirmati().add(fileNonFirmato);
		}
		else {
			// gestione firma parziale per errore (stesso utente o altro utente)
			VersioneModel ultimaVersione = fileFirmato.getVersioni().get(fileFirmato.getVersioni().size() - 1);
			ultimaVersione.setContentType(versioneModel.getContentType());
			ultimaVersione.setDataCreazione(versioneModel.getDataCreazione());
			ultimaVersione.setIdEcm(ultimaVersione.getIdEcm());
			ultimaVersione.setNomeFile(versioneModel.getNomeFile());
			ultimaVersione.setTipo(versioneModel.getTipo());
			ultimaVersione.setUtenteCreatore(versioneModel.getUtenteCreatore());
			ultimaVersione.setVersion(versioneModel.getVersion());
		}
		return stagingModel;
	}

	/**
	 * 
	 * @param docuementiNonFirmati
	 * @param idEcmOriginale
	 * @return
	 */
	private FileDocumentoModel getFileStagingFile(List<FileDocumentoModel> docuementiStaging, String idEcmOriginale) {
		Optional<FileDocumentoModel> fileOpt = docuementiStaging.stream().filter(f -> {
			if(f.getIdEcm().equalsIgnoreCase(idEcmOriginale)) {
				return true;
			}
			if(f.getVersioni() == null || f.getVersioni().isEmpty()) {
				return false;
			}
			return f.getVersioni().parallelStream().anyMatch(v -> v.getIdEcm().equalsIgnoreCase(idEcmOriginale));			
		}).findFirst();
		if(fileOpt.isPresent()) {
			return fileOpt.get();
		}
		return null;
	}

	/**
	 * 
	 * @param updateDocumentoDTO
	 * @param username
	 * @param documentoModel
	 * @return
	 */
	private StagingModel initStaging(FirmaDocumentoDTO updateDocumentoDTO, String username, DocumentoModel documentoModel) {
		StagingModel stagingModel;
		stagingModel = new StagingModel();
		stagingModel.setId(updateDocumentoDTO.getIdDocumento());
		stagingModel.setUtenteCreatore(username);
		List<FileDocumentoModel> fileDaFirmare = new ArrayList<>();
		for(FileDocumentoModel file : documentoModel.getDocumenti()) {
			if(file.isDaFirmare()) {
				fileDaFirmare.add(file);
			}
		}
		stagingModel.setDocumentiNonFirmati(fileDaFirmare);
		stagingModel.setDocumentiFirmati(new ArrayList<>());
		return stagingModel;
	}

	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public void annullamentoProcesso(String idDocumento, String username) throws LibroFirmaDataServiceException{
		log.info("LibroFirmaDataDocumentiService.annullamentoProcesso: idDocumento={}, utente={}", idDocumento, username);
		DocumentoModel model = docRepository.findById(idDocumento).orElse(null);
		if(model == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		if(!"LFU".equalsIgnoreCase(model.getAppProduttore())) {
			throw new LibroFirmaDataServiceException("Non è possibile annullare documento proveniente da applicazione federata", HttpStatus.FORBIDDEN.value());
		}
		if(model.getUtenteCreatore().equals(username)) {
			addOperazione(model, TipoOperazioneEnum.ANNULLAMENTO, username, null);
			completeOperazione(model, TipoOperazioneEnum.ANNULLAMENTO, username);
			for(AclModel acl : model.getAcl()) {
				if(!acl.getUtente().equals(username)) {
					acl.setActive(false);
				}
				acl.setStatoDocumento(null);
			}
			// elimino logicamente le operazioni di firma
			annullaOperazioniFirma(model);
			// elimino logicamente gli assegnatari
			annullaAssegnazioni(model);
			model.setStatoDocumento(StatoDocumentoEnum.ANNULLATO.name());
			docRepository.save(model);
			return;
		}
		throw new LibroFirmaDataServiceException("Azione non consentita sul documento " + model.getNomeDocumento(), HttpStatus.FORBIDDEN.value());
	}

	/**
	 * Annullamento assegnatari
	 * @param model
	 */
	private void annullaAssegnazioni(DocumentoModel model) {
		if(model.getAssegnazione() != null && model.getAssegnazione().getAssegnatari() != null) {
			for(AssegnatarioModel ass : model.getAssegnazione().getAssegnatari()) {
				ass.setActive(false);
			}
		}
	}

	/**
	 * @param model
	 */
	private void annullaOperazioniFirma(DocumentoModel model) {
		for(OperazioneModel op : model.getOperazioni()) {
			if(op.getTipo().equals(TipoOperazioneEnum.FIRMA.name()) && op.getStato().equals(StatoOperazioneEnum.APERTA.name())) {
				op.setStato(StatoOperazioneEnum.ANNULLATA.name());
				op.setDataCompletamento(null);
			}
		}
	}

	/**
	 * @throws LibroFirmaDataServiceException 
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public FascicoloDTO getFascicoloDocumento(String idDocumento, String username)throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataDocumentiService.getFascicoloDocumento: idDocumento={}, utente={}", idDocumento, username);
		Optional<DocumentoModel> modelOpt = docRepository.findById(idDocumento);
		if (!modelOpt.isPresent())
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		DocumentoModel model = modelOpt.get();
		FascicoloDTO response = new FascicoloDTO();
		if (model.getFascicolo() != null)
			response = DocumentoMapper.fromFascicoloModelToFascicoloDto(model.getFascicolo());
		
		return response;
	}
	
}
