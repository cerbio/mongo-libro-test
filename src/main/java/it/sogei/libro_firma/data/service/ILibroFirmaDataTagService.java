package it.sogei.libro_firma.data.service;

import java.util.List;

import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.GestisciTag;
import it.sogei.libro_firma.data.model.TagDTO;
import it.sogei.libro_firma.data.model.TagDocumentoDTO;
import it.sogei.libro_firma.data.model.TagRequestDTO;
import it.sogei.libro_firma.data.model.TagsResponseDTO;

public interface ILibroFirmaDataTagService {

	/**
	 * 
	 * @param tagDTO
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public String saveTag(TagDTO tagDTO) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param codiceGruppo
	 * @param username
	 * @return
	 */
	public List<String> getTagsByGroup(String codiceGruppo, String nomeTag,String username);

	/**
	 * 
	 * @param tagDTO
	 * @throws LibroFirmaDataServiceException 
	 */
	public void insertTagSuDocumento(TagDocumentoDTO tagDTO,String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param tagDTO
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	public void removeTagSuDocumento(TagDocumentoDTO tagDTO,String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param idTag
	 * @param nome
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	public void gestisciTag(GestisciTag gestisciTag, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param tagRequestDto
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public TagsResponseDTO getTags(TagRequestDTO tagRequestDto, String username)throws LibroFirmaDataServiceException;

	/**
	 * Associa i tags di un utente ad un muovo gruppo
	 * @param tagDto
	 * @param username
	 */
	public void associaTag(TagDTO tagDto, String username);

}
