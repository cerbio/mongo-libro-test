package it.sogei.libro_firma.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.sogei.libro_firma.data.entity.DataConfigModel;
import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.entity.GruppoModel;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.CountAppDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.FiltersDTO;
import it.sogei.libro_firma.data.model.FindParamsByGroupDTO;
import it.sogei.libro_firma.data.model.FindParamsDTO;
import it.sogei.libro_firma.data.model.FindToWorkByAppDTO;
import it.sogei.libro_firma.data.model.OrdinamentoFieldDTO;
import it.sogei.libro_firma.data.model.PreviewDTO;
import it.sogei.libro_firma.data.model.RicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.RicercaFiltriDTO;
import it.sogei.libro_firma.data.model.RicercaTabellareDTO;
import it.sogei.libro_firma.data.model.SearchResultDTO;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;
import it.sogei.libro_firma.data.model.enums.OrdinamentoEnum;
import it.sogei.libro_firma.data.model.enums.TipoOrdinamentoEnum;
import it.sogei.libro_firma.data.repository.DataConfigRepository;
import it.sogei.libro_firma.data.repository.DocumentoRepository;
import it.sogei.libro_firma.data.repository.GruppoRepository;
import it.sogei.libro_firma.data.service.ILibroFirmaDataDocumentiService;
import it.sogei.libro_firma.data.service.ILibroFirmaDataSearchService;
import it.sogei.libro_firma.data.util.DocumentoMapper;
import it.sogei.libro_firma.data.util.GruppoRicercaParamsDTO;

@Service
public class LibroFirmaDataSearchService implements ILibroFirmaDataSearchService {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaDataSearchService.class);
	
	private static final String FIND_BY_PARAM_FOR_PREVIEW = "findByParamsForPreview";
	private static final String SORT_DATE = "dataCreazione";
	
	@Autowired
	private DocumentoRepository docRepository;
	
	@Autowired
	private DataConfigRepository dataConfigRepository;
	
	@Autowired
	private GruppoRepository gruppoRepository;
	
	@Autowired
	private LibroFirmaDataPermessiUtil permessiUtils;
	
	public LibroFirmaDataSearchService() {
		super();
	}

	/**
	 * 
	 * @param utente
	 * @return
	 */
	@Override
	public List<DocumentoDTO> findByUtente(String utente) {
		log.info("findByUtente: START - utente={}", utente);
		List<DocumentoModel> listaDocumenti = docRepository.findByUtente(utente);
		return DocumentoMapper.fromDocumentoModelListToDocumentoDTOList(listaDocumenti);
	}

	/**
	 * 
	 * @param utente
	 * @return
	 */
	@Override
	public List<CountAppDTO> countByApp(String utente) {
		log.info("countByApp: START - utente={}", utente);
		List<CountAppDTO> listaContatori = new ArrayList<>();
//		--List<CountAppAggregation> listaCountAppModel = docRepository.countByApp(utente);--
		List<String> listaAppDocDaLavorare = docRepository.findDistinctAppProduttore(utente);
		if(listaAppDocDaLavorare == null || listaAppDocDaLavorare.isEmpty()) {
			return listaContatori;
		}
		Date actualDate = new Date();
		for(String app : listaAppDocDaLavorare) {
			CountAppDTO contatore = new CountAppDTO();
			contatore.setCodiceApplicazione(app);
			contatore.setCountDocumenti(docRepository.countToWorkByApp(utente, app, new RicercaFiltriDTO()));
			contatore.setCountDocumentiPrioritari(docRepository.countToWorkByAppPrioritari(utente, app, actualDate));
			listaContatori.add(contatore);
		}
		return listaContatori;
//		--return DocumentoMapper.fromCountAppAggregationListToCountAppDtoList(listaCountAppModel);--
	}
	
	/**
	 * 
	 * @param param
	 * @param utente
	 * @return
	 */
	@Override
	public List<DocumentoDTO> findByParam(String param, String utente) {
		log.info("countByApp: START - testoCercato={}, utente={}", param, utente);
		List<DocumentoModel> listaDocumenti = docRepository.findByParam(utente, param);
		return DocumentoMapper.fromDocumentoModelListToDocumentoDTOList(listaDocumenti);
	}

	/**
	 * 
	 * @param parametri, utente
	 * @param utente
	 * @return
	 */
	@Override
	public List<PreviewDTO> findByParamForPreview(FindParamsDTO parametri, String utente) {
		Optional<DataConfigModel> config = dataConfigRepository.findById(FIND_BY_PARAM_FOR_PREVIEW);
		String groupByParam = "";
		if(config.isPresent()) {
			groupByParam = config.get().getValore();
		} 		
		List<GruppoModel> listaGruppi = gruppoRepository.findByTipoGruppo(groupByParam);
		List<PreviewDTO> listaPreviewDocumenti = new ArrayList<>();
		Long limite = parametri.getLimitePerStato() == 0 ? 3 : parametri.getLimitePerStato();
		for(GruppoModel gruppo : listaGruppi) {
			Pageable p = PageRequest.of(0, limite.intValue());
			GruppoRicercaParamsDTO gruppoRicerca = getGruppoRicerca(gruppo);
//			--List<DocumentoModel> listaDocumenti = docRepository.findByParamsAndStato(utente, parametri.getParam(), gruppo.getValori(), groupByParam, p);--
			List<DocumentoModel> listaDocumenti = docRepository.findByParamsAndStato(utente, parametri.getParam(), gruppoRicerca, p);
			int numeroRisultati = docRepository.countByParamsAndStato(utente, parametri.getParam(), gruppoRicerca);
			if(listaDocumenti == null || listaDocumenti.isEmpty()) {
				continue;
			}
			List<DatiDocumentoDTO> listaDocumentiDto = DocumentoMapper.fromListDocumentoModelToListDatiDocumentoDTO(listaDocumenti, utente);
//			--List<DatiDocumentoDTO> listaDocumentiDto = DocumentoMapper.fromDocumentoModelListToDocumentoDTOList(listaDocumenti);--
			PreviewDTO previewDocumentiDTO = new PreviewDTO();
			previewDocumentiDTO.setListaDocumenti(listaDocumentiDto);
			previewDocumentiDTO.setIdGruppo(gruppo.getIdGruppo());
			previewDocumentiDTO.setNumeroDoc(numeroRisultati);
			previewDocumentiDTO.setLabelStatoDocumento(gruppo.getLabel());
			listaPreviewDocumenti.add(previewDocumentiDTO);
		}
		return listaPreviewDocumenti;
	}
	
	/**
	 * 
	 * @param parametri, utente
	 * @param utente
	 * @return
	 */
	@Override
	public SearchResultDTO viewAllForPreview(FindParamsByGroupDTO parametri, String username) {
		
		if(parametri.getNumeroPagina() == null || parametri.getNumeroPagina() <= 0) {
			parametri.setNumeroPagina(1);
		}
		if(parametri.getNumeroElementiPagina() == null || parametri.getNumeroElementiPagina() <= 0) {
			parametri.setNumeroElementiPagina(10);
		}
		Sort sortingType = getSortingType(parametri.getOrdinamento());
		Pageable p = PageRequest.of(parametri.getNumeroPagina()-1, parametri.getNumeroElementiPagina(), sortingType);
		int numeroElementi;
		List<DocumentoModel> listaDocumenti;
		FiltersDTO filtri = new FiltersDTO();
		filtri.setParam(parametri.getParam());
		
		RicercaFiltriDTO ricercaFiltriDto = mapFiltriDto(parametri.getRicercaFiltri());
		if(parametri.getRicercaAvanzata() == null) {
			parametri.setRicercaAvanzata(new RicercaAvanzataDTO());
		}
		checkDatesRicercaAvanzata(parametri.getRicercaAvanzata());
		
		if (parametri.getIdGruppo() == null || parametri.getIdGruppo().isEmpty()) {
			// ricerca senza id gruppo
			listaDocumenti = docRepository.findByUtenteAndFileName(username,parametri.getParam(),parametri.getRicercaAvanzata(),ricercaFiltriDto,p);
			numeroElementi = docRepository.countByUtenteAndFileName(username, parametri.getParam(),parametri.getRicercaAvanzata(),ricercaFiltriDto);
		}
		else {
			// ricerca con id gruppo
			GruppoModel group = gruppoRepository.findByIdGruppo(Integer.parseInt(parametri.getIdGruppo()));
			GruppoRicercaParamsDTO gruppoRicerca = getGruppoRicerca(group);
			listaDocumenti = docRepository.findByParamsAndGroup(username,parametri.getParam(), gruppoRicerca, parametri.getRicercaAvanzata(), ricercaFiltriDto, p);
			numeroElementi = docRepository.countByParamsAndGroup(username, parametri.getParam(), gruppoRicerca, parametri.getRicercaAvanzata(), ricercaFiltriDto);
			filtri.setStatoDocumento(group.getValori());
		}
		List<DatiDocumentoDTO> listaDocumentiDTO = DocumentoMapper.fromListDocumentoModelToListDatiDocumentoDTO(listaDocumenti, username);
		
		SearchResultDTO result = new SearchResultDTO();
		result.setListaDocumenti(listaDocumentiDTO);
		result.setNumeroElementiPagina(parametri.getNumeroElementiPagina());
		result.setNumeroPagina(parametri.getNumeroPagina());
		result.setNumeroElementiTotali(numeroElementi);
		result.setFiltri(filtri);
		
		return result;
	}

	/**
	 * 
	 * @param parametri, utente
	 * @param utente
	 * @return
	 */
	@Override
	public SearchResultDTO findToWorkByApp(FindToWorkByAppDTO findToWorkByAppDTO, String utente) {
		if(findToWorkByAppDTO.getNumeroPagina() == null || findToWorkByAppDTO.getNumeroPagina() <= 0) {
			findToWorkByAppDTO.setNumeroPagina(1);
		}
		if(findToWorkByAppDTO.getNumeroElementiPagina() == null || findToWorkByAppDTO.getNumeroElementiPagina() <= 0) {
			findToWorkByAppDTO.setNumeroElementiPagina(10);
		}
		
		Sort sortingType = getSortingType(findToWorkByAppDTO.getOrdinamento());
		
		Pageable p = PageRequest.of(findToWorkByAppDTO.getNumeroPagina()-1, findToWorkByAppDTO.getNumeroElementiPagina(), sortingType);
		FiltersDTO filtri = new FiltersDTO();
		filtri.setCodiceApplicazione(findToWorkByAppDTO.getCodiceApplicazione());
		RicercaFiltriDTO ricercaFiltriDto= mapFiltriDto(findToWorkByAppDTO.getRicercaFiltri());
		
		List<DocumentoModel> listaDocumenti = docRepository.findToWorkByApp(utente, findToWorkByAppDTO.getCodiceApplicazione(), ricercaFiltriDto, p);
		List<DatiDocumentoDTO> listaDocumentiDTO = DocumentoMapper.fromListDocumentoModelToListDatiDocumentoDTO(listaDocumenti, utente);
		SearchResultDTO result = new SearchResultDTO();
		result.setListaDocumenti(listaDocumentiDTO);
		result.setNumeroElementiPagina(findToWorkByAppDTO.getNumeroElementiPagina());
		result.setNumeroPagina(findToWorkByAppDTO.getNumeroPagina());
		result.setNumeroElementiTotali((int)docRepository.countToWorkByApp(utente, findToWorkByAppDTO.getCodiceApplicazione(),ricercaFiltriDto));
		result.setFiltri(filtri);
		return result;
	}
	
	/**
	 * 
	 * @param utente
	 * @return
	 */
	@Override
	public SearchResultDTO findToWork(FindToWorkByAppDTO findToWorkByAppDTO, String utente) {
		if(findToWorkByAppDTO.getNumeroPagina() == null || findToWorkByAppDTO.getNumeroPagina() <= 0) {
			findToWorkByAppDTO.setNumeroPagina(1);
		}
		if(findToWorkByAppDTO.getNumeroElementiPagina() == null || findToWorkByAppDTO.getNumeroElementiPagina() <= 0) {
			findToWorkByAppDTO.setNumeroElementiPagina(10);
		}
		Sort sortingType = getSortingType(findToWorkByAppDTO.getOrdinamento());
		
		Pageable p = PageRequest.of(findToWorkByAppDTO.getNumeroPagina() - 1, findToWorkByAppDTO.getNumeroElementiPagina(), sortingType);
		RicercaFiltriDTO ricercaFiltriDto= mapFiltriDto(findToWorkByAppDTO.getRicercaFiltri());
		
		List<DocumentoModel> listaDocumenti = docRepository.findToWork(utente, ricercaFiltriDto, p);
		SearchResultDTO result = new SearchResultDTO();
		List<DatiDocumentoDTO> listaDocumentiDTO = DocumentoMapper.fromListDocumentoModelToListDatiDocumentoDTO(listaDocumenti, utente);
		result.setListaDocumenti(listaDocumentiDTO);
		result.setNumeroElementiPagina(findToWorkByAppDTO.getNumeroElementiPagina());
		result.setNumeroPagina(findToWorkByAppDTO.getNumeroPagina());
		result.setNumeroElementiTotali((int)docRepository.countToWork(utente, ricercaFiltriDto));
		return result;
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public Long countPrioritari(String username) {
		RicercaTabellareDTO ricercaTab = new RicercaTabellareDTO();
		log.info("LibroFirmaDataSearchService.countPrioritari: utente={}", username);
		Long numeroDocumentiPrioritari = docRepository.countToWorkPrioritari(username, ricercaTab, new Date());
		log.info("LibroFirmaDataSearchService.countPrioritari: utente={}, numeroDocPrioritari={}", username, numeroDocumentiPrioritari);
		return numeroDocumentiPrioritari;
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public List<DatiDocumentoDTO> ricercaAvanzata(RicercaAvanzataDTO ricercaAvanzataDto,String user) throws LibroFirmaDataServiceException{
	
		log.info("LibroFirmaDataSearchService.ricercaAvanzata: idDocumento={}", ricercaAvanzataDto.getNomeDocumento());
		List<DocumentoModel> datiDocumento = docRepository.ricercaAvanzata(ricercaAvanzataDto);
		return DocumentoMapper.fromListDocumentoModelToListDatiDocumentoDTO(datiDocumento,user);
		
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public SearchResultDTO findDocPrioritari(RicercaTabellareDTO ricercaTabellareDTO, String username) {
		
		if(ricercaTabellareDTO.getNumeroPagina() <= 0) {
			ricercaTabellareDTO.setNumeroPagina(1);
		}
		if(ricercaTabellareDTO.getNumeroElementiPerPagina()  <= 0) {
			ricercaTabellareDTO.setNumeroElementiPerPagina(10);
		}
		
		Sort sortingType;
		Sort.Order[] listaOrdinamenti = getListaOrdinamenti(ricercaTabellareDTO.getOrdinamento());
		if(listaOrdinamenti.length > 0) {
			sortingType = Sort.by(listaOrdinamenti);
		}
		else {
			sortingType = Sort.by(SORT_DATE).descending();
		}
			
		Pageable p = PageRequest.of(ricercaTabellareDTO.getNumeroPagina()-1, ricercaTabellareDTO.getNumeroElementiPerPagina(), sortingType);
		int numeroElementi;
		List<DocumentoModel> listaDocumenti;

		listaDocumenti = docRepository.findDocPrioritari(username, ricercaTabellareDTO, new Date(), p);
		numeroElementi = docRepository.countToWorkPrioritari(username, ricercaTabellareDTO, new Date()).intValue();

		List<DatiDocumentoDTO> listaDocumentiDTO = DocumentoMapper.fromListDocumentoModelToListDatiDocumentoDTO(listaDocumenti, username);
		
		SearchResultDTO result = new SearchResultDTO();
		result.setListaDocumenti(listaDocumentiDTO);
		result.setNumeroElementiPagina(ricercaTabellareDTO.getNumeroElementiPerPagina());
		result.setNumeroPagina(ricercaTabellareDTO.getNumeroPagina());
		result.setNumeroElementiTotali(numeroElementi);
		
		return result;
	}
	
	/**
	 * @see ILibroFirmaDataDocumentiService
	 */
	@Override
	public DatiDocumentoDTO getForAction(String idDocumento, AzioneEnum azione, String username) throws LibroFirmaDataServiceException {
		log.info("LibroFirmaDataSearchService.getForAction: idDocumento={}, azione={}, utente={}", idDocumento, azione, username);
		DocumentoModel model = docRepository.findById(idDocumento).orElse(null);
		if(model == null) {
			throw new LibroFirmaDataServiceException("Documento non trovato", HttpStatus.NOT_FOUND.value());
		}
		boolean canDo;
		switch(azione) {
			case VIEW: canDo = permessiUtils.getForView(username, model); break;
			case DELETE: canDo = permessiUtils.getForDelete(username, model); break;
			case FIRMA: canDo = permessiUtils.getForSign(username, model); break;
			case CONDIVISIONE: canDo = permessiUtils.getForShare(username, model); break;
			default: throw new LibroFirmaDataServiceException("Azione " + azione + " non consentita", HttpStatus.FORBIDDEN.value());
		}
		if(canDo) {
			return DocumentoMapper.fromDocumentoModelToDatiDocumentoDTO(model, username);
		}
		throw new LibroFirmaDataServiceException("Azione non consentita sul documento " + model.getNomeDocumento(), HttpStatus.FORBIDDEN.value());
	}
	
	/**
	 * 
	 * @param gruppo
	 * @return
	 */
	private GruppoRicercaParamsDTO getGruppoRicerca(GruppoModel gruppo) {
		GruppoRicercaParamsDTO gruppoRicerca = new GruppoRicercaParamsDTO();
		gruppoRicerca.setFieldToFilter(gruppo.getTipoGruppo());
		gruppoRicerca.setValori(new ArrayList<>());
		if(gruppo.isValoreBooleano()) {
			gruppoRicerca.setFlag(Boolean.parseBoolean(gruppo.getValori().get(0)));
		}
		else {
			gruppoRicerca.setValori(gruppo.getValori());
		}
		return gruppoRicerca;
	}
	
	/**
	 * 
	 * @param ordinamentoEnum
	 * @return
	 */
	private Sort getSortingType(OrdinamentoEnum ordinamentoEnum) {
		if(ordinamentoEnum != null) {
			
			Sort.Order[] listaOrdinamenti = getListaOrdinamenti(ordinamentoEnum);
			if(listaOrdinamenti != null) {
				return Sort.by(listaOrdinamenti);
			}
		}
		return Sort.by(SORT_DATE).descending();
	}
	
	/**
	 * 
	 * @param ordinamentoEnum
	 * @return
	 */
	private Order[] getListaOrdinamenti(OrdinamentoEnum ordinamentoEnum) {
		Order[] listaOrd = new Order[ordinamentoEnum.getCampiDaOrdinare().length];
		int i = 0;
		for(String ordinamento : ordinamentoEnum.getCampiDaOrdinare()) {
			Order ord = getOrdinamento(ordinamento);
			if(ord != null) {
				listaOrd[i] = ord;
				i++;
			}
		}
		return i > 0 ? listaOrd : null;
	}

	/**
	 * 
	 * @param ordinamento
	 * @return
	 */
	private Order getOrdinamento(String ordinamento) {
		String[] parametriOrdinamento = ordinamento.split("-");
		if(parametriOrdinamento.length != 2) {
			return null;
		}
		boolean nullLast = parametriOrdinamento.length == 3;
		Order order;
		if(TipoOrdinamentoEnum.ASC.name().equalsIgnoreCase(parametriOrdinamento[1])) {
			order = Order.asc(parametriOrdinamento[0]);
		}
		else {
			order = Order.desc(parametriOrdinamento[0]);
		}
		return nullLast ? order.nullsLast() : order;
	}

	/**
	 * 
	 * @param ricercaFiltri
	 * @return
	 */
	private RicercaFiltriDTO mapFiltriDto(List<String> ricercaFiltri) {
		RicercaFiltriDTO ricercafiltroDto = new RicercaFiltriDTO();

		if (ricercaFiltri == null || ricercaFiltri.isEmpty())
			return ricercafiltroDto;

		List<String> listaStatoDocumento = new ArrayList<>();

		ricercaFiltri.stream().forEach(filtro -> {
			if(StringUtils.isBlank(filtro)) {
				return;
			}
			switch (filtro) {
			case "URGENTE":
				ricercafiltroDto.setUrgente(true);
				break;
			case "IN_SCADENZA":
				ricercafiltroDto.setInScadenza(true);
				break;
			case "SCADUTO":
				ricercafiltroDto.setScaduto(true);
				break;
			case "VERIFICATO":
				ricercafiltroDto.setVerificato(true);
				break;
			case "CONDIVISO":
				ricercafiltroDto.setCondiviso(true);
//				--listaStatoDocumento.add(filtro);--
				break;
			default:
				listaStatoDocumento.add(filtro);
			}
		});
		ricercafiltroDto.setListaStatiDocumento(listaStatoDocumento);
		return ricercafiltroDto;
	}
	
	/**
	 * 
	 * @param ricercaAvanzata
	 */
	private void checkDatesRicercaAvanzata(RicercaAvanzataDTO ricercaAvanzata) {
		if(ricercaAvanzata == null) {
			return;
		}
		if(ricercaAvanzata.getDataCaricamentoDa() != null) {
			ricercaAvanzata.setDataCaricamentoDa(permessiUtils.getRangeDate(ricercaAvanzata.getDataCaricamentoDa(), true));
		}
		if(ricercaAvanzata.getDataCaricamentoA() != null) {
			ricercaAvanzata.setDataCaricamentoA(permessiUtils.getRangeDate(ricercaAvanzata.getDataCaricamentoA(), false));
		}
		if(ricercaAvanzata.getDataFirmaDa() != null) {
			ricercaAvanzata.setDataFirmaDa(permessiUtils.getRangeDate(ricercaAvanzata.getDataFirmaDa(), true));
		}
		if(ricercaAvanzata.getDataFirmaA() != null) {
			ricercaAvanzata.setDataFirmaA(permessiUtils.getRangeDate(ricercaAvanzata.getDataFirmaA(), false));
		}
		if(ricercaAvanzata.getDataScadenzaDa() != null) {
			ricercaAvanzata.setDataScadenzaDa(permessiUtils.getRangeDate(ricercaAvanzata.getDataScadenzaDa(), true));
		}
		if(ricercaAvanzata.getDataScadenzaA() != null) {
			ricercaAvanzata.setDataScadenzaA(permessiUtils.getRangeDate(ricercaAvanzata.getDataScadenzaA(), false));
		}
	}
	
	/**
	 * 
	 * @param listaOrdinamenti
	 * @return
	 */
	private Order[] getListaOrdinamenti(List<OrdinamentoFieldDTO> listaOrdinamenti) {
		if(listaOrdinamenti == null || listaOrdinamenti.isEmpty()) {
			return new Order[0];
		}
		Order[] ordinamenti = new Order[listaOrdinamenti.size()];
		Order order;
		int i = 0;
		for(OrdinamentoFieldDTO ord : listaOrdinamenti) {
			if(TipoOrdinamentoEnum.ASC.equals(ord.getTipo())) {
				order = Order.asc(ord.getNomeCampo().getNomeCampo());
			}
			else {
				order = Order.desc(ord.getNomeCampo().getNomeCampo());
			}
			ordinamenti[i]  = order;
			i++;
		}
		return ordinamenti;
	}
	
}
