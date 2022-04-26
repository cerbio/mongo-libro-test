package it.sogei.libro_firma.data.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

public interface ILibroFirmaDataSearchController {

	/**
	 * Inserimento dati documento
	 * @param documentoDto
	 * @return
	 */
	@Operation(summary = "Ricerca documenti", 
			description = "Endpoint per la ricerca parametrica dei documenti")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	        		content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDTO.class)))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/search")
	public ResponseEntity<List<DocumentoDTO>> findByParams(FindParamsDTO parametri, Authentication authentication);

	/**
	 * documenti per la preview di una ricerca
	 * @param parametri
	 * @return
	 */
	@Operation(summary = "Documenti per la preview di una ricerca", 
			description = "Endpoint per i documenti per la preview di una ricerca")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	        		content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDTO.class)))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/search/preview")
	public ResponseEntity<List<PreviewDTO>> findByParamsForPreview(FindParamsDTO parametri, Authentication authentication);
	
	/**
	 * documenti per applicazione
	 * @param parametri
	 * @return
	 */
	@Operation(summary = "Documenti per applicazione", 
			description = "Endpoint per i documenti per applicazione")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	        		content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDTO.class)))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/search/findToWorkByApp")
	public ResponseEntity<SearchResultDTO> findToWorkByApp(FindToWorkByAppDTO findToWorkByAppDTO, Authentication authentication);

	/**
	 * Lista di tutti i documenti per una ricerca
	 * @param parametri
	 * @return
	 */
	@Operation(summary = "Lista di tutti i documenti per una ricerca", 
			description = "Endpoint per la visualizzazione di tutti documenti per una ricerca")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	        		content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDTO.class)))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/search/view-all")
	public ResponseEntity<SearchResultDTO> viewAllForPreview(FindParamsByGroupDTO parametri, Authentication authentication);
	
	/**
	 * documenti per applicazione
	 * @param parametri
	 * @return
	 */
	@Operation(summary = "Lista documenti da lavorare", 
			description = "Endpoint per il recupero della lista documenti da lavorare")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	        		content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDTO.class)))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/search/findToWork")
	public ResponseEntity<SearchResultDTO> findToWork(FindToWorkByAppDTO findToWorkByAppDTO, Authentication authentication);
	
	/*
	 * 
	 */
	@Operation(summary = "Ricerca avanzata", 
			description = "Ricerca Avanzata")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Ricerca Avanzata",
	        		content = @Content(schema = @Schema(implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PostMapping(path = "/search/ricercaAvanzata")
	public ResponseEntity<List<DatiDocumentoDTO>> ricercaAvanzata(@RequestBody RicercaAvanzataDTO ricercaAvanzataDto, Authentication authentication);

	/**
	 * Recupero label dei filtri avanzati
	 * @param tipoFiltro
	 * @return
	 */
	@Operation(summary = "Recupero label dei filtri avanzati", 
			description = "Endpoint per il recupero delle label dei filtri avanzati")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Lista delle label dei filtri avanzati",
	        		content = @Content(schema = @Schema(implementation = FiltroAvanzatoDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@GetMapping(path = "/search/getFiltri")
	public ResponseEntity<List<FiltroAvanzatoDTO>> getFiltriAvanzati(@RequestParam FiltriEnum tipoFiltro);
	
	/**
	 * 
	 * @param countDTO
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupero contatori dei filtri avanzati", 
			description = "Endpoint per il recupero dei contatori dei filtri avanzati")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Lista dei contatori dei filtri avanzati",
	        		content = @Content(schema = @Schema(implementation = ContatoriRicercaAvanzataDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PostMapping(path = "/search/countRicercaAvanzata")
	public ResponseEntity<List<ContatoriRicercaAvanzataDTO>> countRicercaAvanzata(@RequestBody CountRicercaAvanzataDTO countDTO,Authentication authentication);

	/**
	 * Lista documenti prioritari
	 * @param parametri
	 * @return
	 */
	@Operation(summary = "Lista documenti prioritari", 
			description = "Endpoint per il recupero della lista documenti prioritari")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	        		content = @Content(schema = @Schema(implementation = SearchResultDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/search/findDocPrioritari")
	public ResponseEntity<SearchResultDTO> findDocPrioritari(@RequestBody RicercaTabellareDTO ricercaTabellareDTO, Authentication authentication);
}