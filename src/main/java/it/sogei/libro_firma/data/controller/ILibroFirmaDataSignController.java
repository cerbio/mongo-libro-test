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
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.FirmaDocumentoDTO;
import it.sogei.libro_firma.data.model.FirmaLFUDto;
import it.sogei.libro_firma.data.model.VerificaDTO;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;

public interface ILibroFirmaDataSignController {
	
	/**
	 * Update di un documento
	 * @return
	 */
	@Operation(summary = "Firma di un documento", 
			description = "Endpoint per la firma di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PutMapping(path = "/sign/firmaDocumento")
	public ResponseEntity<DatiDocumentoDTO> firmaDocumento(FirmaDocumentoDTO updateDocumentoDTO, Authentication authentication);

	/**
	 * Controlla se il tipo di firma in input è applicabile al documento
	 * @param idDoc
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Controlla se il tipo di firma in input è applicabile al documento ", 
			description = "Endpoint che ontrolla se il tipo di firma in input è applicabile al documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = Boolean.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@GetMapping(path = "/doc/checkFirmaApplicabile")
	public ResponseEntity<Boolean> checkFirmaApplicabile(@RequestParam String idDoc, @RequestParam FirmaEnum tipoFirma, Authentication authentication);

	/**
	 * Controlla se l'utente sia abilitato alla verifica della firma
	 * @param idDoc
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Controlla se l'utente sia abilitato alla verifica della firma", 
			description = "Endpoint che controlla se l'utente sia abilitato alla verifica della firma")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = Boolean.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@GetMapping(path = "/doc/checkVerificaFirma")
	public ResponseEntity<Boolean> checkVerificaFirma(@RequestParam String idDoc, Authentication authentication);
	
	/**
	 * Recupera tutti gli id Ecm dei files firmati di un singolo documento
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupera tutti gli id Ecm dei files firmati di un singolo documento", 
			description = "Endpoint per il recupero di tutti gli id Ecm dei files firmati di un singolo documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Lista di id ecm", implementation = String[].class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/getAllIdEcmVerificaFirma")
	public ResponseEntity<List<String>> getAllIdEcmVerificaFirma(@RequestParam String idDocumento, Authentication authentication);

	/**
	 * Inserisce un nuovo record di verifica per un documento
	 * @param verificaDTO
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Inserisce un nuovo record di verifica per un documento", 
			description = "Endpoint per l'inserimento di un nuovo record di verifica")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Esito", implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@PostMapping(path = "/sign/insertVerifica")
	public ResponseEntity<String> insertVerifica(@RequestBody VerificaDTO verificaDTO,Authentication authentication);
	
	/**
	 * Recupera le informazioni sull'esito di una verifica di una firma
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupera le informazioni sull'esito di una verifica di una firma", 
			description = "Endpoint che recupera le informazioni sull'esito di una verifica di una firma")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Esito della verifica", implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = VerificaDTO.class)))
	})
	@GetMapping(path = "/sign/getEsitoVerificaFirma")
	public ResponseEntity<VerificaDTO> getEsitoVerificaFirma(@RequestParam String idDocumento,Authentication authentication);

	/**
	 * Recupera tutte le firme su un documento
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupera tutte le firme su un documento", 
			description = "Endpoint che recupera tutte le firme su un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Lista delle firme su un documento", implementation = FirmaLFUDto[].class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = VerificaDTO.class)))
	})
	@GetMapping(path = "/sign/getFirmeDocumento")
	public ResponseEntity<List<FirmaLFUDto>> getFirmeDocumento(@RequestParam String idDocumento, Authentication authentication);
	
	/**
	 * 
	 * @param updateDocumentoDTO
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Firma di un documento locale", 
			description = "Endpoint per la firma di un documento locale")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PutMapping(path = "/sign/firmaDocumentoLocale")
	public ResponseEntity<DatiDocumentoDTO> firmaDocumentoLocale(FirmaDocumentoDTO updateDocumentoDTO, Authentication authentication);
}
