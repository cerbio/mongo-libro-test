package it.sogei.libro_firma.data.service;

import java.util.List;

import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.ContatoriRicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.CountRicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.FiltroAvanzatoDTO;
import it.sogei.libro_firma.data.model.enums.FiltriEnum;

public interface ILibroFirmaDataFiltriService {

	/**
	 * 
	 * @param tipoFiltro
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	List<FiltroAvanzatoDTO> getFiltriAvanzati(FiltriEnum tipoFiltro) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param countDTO
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	List<ContatoriRicercaAvanzataDTO> countRicercaAvanzata(CountRicercaAvanzataDTO countDTO, String username) throws LibroFirmaDataServiceException;

}
