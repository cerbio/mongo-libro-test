package it.sogei.libro_firma.data.service.impl.client.fed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.service.impl.client.fed.beans.OperazioneCompletataDTO;

@Service
public class LibroFirmaFedClient {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaFedClient.class);

	@Value("${ws.libro-firma.fed}")
	private String fedEndpoint;

	@Autowired
	private RestTemplate restTemplate;
	
	public LibroFirmaFedClient() {
		super();
	}

	/**
	 * 
	 * @param operazioneCompletata
	 * @throws LibroFirmaDataServiceException 
	 */
	public void notificaApplicazioneFederata(OperazioneCompletataDTO operazioneCompletata) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaFedClient.notificaApplicazioneFederata: START - idDocumento={}", operazioneCompletata.getIdDocumento());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.ALL_VALUE);

		HttpEntity<OperazioneCompletataDTO> requestEntity = new HttpEntity<>(operazioneCompletata, headers);

		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(fedEndpoint + "/fed/notificaAppFederata", HttpMethod.POST, requestEntity,
					String.class);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				return;
			}
		}
		catch (Exception e) {
			log.info("LibroFirmaFedClient.notificaApplicazioneFederata:: errore in notifica app federata");
			throw new LibroFirmaDataServiceException("LibroFirmaFedClient.notificaApplicazioneFederata:: errore in notifica app federata",
					HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		throw new LibroFirmaDataServiceException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	/**
	 * 
	 * @param idDocumento
	 * @throws LibroFirmaDataServiceException 
	 */
	public void notificaApplicazioneFederata(String idDocumento) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaFedClient.notificaApplicazioneFederata: START - idDocumento={}", idDocumento);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.ALL_VALUE);

		HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(fedEndpoint + "/fed/" + idDocumento + "/notificaAppFederata", HttpMethod.POST, requestEntity,
					String.class);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				return;
			}
		}
		catch (Exception e) {
			log.info("LibroFirmaFedClient.notificaApplicazioneFederata:: errore in notifica app federata");
			throw new LibroFirmaDataServiceException("LibroFirmaFedClient.notificaApplicazioneFederata:: errore in notifica app federata",
					HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		throw new LibroFirmaDataServiceException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		
	}

}
