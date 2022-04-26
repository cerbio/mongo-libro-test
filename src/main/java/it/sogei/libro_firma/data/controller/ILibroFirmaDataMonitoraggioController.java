package it.sogei.libro_firma.data.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.sogei.libro_firma.data.model.CountAppDTO;

public interface ILibroFirmaDataMonitoraggioController {

	/**
	 * Recupera le count dei documenti per applicazione
	 * @return
	 */
	@Operation(summary = "Recupera le count dei documenti per applicazione", 
			description = "Endpoint per il recupero delle count dei documenti per applicazione")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CountAppDTO.class)))),
	        @ApiResponse(responseCode = "default", description = "Messaggio di errore",
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping("/monit/countByApp")
	public ResponseEntity<List<CountAppDTO>> countByApp(Authentication authentication);
	
	/**
	 * Recupera le count dei documenti per applicazione
	 * @return
	 */
	@Operation(summary = "Recupera le count dei documenti prioritari", 
			description = "Endpoint per il recupero delle count dei documenti prioritari")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = Number.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio di errore", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@GetMapping("/monit/countPrioritari")
	public ResponseEntity<Long> countPrioritari(Authentication authentication);

}