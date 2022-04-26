package it.sogei.libro_firma.data.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.sogei.libro_firma.data.configuration.security.JwtUser;
import it.sogei.libro_firma.data.controller.ILibroFirmaDataMonitoraggioController;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.CountAppDTO;
import it.sogei.libro_firma.data.service.ILibroFirmaDataSearchService;

@RestController
public class LibroFirmaDataMonitoraggioController implements ILibroFirmaDataMonitoraggioController {

	private Logger log = LoggerFactory.getLogger(LibroFirmaDataMonitoraggioController.class);
	
	@Autowired
	private ILibroFirmaDataSearchService searchService;
	
	public LibroFirmaDataMonitoraggioController() {
		super();
	}
	
	/**
	 * Recupera le count dei documenti per applicazione
	 * @return
	 */
	@Override
	public ResponseEntity<List<CountAppDTO>> countByApp(Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataMonitoraggioController.countByApp: START - utente={}", user.getUsername());
		try {
			List<CountAppDTO> listaContatori = searchService.countByApp(user.getUsername());
			return ResponseEntity.ok().body(listaContatori);
		}
		catch(Exception e) {
			throw gestisciException(e, "countByApp", "Errore in recupero contatoribasta ");
		}
	}
	
	/**
	 * 
	 * @param authentication
	 * @return
	 */
	@Override
	public ResponseEntity<Long> countPrioritari(Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataMonitoraggioController.countPrioritari: START - utente={}", user.getUsername());
		try {
			Long numeroDocumentiPrioritari = searchService.countPrioritari(user.getUsername());
			return ResponseEntity.ok().body(numeroDocumentiPrioritari);
		}
		catch(Exception e) {
			throw gestisciException(e, "countByApp", "Errore in recupero contatoribasta ");
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
		log.error("LibroFirmaDataMonitoraggioController.{}: ERROR", method, e);
		if(e instanceof LibroFirmaDataServiceException) {
			LibroFirmaDataServiceException lexc = (LibroFirmaDataServiceException) e;
			return new ResponseStatusException(HttpStatus.valueOf(lexc.getCode()), lexc.getMessage());
		}
		return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}
	
}
