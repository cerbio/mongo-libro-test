package it.sogei.libro_firma.data.service;

import java.util.List;

import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.CountAppDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.FindParamsByGroupDTO;
import it.sogei.libro_firma.data.model.FindParamsDTO;
import it.sogei.libro_firma.data.model.FindToWorkByAppDTO;
import it.sogei.libro_firma.data.model.PreviewDTO;
import it.sogei.libro_firma.data.model.RicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.RicercaTabellareDTO;
import it.sogei.libro_firma.data.model.SearchResultDTO;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;

public interface ILibroFirmaDataSearchService {
	
	/**
	 * 
	 * @param utente
	 * @return
	 */
	public List<DocumentoDTO> findByUtente(String utente);

	/**
	 * 
	 * @param utente
	 * @return
	 */
	public List<CountAppDTO> countByApp(String utente);

	/**
	 * 
	 * @param param
	 * @param utente
	 * @return
	 */
	public List<DocumentoDTO> findByParam(String param, String utente);

	/**
	 * 
	 * @param parametri
	 * @param utente
	 * @return
	 */
	public List<PreviewDTO> findByParamForPreview(FindParamsDTO parametri, String utente);
	
	/**
	 * 
	 * @param parametri
	 * @param utente
	 * @return
	 */
	public SearchResultDTO findToWorkByApp(FindToWorkByAppDTO findToWorkByAppDTO, String utente);

	/**
	 * 
	 * @param parametri
	 * @param utente
	 * @return
	 */
	public SearchResultDTO viewAllForPreview(FindParamsByGroupDTO parametri, String username);
	
	/**
	 * 
	 * @param parametri
	 * @param utente
	 * @return
	 */
	public SearchResultDTO findToWork(FindToWorkByAppDTO findToWorkByAppDTO, String utente);

	/**
	 * recupera i dati del documento se l'utente ha i permessi per eseguire l'azione indicata
	 * @param idDocumento
	 * @param azione
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public DatiDocumentoDTO getForAction(String idDocumento, AzioneEnum azione, String username) throws LibroFirmaDataServiceException;

	/**
	 * Recupera il numero di documenti prioritari
	 * @param username
	 * @return
	 */
	public Long countPrioritari(String username);
	
	/**
	 * 
	 * @param ricercaAvanzataDto
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public List<DatiDocumentoDTO> ricercaAvanzata(RicercaAvanzataDTO ricercaAvanzataDto,String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param ricercaTabellareDTO
	 * @param utente
	 * @return
	 */
	public SearchResultDTO findDocPrioritari(RicercaTabellareDTO ricercaTabellareDTO, String utente);

}
