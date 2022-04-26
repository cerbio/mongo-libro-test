package it.sogei.libro_firma.data.service;

import java.util.List;

import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.AnnullaOperazioneDTO;
import it.sogei.libro_firma.data.model.CondividiDocumentiDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoFedDTO;
import it.sogei.libro_firma.data.model.FascicoloDTO;
import it.sogei.libro_firma.data.model.FirmaDocumentoDTO;
import it.sogei.libro_firma.data.model.FirmaLFUDto;
import it.sogei.libro_firma.data.model.LockDTO;
import it.sogei.libro_firma.data.model.StoricoOperazioniDTO;
import it.sogei.libro_firma.data.model.UpdateDocumentoDTO;
import it.sogei.libro_firma.data.model.VerificaDTO;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;

public interface ILibroFirmaDataDocumentiService {

	/**
	 * 
	 * @param documentoDto
	 * @param utenteCreatore 
	 * @return
	 */
	public String insertDocumento(DocumentoDTO documentoDto, String utenteCreatore);

	/**
	 * 
	 * @param documentoDto
	 * @param username
	 * @return
	 */
	public String insertDocumentoFed(DocumentoFedDTO documentoDto, String username) throws LibroFirmaDataServiceException;

	
	/**
	 * Eliminazione di un documento
	 * @param idDocumento
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public void deleteDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * Update di un documento
	 * @param paramsUpdate
	 * @param username
	 * @throws LibroFirmaDataServiceException 
	 */
	public void updateDocumento(UpdateDocumentoDTO updateDocumentoDTO, String username) throws LibroFirmaDataServiceException;

	/**
	 * Salva i dati di firma di un documento
	 * @param updateDocumentoDTO
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public DatiDocumentoDTO firmaDocumento(FirmaDocumentoDTO updateDocumentoDTO, String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param getStoricoOperazioniDocumento
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public List<StoricoOperazioniDTO> getStoricoOperazioniDocumento(String idDocumento) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param checkFirmaFed
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public Boolean checkFirmaFed(String idDocumento, String appCode, String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param findById
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public List<String> getAllIdEcm(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDoc
	 * @param tipoFirma
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public Boolean checkFirmaApplicabile(String idDoc, FirmaEnum tipoFirma, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDocumento
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public DocumentoFedDTO getDocumentoFed(String idDocumento) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param idDocumento
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public DocumentoFedDTO getDocumentoFed(String idDocumento,String idRichiesta) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param condividiDocumentiDto
	 * @return 
	 * @throws LibroFirmaDataServiceException
	 */
	public void  condividiDocumenti(CondividiDocumentiDTO condividiDocumentiDto,String username) throws LibroFirmaDataServiceException;


	/**
	 * 
	 * @param idDoc
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public Boolean checkVerificaFirma(String idDoc, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public List<String> getAllIdEcmVerificaFirma(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param verificaDTO
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	public void insertVerifica(VerificaDTO verificaDTO, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public VerificaDTO getEsitoVerificaFirma(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public DatiDocumentoDTO getDocumento(String id,String utente) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException 
	 */
	public List<FirmaLFUDto> getFirmeDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param operazioneDto
	 * @param username
	 * @throws LibroFirmaDataServiceException 
	 */
	public void annullaCondivisione(AnnullaOperazioneDTO operazioneDto, String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param firmaDocumentoDto
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	public void rifiutoFirma(FirmaDocumentoDTO firmaDocumentoDto, String username) throws LibroFirmaDataServiceException;
	
	/**
	 * 
	 * @param lockDto
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	public void lockDocumento(LockDTO lockDto, String username) throws LibroFirmaDataServiceException;
   
	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @throws LibroFirmaDataServiceException 
	 */
	public String unlockDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param updateDocumentoDTO
	 * @param usernamethrows
	 * @return
	 */
	public DatiDocumentoDTO firmaDocumentoLocale(FirmaDocumentoDTO updateDocumentoDTO, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @throws LibroFirmaDataServiceException
	 */
	public void annullamentoProcesso(String idDocumento, String username) throws LibroFirmaDataServiceException;

	/**
	 * 
	 * @param idDocumento
	 * @param username
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	public FascicoloDTO getFascicoloDocumento(String idDocumento, String username) throws LibroFirmaDataServiceException;
	
	

}