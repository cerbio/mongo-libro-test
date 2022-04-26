package it.sogei.libro_firma.data.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.sogei.libro_firma.data.configuration.security.JwtUser;
import it.sogei.libro_firma.data.controller.ILibroFirmaDataTagController;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.GestisciTag;
import it.sogei.libro_firma.data.model.TagDTO;
import it.sogei.libro_firma.data.model.TagDocumentoDTO;
import it.sogei.libro_firma.data.model.TagRequestDTO;
import it.sogei.libro_firma.data.model.TagsResponseDTO;
import it.sogei.libro_firma.data.service.ILibroFirmaDataTagService;

@RestController
@Validated
public class LibroFirmaDataTagController implements ILibroFirmaDataTagController {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaDataTagController.class);
	
	@Autowired
	private ILibroFirmaDataTagService tagService;

	public LibroFirmaDataTagController() {
		super();
	}
	
	/**
	 * @see ILibroFirmaDataTagController
	 */
	@Override
	public ResponseEntity<String> saveTag(TagDTO tagDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.insertDocumento: START - tagName={}, utente={}", tagDTO.getNome(), user.getUsername());
		String idTag;
		try {
			log.info("LibroFirmaDataTagController.saveTag: START - nomeTag={}, utente={}", tagDTO.getNome(), user.getUsername());
			idTag = tagService.saveTag(tagDTO);
			return ResponseEntity.ok().body(idTag);
		} catch (Exception e) {
			throw gestisciException(e, "saveTag", "Errore nel salvataggio del tag");
		}

	}
	
	/**
	 * @see ILibroFirmaDataTagController
	 */
	@Override
	public ResponseEntity<List<String>> getTagsByGroup(String codiceGruppo,String nomeTag, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.getTagsByGroup: START - codiceGruppo={}, utente={}", codiceGruppo,user.getUsername());
		List<String> tags = new ArrayList<>();
		try {
			tags = tagService.getTagsByGroup(codiceGruppo,nomeTag,user.getUsername());
			return ResponseEntity.ok(tags);
		} catch (Exception e) {
			throw gestisciException(e, "getTagsByGroup", "Errore nel recupero dei tags");
		}
	}

	/**
	 * @see ILibroFirmaDataTagController
	 */
	@Override
	public ResponseEntity<String> insertTagSuDocumento(TagDocumentoDTO tagDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.getTagsByGroup: START - idDocumento={}, utente={}", tagDTO.getIdDocumento(),user.getUsername());
		try {
			tagService.insertTagSuDocumento(tagDTO,user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		} catch (Exception e) {
			throw gestisciException(e, "insertTagSuDocumento", "Errore durante l'inserimento dei tag");
		}
	}
	
	/**
	 * @see ILibroFirmaDataTagController
	 */
	@Override
	public ResponseEntity<String> removeTagSuDocumento(TagDocumentoDTO tagDTO, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.removeTagSuDocumento: START - idDocumento={}, utente={}", tagDTO.getIdDocumento(),user.getUsername());
		try {
			tagService.removeTagSuDocumento(tagDTO,user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		} catch (Exception e) {
			throw gestisciException(e, "removeTagSuDocumento", "Errore durante l'inserimento dei tag");
		}
	}
	
	@Override
	public ResponseEntity<String> gestisciTag(GestisciTag gestisciTag, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.gestisciTag: START - idTag={}, utente={}",gestisciTag.getId(),user.getUsername());
		try {
			tagService.gestisciTag(gestisciTag,user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		} catch (Exception e) {
			throw gestisciException(e, "modificaTag", "Errore durante la modifica dei tag");
		}
	}
	
	/**
	 * @see ILibroFirmaDataTagController
	 */
	@Override
	public ResponseEntity<TagsResponseDTO> getTags(TagRequestDTO tagRequestDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.getTags: START - nomeTag={}, utente={}", tagRequestDto.getNomeTag(),user.getUsername());
		TagsResponseDTO tags = new TagsResponseDTO();
		try {
			tags = tagService.getTags(tagRequestDto,user.getUsername());
			return ResponseEntity.ok(tags);
		}
		catch (Exception e) {
			throw gestisciException(e, "getTags", "Errore nel recupero dei tags");
		}
	}
	
	/**
	 * @see ILibroFirmaDataTagController
	 */
	@Override
	public ResponseEntity<String> associaTag(TagDTO tagDto, Authentication authentication) {
		JwtUser user = (JwtUser) authentication.getPrincipal();
		log.info("LibroFirmaDataTagController.associaTag: START utente={}", user.getUsername());
		try {
			tagService.associaTag(tagDto, user.getUsername());
			return ResponseEntity.ok().body(HttpStatus.OK.getReasonPhrase());
		}
		catch (Exception e) {
			throw gestisciException(e, "associaTag", "Errore nell'associazione dei tags");
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
