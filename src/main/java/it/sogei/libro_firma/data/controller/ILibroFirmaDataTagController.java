package it.sogei.libro_firma.data.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.sogei.libro_firma.data.model.GestisciTag;
import it.sogei.libro_firma.data.model.TagDTO;
import it.sogei.libro_firma.data.model.TagDocumentoDTO;
import it.sogei.libro_firma.data.model.TagRequestDTO;
import it.sogei.libro_firma.data.model.TagsResponseDTO;

public interface ILibroFirmaDataTagController {
	
	/**
	 * Salvataggio di un nuovo tag
	 * @param tagDTO
	 * @return
	 */
	@Operation(summary = "Salvataggio di un nuovo tag", 
			description = "Endpoint per il salvataggio di un nuovo tag")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "dati del tag" ,implementation = TagDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/tag/saveTag")
	public ResponseEntity<String> saveTag(@RequestBody TagDTO tagDTO,Authentication authentication);
	
	/**
	 * 
	 * @param codiceGruppo
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Servizio per il recupero dei tag di un determinato gruppo", 
			description = "Endpoint per il recupero dei tag di un determinato gruppo")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Endpoint per il recupero dei tag di un determinato gruppo",
	        		content = @Content(schema = @Schema(description = "Lista di tag per gruppo", implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@GetMapping(path = "/tag/getTagsByGroup")
	public ResponseEntity<List<String>> getTagsByGroup(@RequestParam(required = false) String codiceGruppo, @RequestParam(required=false) String nomeTag, Authentication authentication);
	
	/**
	 * 
	 * @param tagDTO
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Servizio per l'inserimento di un tag su un documento", 
			description = "Endpoint per l'inserimento di un tag su un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Endpoint per l'inserimento di un tag su un documento",
	        		content = @Content(schema = @Schema(description = "Response status dell'operazione", implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/tag/insertTagSuDocumento")
	public ResponseEntity<String> insertTagSuDocumento(@RequestBody TagDocumentoDTO tagDTO, Authentication authentication);
	
	/**
	 * 
	 * @param tagDTO
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Servizio per l'eliminazione di un tag su un documento", 
			description = "Endpoint per l'eliminazione di un tag su un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Endpoint per l'eliminazione di un tag su un documento",
	        		content = @Content(schema = @Schema(description = "Response status dell'operazione", implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/tag/removeTagSuDocumento")
	public ResponseEntity<String> removeTagSuDocumento(@RequestBody TagDocumentoDTO tagDTO, Authentication authentication);
	
	/**
	 * 
	 * @param gestisciTag
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Servizio la modifica di un tag", description = "Endpoint per la modifica di un tag")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Endpoint per la modifica di un tag", content = @Content(schema = @Schema(description = "Response status dell'operazione", implementation = String.class))),
			@ApiResponse(responseCode = "default", description = "Messaggio errore", content = @Content(schema = @Schema(implementation = String.class))) })
	@PutMapping(path = "/tag/gestisciTag")
	public ResponseEntity<String> gestisciTag(@RequestBody GestisciTag gestisciTag,	Authentication authentication);
	
	/**
	 * Servizio per il recupero dei tag
	 * @param tagRequestDto
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Servizio per il recupero dei tag", 
			description = "Endpoint per il recupero dei tag")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Endpoint per il recupero dei tag",
	        		content = @Content(schema = @Schema(description = "Lista di tag", implementation = TagsResponseDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PostMapping(path = "/tag/getTags")
	public ResponseEntity<TagsResponseDTO> getTags(@RequestBody TagRequestDTO tagRequestDto ,Authentication authentication);

	/**
	 * 
	 * @param tagDto
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Servizio per l'associazione di un tag", 
			description = "Endpoint per l'associazione dei tags di un utente ad un nuovo gruppo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Esito", 
					content = @Content(schema = @Schema(description = "Esito", implementation = String.class))),
			@ApiResponse(responseCode = "default", description = "Messaggio errore", 
					content = @Content(schema = @Schema(implementation = String.class))) })
	@PutMapping(path = "/tag/associaTag")
	public ResponseEntity<String> associaTag(@RequestBody TagDTO tagDto, Authentication authentication);
	
}
