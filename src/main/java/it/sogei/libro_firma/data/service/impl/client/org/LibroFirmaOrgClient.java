package it.sogei.libro_firma.data.service.impl.client.org;

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
import it.sogei.libro_firma.data.model.NotificaDTO;

@Service
public class LibroFirmaOrgClient {

	private Logger log = LoggerFactory.getLogger(LibroFirmaOrgClient.class);

	@Value("${ws.libro-firma.org}")
	private String orgEndpoint;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @return
	 * @throws LibroFirmaBffServiceException
	 */
	public String inserisciNotifica(NotificaDTO notifica) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaOrgClient.inserisciNotifica: START");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.ALL_VALUE);

		HttpEntity<NotificaDTO> requestEntity = new HttpEntity<>(notifica, headers);

		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(orgEndpoint + "/notif/creaNotifica", HttpMethod.POST, requestEntity,
					String.class);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				return response.getBody();
			}
		} catch (Exception e) {
			log.info("LibroFirmaOrgClient.inserisciNotifica: errore in inserimento Notifica");
			throw new LibroFirmaDataServiceException(
					"LibroFirmaOrgClient.inserisciNotifica: errore in inserimento Notifica",
					HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		throw new LibroFirmaDataServiceException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

}