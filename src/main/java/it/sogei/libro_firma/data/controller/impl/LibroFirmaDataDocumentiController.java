package it.sogei.libro_firma.data.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.sogei.libro_firma.data.configuration.security.JwtUser;
import it.sogei.libro_firma.data.controller.ILibroFirmaDataDocumentiController;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.AnnullaOperazioneDTO;
import it.sogei.libro_firma.data.model.CondividiDocumentiDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoFedDTO;
import it.sogei.libro_firma.data.model.FascicoloDTO;
import it.sogei.libro_firma.data.model.FirmaDocumentoDTO;
import it.sogei.libro_firma.data.model.LockDTO;
import it.sogei.libro_firma.data.model.StoricoOperazioniDTO;
import it.sogei.libro_firma.data.model.UpdateDocumentoDTO;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;
import it.sogei.libro_firma.data.service.ILibroFirmaDataDocumentiService;
import it.sogei.libro_firma.data.service.ILibroFirmaDataSearchService;

@RestController
@Validated
public class LibroFirmaDataDocumentiController implements ILibroFirmaDataDocumentiController {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaDataDocumentiController.class);
	
	@Autowired
	private ILibroFirmaDataDocumentiService docService;
	
	@Autowired
	private ILibroFirmaDataSearchService searchService;
	
	public LibroFirmaDataDocumentiController() {
		super();
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> insertDocumento(DocumentoDTO documentoDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.insertDocumento: START - fileName={}, utente={}", documentoDto.getNomeDocumento(), user.getUsername());
		String idFile;
		try {
			idFile = docService.insertDocumento(documentoDto, user.getUsername());
			log.info("LibroFirmaDataDocumentiController.insertDocumento: START - fileName={}, utente={}, idDocumento={}", documentoDto.getNomeDocumento(), user.getUsername(), idFile);
			return ResponseEntity.ok().body(idFile);
		}
		catch(Exception e) {
			throw gestisciException(e, "insertDocumento", "Errore in insert documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> insertDocumentoFed(DocumentoFedDTO documentoDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.insertDocumentoFed: START - fileName={}, utente={}", documentoDto.getNomeDocumento(), user.getUsername());
		String idFile;
		try {
			idFile = docService.insertDocumentoFed(documentoDto, user.getUsername());
			log.info("LibroFirmaDataDocumentiController.insertDocumentoFed: START - fileName={}, utente={}, idDocumento={}", documentoDto.getNomeDocumento(), user.getUsername(), idFile);
			return ResponseEntity.ok().body(idFile);
		}
		catch(Exception e) {
			throw gestisciException(e, "insertDocumentoFed", "Errore in insert documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<List<DocumentoDTO>> findByUtente(Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.findByUtente: START - utente={}", user.getUsername());
		try {
			List<DocumentoDTO> listaDocumenti = searchService.findByUtente(user.getUsername());
			return ResponseEntity.ok().body(listaDocumenti);
		}
		catch(Exception e) {
			throw gestisciException(e, "findByUtente", "Errore in ricerca documento");
		}
	}

	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<DatiDocumentoDTO> getForAction(String idDocumento, @PathVariable AzioneEnum azione, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getForAction: START - idDocumento={}, azione={}, utente={}", idDocumento, azione, user.getUsername());
		try {
			DatiDocumentoDTO datiDocumento = searchService.getForAction(idDocumento, azione, user.getUsername());
			return ResponseEntity.ok().body(datiDocumento);
		}
		catch(Exception e) {
			throw gestisciException(e, "getForAction", "Errore in ricerca documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> deleteDocumento(@PathVariable String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.deleteDocumento: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			docService.deleteDocumento(idDocumento, user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "deleteDocumento", "Errore in eliminazione documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> updateDocumento(@RequestBody UpdateDocumentoDTO updateDocumentoDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.deleteDocumento: START - idDocumento={}, utente={}", updateDocumentoDTO.getIdDocumento(), user.getUsername());
		try {
			docService.updateDocumento(updateDocumentoDTO,user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "updateDocumento", "Errore nell'update del documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<List<StoricoOperazioniDTO>> getStoricoOperazioniDocumento(@PathVariable String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getStoricoOperazioniDocumento: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			List<StoricoOperazioniDTO> storicoOperazioni = docService.getStoricoOperazioniDocumento(idDocumento);
			return ResponseEntity.ok().body(storicoOperazioni);
		}
		catch(Exception e) {
			throw gestisciException(e, "getStoricoOperazioniDocumento", "Errore in ricerca operazioni");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<Boolean> checkFirmaFed(String idDocumento, String appCode, Authentication authentication){
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.checkFirmaFed: START - idDocumento={}, appCode={}, utente={}", idDocumento, appCode, user.getUsername());
		try {
			Boolean check = docService.checkFirmaFed(idDocumento, appCode, user.getUsername());
			return ResponseEntity.ok().body(check);
		}
		catch(Exception e) {
			throw gestisciException(e, "checkFirmaFed", "Errore in controllo documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<List<String>> getAllIdEcm(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getAllIdEcm: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			List<String> idsEcm = docService.getAllIdEcm(idDocumento, user.getUsername());
			return ResponseEntity.ok().body(idsEcm);
		}
		catch(Exception e) {
			throw gestisciException(e, "getAllIdEcm", "Errore nel recupero degli id ecm del documento");
		}
	}

	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<DocumentoFedDTO> getDocumentoFed(String idDocumento) {
		log.info("LibroFirmaDataDocumentiController.getDocumentoFed: START - idDocumento={}", idDocumento);
		try {
			DocumentoFedDTO response = docService.getDocumentoFed(idDocumento);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			throw gestisciException(e, "findByUtente", "Errore nella ricerca del documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<DocumentoFedDTO> getDocumentoFed(String idDocumento, String idRichiesta) {
		log.info("LibroFirmaDataDocumentiController.getDocumentoFed: START - idDocumento={}, idRichiesta={}", idDocumento, idRichiesta);
		try {
			DocumentoFedDTO response = docService.getDocumentoFed(idDocumento,idRichiesta);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			throw gestisciException(e, "findByUtente", "Errore nella ricerca del documento");
		}
	}

	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> condividiDocumenti(CondividiDocumentiDTO condividiDocumentiDto,Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.condividiDocumenti: START - utente={}", user.getUsername());
		try {
			docService.condividiDocumenti(condividiDocumentiDto,user.getUsername());
			log.info("LibroFirmaDataDocumentiController.condividiDocumenti: START -  utente={}", user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "condividiDocumenti", "Errore in condividi documenti");
		}
	}

	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<DatiDocumentoDTO> getDocumento(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getDocumento: START - idDocumento={}",idDocumento);
		try {
			DatiDocumentoDTO response = docService.getDocumento(idDocumento,user.getUsername());
			return ResponseEntity.ok(response);
		}
		catch(Exception e) {
			throw gestisciException(e, "getDocumento", "Errore nel recupero del documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> annullaCondivisione(AnnullaOperazioneDTO operazioneDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.annullaOperazione: START - idDocumento={}, utente={}",operazioneDto.getIdDocumento(),user.getUsername());
		try {
			docService.annullaCondivisione(operazioneDto,user.getUsername());
			return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "annullaOperazione", "Errore nell'annullamento dell'operazione");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> rifiutoFirma(FirmaDocumentoDTO firmaDocumentoDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.rifiutoFirma: START - idDocumento={}, utente={}", firmaDocumentoDto.getIdDocumento(), user.getUsername());
		try {
			docService.rifiutoFirma(firmaDocumentoDto, user.getUsername());
			return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "rifiutoFirma", "Errore nel rifiuto della firma");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<LockDTO> lockDocumento(LockDTO lockDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.lockDocumento: START - idDocumento={}, utente={}", user.getUsername());
		try {
			docService.lockDocumento(lockDto, user.getUsername());
			return ResponseEntity.ok().body(lockDto);
		}
		catch(Exception e) {
			throw gestisciException(e, "lockDocumento", "Errore nel lock del Documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> unlockDocumento(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.unlockDocumento: START - idDocumento={}, utente={}",idDocumento,user.getUsername());
		try {
			String response = docService.unlockDocumento(idDocumento,user.getUsername());
			return ResponseEntity.ok(response == null ? HttpStatus.OK.getReasonPhrase() : response);
		} catch(Exception e) {
			throw gestisciException(e, "unlockDocumento", "Errore nel unlock del Documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<String> annullamentoProcesso(@PathVariable("idDocumento") String idDocumento, Authentication authentication){
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.annullamentoProcesso: START - id={}, utente={}", idDocumento, user.getUsername());
		try {
			docService.annullamentoProcesso(idDocumento, user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "annullamentoProcesso", "Errore in delete documento");
		}	
	}

	/**
	 * @see ILibroFirmaDataDocumentiController
	 */
	@Override
	public ResponseEntity<FascicoloDTO> getFascicoloDocumento(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getFascicoloDocumento: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			FascicoloDTO response = docService.getFascicoloDocumento(idDocumento, user.getUsername());
			return ResponseEntity.ok().body(response);
		}
		catch(Exception e) {
			throw gestisciException(e, "getFascicoloDocumento", "Errore in recupero fascicolo documento");
		}	
	}
	
	/**
	 * 
	 * @param e
	 * @param method
	 * @param message
	 * @return
	 */
	private ResponseStatusException gestisciException(Exception e, String method, String message) {
		log.error("LibroFirmaDataDocumentiController.{}: ERROR", method, e);
		if(e instanceof LibroFirmaDataServiceException) {
			LibroFirmaDataServiceException lexc = (LibroFirmaDataServiceException) e;
			return new ResponseStatusException(HttpStatus.valueOf(lexc.getCode()), lexc.getMessage());
		}
		return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}

}
