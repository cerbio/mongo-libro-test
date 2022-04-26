package it.sogei.libro_firma.data.controller.impl;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.sogei.libro_firma.data.configuration.security.JwtUser;
import it.sogei.libro_firma.data.controller.ILibroFirmaDataSearchController;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.ContatoriRicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.CountRicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.FiltroAvanzatoDTO;
import it.sogei.libro_firma.data.model.FindParamsByGroupDTO;
import it.sogei.libro_firma.data.model.FindParamsDTO;
import it.sogei.libro_firma.data.model.FindToWorkByAppDTO;
import it.sogei.libro_firma.data.model.PreviewDTO;
import it.sogei.libro_firma.data.model.RicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.RicercaTabellareDTO;
import it.sogei.libro_firma.data.model.SearchResultDTO;
import it.sogei.libro_firma.data.model.enums.FiltriEnum;
import it.sogei.libro_firma.data.service.ILibroFirmaDataFiltriService;
import it.sogei.libro_firma.data.service.ILibroFirmaDataSearchService;

@RestController
@Validated
public class LibroFirmaDataSearchController implements ILibroFirmaDataSearchController {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaDataSearchController.class);
	
	@Autowired
	private ILibroFirmaDataSearchService searchService;
	
	@Autowired
	private ILibroFirmaDataFiltriService filtriService;
	
	public LibroFirmaDataSearchController() {
		super();
	}
	
	/**
	 * Inserimento dati documento
	 * @param documentoDto
	 * @return
	 */
	@Override
	public ResponseEntity<List<DocumentoDTO>> findByParams(@Valid @RequestBody FindParamsDTO parametri, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("findByParams: START - utente={}", user.getUsername());
		try {
			List<DocumentoDTO> listaDocumenti = searchService.findByParam(parametri.getParam(), user.getUsername());
			return ResponseEntity.ok().body(listaDocumenti);
		}
		catch(Exception e) {
			log.error("findByParams: ERROR", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore recupero documenti");
		}
	}
	
	/**
	 * Documenti per la preview di una ricerca
	 * @param parametri
	 * @return
	 */
	@Override
	public ResponseEntity<List<PreviewDTO>> findByParamsForPreview(@RequestBody FindParamsDTO parametri, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("findByParamsForPreview: START - utente={}", user.getUsername());
		try {
			List<PreviewDTO> listaDocumenti = searchService.findByParamForPreview(parametri, user.getUsername());
			return ResponseEntity.ok().body(listaDocumenti);
		}
		catch(Exception e) {
			log.error("findByParamsForPreview: ERROR", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore in recupero documenti");
		}
	}
	
	/**
	 * Documenti per applicazione
	 * @param parametri
	 * @return
	 */
	@Override
	public ResponseEntity<SearchResultDTO> findToWorkByApp(@RequestBody FindToWorkByAppDTO findToWorkByAppDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("findByParamsForPreview: START - utente={}", user.getUsername());
		try {
			SearchResultDTO result = searchService.findToWorkByApp(findToWorkByAppDTO, user.getUsername());
			return ResponseEntity.ok().body(result);
		}
		catch(Exception e) {
			log.error("findByParamsForPreview: ERROR", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore in recupero documenti");
		}
	}

	/**
	 * Tutti i documenti per la ricerca
	 * @param parametri
	 * @return
	 */
	@Override
	public ResponseEntity<SearchResultDTO> viewAllForPreview(@RequestBody FindParamsByGroupDTO parametri,Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataSearchController.findByParamsForPreview: START - utente={}", user.getUsername());
		try {
			SearchResultDTO result = searchService.viewAllForPreview(parametri, user.getUsername());
			return ResponseEntity.ok().body(result);
		}
		catch(Exception e) {
			log.error("LibroFirmaDataSearchController.viewAllForPreview: ERROR", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore in recupero documenti");
		}
	}
	
	/**
	 * Documenti da lavorare
	 * @return
	 */
	@Override
	public ResponseEntity<SearchResultDTO> findToWork(@RequestBody FindToWorkByAppDTO findToWorkByAppDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("findByParamsForPreview: START - utente={}", user.getUsername());
		try {
			SearchResultDTO result = searchService.findToWork(findToWorkByAppDTO, user.getUsername());
			return ResponseEntity.ok().body(result);
		}
		catch(Exception e) {
			log.error("findByParamsForPreview: ERROR", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore in recupero documenti");
		}
	}
	
	/**
	 * @see ILibroFirmaDataSearchController
	 */
		@Override
		public ResponseEntity<List<DatiDocumentoDTO>> ricercaAvanzata(RicercaAvanzataDTO ricercaAvanzataDto,
				Authentication authentication) {
			
			JwtUser user = (JwtUser) authentication.getPrincipal();
			log.info("ILibroFirmaDataSearchController.ricercaAvanzata: START, utente={}", user.getUsername());
			try {
				List<DatiDocumentoDTO> result = searchService.ricercaAvanzata(ricercaAvanzataDto,user.getUsername());
				return ResponseEntity.ok().body(result);
			}
			catch (Exception e) {
				log.error("ricercaAvanzata: ERROR", e);
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore in ricerca documenti");
			}
		}

	/**
	 * @see ILibroFirmaDataSearchController
	 */
	@Override
	public ResponseEntity<List<FiltroAvanzatoDTO>> getFiltriAvanzati(FiltriEnum tipoFiltro) {
		log.info("ILibroFirmaDataSearchController.getFiltriAvanzati: START - tipoFiltro={}", tipoFiltro);
		try {
			List<FiltroAvanzatoDTO> filtroAvanzatoDTO = filtriService.getFiltriAvanzati(tipoFiltro);
			return ResponseEntity.ok(filtroAvanzatoDTO);
		} catch (Exception e) {
			throw gestisciException(e,"getFiltriAvanzati","Errore nel recupero dei filtri");
		}
	}

	/**
	 * @see ILibroFirmaDataSearchController
	 */
	@Override
	public ResponseEntity<List<ContatoriRicercaAvanzataDTO>> countRicercaAvanzata(CountRicercaAvanzataDTO countDTO,	Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("ILibroFirmaDataSearchController.countRicercaAvanzata: START - user={}", user.getUsername());
		try {
			List<ContatoriRicercaAvanzataDTO> result = filtriService.countRicercaAvanzata(countDTO,user.getUsername());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			throw gestisciException(e, "countRicercaAvanzata", "Errore nel recupero dei contatori della ricerca avanzata");
		}
	}
	
	/**
	 * @see ILibroFirmaDataSearchController
	 */
	@Override
	public ResponseEntity<SearchResultDTO> findDocPrioritari(RicercaTabellareDTO ricercaTabellareDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataSearchController.findDocPrioritari: START - utente={}", user.getUsername());
		try {
			SearchResultDTO result = searchService.findDocPrioritari(ricercaTabellareDTO, user.getUsername());
			return ResponseEntity.ok().body(result);
		}
		catch(Exception e) {
			throw gestisciException(e, "findDocPrioritari", "Errore in recupero documenti prioritari");
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
		log.error("LibroFirmaBffSearchController.{}: ERROR", method, e);
		if(e instanceof LibroFirmaDataServiceException) {
			LibroFirmaDataServiceException lexc = (LibroFirmaDataServiceException) e;
			return new ResponseStatusException(HttpStatus.valueOf(lexc.getCode()), lexc.getMessage());
		}
		return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}

}
