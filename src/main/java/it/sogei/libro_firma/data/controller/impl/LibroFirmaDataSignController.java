package it.sogei.libro_firma.data.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.sogei.libro_firma.data.configuration.security.JwtUser;
import it.sogei.libro_firma.data.controller.ILibroFirmaDataSignController;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.FirmaDocumentoDTO;
import it.sogei.libro_firma.data.model.FirmaLFUDto;
import it.sogei.libro_firma.data.model.VerificaDTO;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;
import it.sogei.libro_firma.data.service.ILibroFirmaDataDocumentiService;

@RestController
public class LibroFirmaDataSignController implements ILibroFirmaDataSignController {

	private Logger log = LoggerFactory.getLogger(LibroFirmaDataSignController.class);
	
	@Autowired
	private ILibroFirmaDataDocumentiService docService;
	
	public LibroFirmaDataSignController() {
		super();
	}
	
	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<DatiDocumentoDTO> firmaDocumento(@RequestBody FirmaDocumentoDTO updateDocumentoDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataSignController.firmaDocumento: START - idDocumento={}, utente={}", updateDocumentoDTO.getIdDocumento(), user.getUsername());
		try {
			DatiDocumentoDTO datiDocFirmato = docService.firmaDocumento(updateDocumentoDTO, user.getUsername());
			return ResponseEntity.ok().body(datiDocFirmato);
		}
		catch(Exception e) {
			throw gestisciException(e, "firmaDocumento", "Errore in firma del documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<Boolean> checkFirmaApplicabile(String idDoc, FirmaEnum tipoFirma, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataSignController.checkFirmaApplicabile: START - idDocumento={} ,tipoFirma={}, utente={}", idDoc, tipoFirma, user.getUsername());
		try {
			Boolean check = docService.checkFirmaApplicabile(idDoc, tipoFirma, user.getUsername());
			return ResponseEntity.ok().body(check);
		}
		catch(Exception e) {
			throw gestisciException(e, "checkFirmaApplicabile", "Errore nella verifica della firma");
		}
	}

	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<Boolean> checkVerificaFirma(String idDoc, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataSignController.checkUtenteVerificaFirma: START - idDocumento={}, utente={}", idDoc, user.getUsername());
		try {
			boolean check = docService.checkVerificaFirma(idDoc, user.getUsername());
			return ResponseEntity.ok().body(check);
		}
		catch(Exception e) {
			throw gestisciException(e, "checkUtenteVerificaFirma", "Errore nella verifica dell'utente");
		}
	}
	
	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<List<String>> getAllIdEcmVerificaFirma(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getAllIdEcmVerificaFirma: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			List<String> idsEcm = docService.getAllIdEcmVerificaFirma(idDocumento, user.getUsername());
			return ResponseEntity.ok().body(idsEcm);
		}
		catch(Exception e) {
			throw gestisciException(e, "getAllIdEcmVerificaFirma", "Errore nel recupero degli id ecm del documento");
		}
	}
	
	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<String> insertVerifica(VerificaDTO verificaDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.insertVerifica: START - idDocumento={}, utente={}", verificaDTO.getIdDocumento(), user.getUsername());
		try {
			docService.insertVerifica(verificaDTO, user.getUsername());
			return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
		}
		catch(Exception e) {
			throw gestisciException(e, "insertVerifica", "Errore nell'inserimento della verifica del documento");
		}
	}

	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<VerificaDTO> getEsitoVerificaFirma(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getEsitoVerificaFirma: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			VerificaDTO response = docService.getEsitoVerificaFirma(idDocumento, user.getUsername());
			return ResponseEntity.ok(response);
		}
		catch(Exception e) {
			throw gestisciException(e, "getEsitoVerificaFirma", "Errore nel recupero delle informazioni di un esito di una verifica");
		}
	}

	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<List<FirmaLFUDto>> getFirmeDocumento(String idDocumento, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataDocumentiController.getFirmeDocumento: START - idDocumento={}, utente={}", idDocumento, user.getUsername());
		try {
			List<FirmaLFUDto> response = docService.getFirmeDocumento(idDocumento, user.getUsername());
			return ResponseEntity.ok(response);
		}
		catch(Exception e) {
			throw gestisciException(e, "getFirmeDocumento", "Errore nel recupero delle firme di un documento");
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
		log.error("LibroFirmaDataSignController.{}: ERROR", method, e);
		if(e instanceof LibroFirmaDataServiceException) {
			LibroFirmaDataServiceException lexc = (LibroFirmaDataServiceException) e;
			return new ResponseStatusException(HttpStatus.valueOf(lexc.getCode()), lexc.getMessage());
		}
		return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}
	
	/**
	 * @see ILibroFirmaDataSignController
	 */
	@Override
	public ResponseEntity<DatiDocumentoDTO> firmaDocumentoLocale(@RequestBody FirmaDocumentoDTO updateDocumentoDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataSignController.firmaDocumentoLocale: START - idDocumento={}, utente={}", updateDocumentoDTO.getIdDocumento(), user.getUsername());
		try {
			DatiDocumentoDTO datiDocFirmato = docService.firmaDocumentoLocale(updateDocumentoDTO, user.getUsername());
			return ResponseEntity.ok().body(datiDocFirmato);
		}
		catch(Exception e) {
			throw gestisciException(e, "firmaDocumento", "Errore in firma del documento");
		}
	}
}
