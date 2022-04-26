package it.sogei.libro_firma.data.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

public interface ILibroFirmaDataDocumentiController {

	/**
	 * Inserimento dati documento
	 * @param documentoDto
	 * @return
	 */
	@Operation(summary = "Inserimento dati documento", 
			description = "Endpoint per l'inserimento dei dati di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Id persistente" ,implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/doc/insertDocumento")
	public ResponseEntity<String> insertDocumento(@RequestBody DocumentoDTO documentoDto, Authentication authentication);

	/**
	 * Inserimento dati documento proveniente da applicazioni federate
	 * @param documentoDto
	 * @return
	 */
	@Operation(summary = "Inserimento dati documento proveniente da applicazioni federate", 
			description = "Endpoint per l'inserimento dei dati di un documento proveniente da applicazioni federate")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Id persistente" ,implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PostMapping(path = "/doc/insertDocumentoFed")
	public ResponseEntity<String> insertDocumentoFed(@RequestBody DocumentoFedDTO documentoDto, Authentication authentication);
	
	/**
	 * Recupera i documenti visibili da un utente
	 * @return
	 */
	@Operation(summary = "Recupera i documenti visibili da un utente", 
			description = "Endpoint per il recupero dei documenti visibili ad un utente")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDTO.class)))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@GetMapping(path = "/doc/findByUtente")
	public ResponseEntity<List<DocumentoDTO>> findByUtente(Authentication authentication);
	
	/**
	 * Recupera il documento se l'utente può svolgere l'azione indicata
	 * @return
	 */
	@Operation(summary = "Recupera il documento se l'utente può svolgere l'azione indicata", 
			description = "Endpoint per il recupero del documento se l'utente può svolgere l'azione indicata")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Dati documento", implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/{idDocumento}/{azione}/getForAction")
	public ResponseEntity<DatiDocumentoDTO> getForAction(@PathVariable String idDocumento, AzioneEnum azione, Authentication authentication);
	
	/**
	 * Eliminazione di un documento
	 * @return
	 */
	@Operation(summary = "Eliminazione di un documento", 
			description = "Endpoint per l'eliminazione di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@DeleteMapping(path = "/doc/{idDocumento}/deleteDocumento")
	public ResponseEntity<String> deleteDocumento(String idDocumento, Authentication authentication);

	/**
	 * Update di un documento
	 * @return
	 */
	@Operation(summary = "Update di un documento", 
			description = "Endpoint per l'update di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore" ,implementation = String.class)))
	})
	@PutMapping(path = "/doc/updateDocumento")
	public ResponseEntity<String> updateDocumento(UpdateDocumentoDTO updateDocumentoDTO, Authentication authentication);
	
	/**
	 * Recupera il documento se l'utente può svolgere l'azione indicata
	 * @return
	 */
	@Operation(summary = "Recupera la lista delle operazioni svolte per un documento", 
			description = "Endpoint per il recupero della lista delle operazioni svolte per un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Storico operazioni", implementation = StoricoOperazioniDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/{idDocumento}/getStoricoOperazioniDocumento")
	public ResponseEntity<List<StoricoOperazioniDTO>> getStoricoOperazioniDocumento(String idDocumento, Authentication authentication);
	
	/**
	 * Verifica appProduttore e stato documento
	 * @return
	 */
	@Operation(summary = "Verifica il produttore e lo stato documento", 
			description = "Endpoint per la verifica del produttore e lo stato documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Esito", implementation = Boolean.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/checkFirmaFed")
	public ResponseEntity<Boolean> checkFirmaFed(@RequestParam String idDocumento, @RequestParam String appCode, Authentication authentication);
	
	/**
	 * Recupera tutti gli id Ecm di un singolo documento
	 * @return
	 */
	@Operation(summary = "Recupera tutti gli id Ecm di un singolo documento", 
			description = "Endpoint per il recupero di tutti gli id Ecm di un singolo documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Lista di id ecm", implementation = String.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/getAllIdEcm")
	public ResponseEntity<List<String>> getAllIdEcm(@RequestParam String idDocumento, Authentication authentication);
	
	/**
	 * Recupera un documento dato l'id del documento
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupera un documento dato l'id del documento", 
			description = "Endpoint per il recupero di un documento dato l'id del documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Documento", implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/getDocumentoFed")
	public ResponseEntity<DocumentoFedDTO> getDocumentoFed(@RequestParam String idDocumento);
	
	/**
	 * Recupera un documento dato l'id del documento e l'id della relativa richiesta
	 * @param idDocumento
	 * @param idRichiesta
	 * @return
	 */
	@Operation(summary = "Recupera un documento dato l'id del documento e l'id della relativa richiesta", 
			description = "Endpoint per il recupero di un documento dato l'id e l'id della relativa richiesta")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", 
	                content = @Content(schema = @Schema(description = "Documento", implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", 
            		content = @Content(schema = @Schema(description = "Messaggio errore", implementation = String.class)))
	})
	@GetMapping(path = "/doc/getDocumentoFedByRequest")
	public ResponseEntity<DocumentoFedDTO> getDocumentoFed(@RequestParam String idDocumento,@RequestParam String idRichiesta);

	/**
	 * 
	 * @param condividiDocumentiDto
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Condividi Documenti",
			description = "Endpoint per la condivisione dei documenti")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	        		content = @Content(schema = @Schema(description = "Esito", implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PostMapping(path = "/doc/condividiDocumenti")
	public ResponseEntity<String> condividiDocumenti(@RequestBody CondividiDocumentiDTO condividiDocumentiDto, Authentication authentication);
	
	/**
	 * Recupero di un documento
	 * @param id
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupero di un documento",
			description = "Endpoint per il recupero di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	        		content = @Content(schema = @Schema(description = "Esito", implementation = DatiDocumentoDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@GetMapping(path = "/doc/getDocumento")
	public ResponseEntity<DatiDocumentoDTO> getDocumento(@RequestParam String idDocumento,Authentication authentication);

	/**
	 * Annullamento di una operazione / revoca collaboratore
	 * @param operazioneDto
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Annullamento di una operazione / revoca collaboratore", 
			description = "Endpoint che per l'annullamento di un'operazione o per rimuovere un collaboratore dalle ACL")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	                content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
	        		content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/doc/annullamentoCondivisione")
	public ResponseEntity<String> annullaCondivisione(@RequestBody AnnullaOperazioneDTO operazioneDto, Authentication authentication);
	
	/**
	 * Rifiuto firma
	 * @param authentication
	 * @param FirmaDocumentiDTO
	 * @return
	 */
	@Operation(summary = "Rifiuto firma", 
			description = "Endpoint per il rifiuto della firma")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	                content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
	        		content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/doc/rifiutoFirma")
	public ResponseEntity<String> rifiutoFirma(@RequestBody FirmaDocumentoDTO firmaDocumentoDto, Authentication authentication);
	
	/**
	 * 
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "lock di un documento", 
			description = "Endpoint per lock di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	                content = @Content(schema = @Schema(implementation = LockDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
	        		content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/doc/lock")
	public ResponseEntity<LockDTO> lockDocumento(@RequestBody LockDTO lockDto, Authentication authentication);
	
	/**
	 * 
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "unlock di un documento", 
			description = "Endpoint per unlock di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	                content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
	        		content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/doc/unlock")
	public ResponseEntity<String> unlockDocumento(@RequestParam String idDocumento, Authentication authentication);
	
	/**
	 * Annullamento processo di un documento
	 * @param idDocumento
	 * @return
	 */
	@Operation(summary = "Annullamento processo di un documento", 
			description = "Endpoint per l'annullamento processo di un documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	        		content = @Content(schema = @Schema(description = "Esito", implementation = String.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@PutMapping(path = "/doc/{idDocumento}/annullamentoProcesso")
	public ResponseEntity<String> annullamentoProcesso(@PathVariable("idDocumento") String idDocumento, Authentication authentication);
	
	/**
	 * Recupero di un fascicolo di un determinato documento
	 * @param idDocumento
	 * @param authentication
	 * @return
	 */
	@Operation(summary = "Recupero di un fascicolo di un determinato documento", 
			description = "Endpoint per il recupero di un fascicolo di un determinato documento")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Esito",
	        		content = @Content(schema = @Schema(description = "Esito", implementation = FascicoloDTO.class))),
	        @ApiResponse(responseCode = "default", description = "Messaggio errore", 
    			content = @Content(schema = @Schema(implementation = String.class)))
	})
	@GetMapping(path = "/doc/getFascicolo")
	public ResponseEntity<FascicoloDTO> getFascicoloDocumento(@RequestParam String idDocumento,Authentication authentication);
}