package it.sogei.libro_firma.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sogei.libro_firma.data.entity.FiltriConfigModel;
import it.sogei.libro_firma.data.entity.GruppoModel;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.ContatoriRicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.CountRicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.FiltroAvanzatoDTO;
import it.sogei.libro_firma.data.model.RicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.RicercaFiltriDTO;
import it.sogei.libro_firma.data.model.enums.FiltriEnum;
import it.sogei.libro_firma.data.repository.DocumentoRepository;
import it.sogei.libro_firma.data.repository.FiltriConfigRepository;
import it.sogei.libro_firma.data.repository.GruppoRepository;
import it.sogei.libro_firma.data.service.ILibroFirmaDataFiltriService;
import it.sogei.libro_firma.data.util.GruppoRicercaParamsDTO;

@Service
public class LibroFirmaDataFiltriService implements ILibroFirmaDataFiltriService{

	private Logger log = LoggerFactory.getLogger(LibroFirmaDataFiltriService.class);
	
	private static final String FILTRO_DB = "filtri-";
	private static final String CONTATORI_DB = "COUNT";
	
	@Autowired
	private FiltriConfigRepository filtriRepository;
	
	@Autowired
	private DocumentoRepository docRepository;
	
	@Autowired
	private GruppoRepository gruppoRepository;
	
	@Override
	public List<FiltroAvanzatoDTO> getFiltriAvanzati(FiltriEnum tipoFiltro) throws LibroFirmaDataServiceException {
		log.info("getFiltriAvanzati: START - tipoFiltro={}", tipoFiltro);

		List<FiltroAvanzatoDTO> filtriAvanzatiDTO;
		FiltriConfigModel model;
		Optional<FiltriConfigModel> filtroOpt = filtriRepository.findById(FILTRO_DB + tipoFiltro);
		if(filtroOpt.isPresent()) {
			model = filtroOpt.get();
		}
		else {
			throw new LibroFirmaDataServiceException("Filtro non trovato");
		}
		filtriAvanzatiDTO = model.getFiltriAvanzati().stream().map(filtro -> {
			FiltroAvanzatoDTO dto = new FiltroAvanzatoDTO();
			dto.setFiltri(filtro.getFiltri());
			dto.setIdRiga(filtro.getIdRiga());
			return dto;
		}).collect(Collectors.toList());
		
		return filtriAvanzatiDTO;
	}

	/**
	 * 
	 */
	@Override
	public List<ContatoriRicercaAvanzataDTO> countRicercaAvanzata(CountRicercaAvanzataDTO countDTO, String username) throws LibroFirmaDataServiceException {
		FiltriConfigModel filtriContatori;
		Optional<FiltriConfigModel> filtriOpt = filtriRepository.findById(FILTRO_DB + CONTATORI_DB);
		if(filtriOpt.isPresent()) {
			filtriContatori = filtriOpt.get();
		} else
			throw new LibroFirmaDataServiceException("Filtri contatori non trovati");
		
		List<ContatoriRicercaAvanzataDTO> result = new ArrayList<>();
		
		if (countDTO.getTipoDiFiltri().equals(FiltriEnum.TOWORK)) {
			RicercaFiltriDTO mapFiltriDto = mapFiltriDto(countDTO.getRicercaFiltri());
			if (countDTO.getParametri() == null) {
				countDTO.setParametri(new RicercaAvanzataDTO());
			}
			filtriContatori.getFiltriAvanzati().stream().forEach(filtro ->
				filtro.getFiltri().stream().forEach(codice -> {
					long temp = docRepository.countRicercaAvanzataTOWORK(codice.getCodice(), countDTO.getCodiceApplicazione(), countDTO.getParametri(), new Date(), username, mapFiltriDto);
					ContatoriRicercaAvanzataDTO dto = new ContatoriRicercaAvanzataDTO();
					dto.setCodice(codice.getCodice());
					dto.setLabel(codice.getLabel());
					dto.setNumeroElementi(temp);
					result.add(dto);
			}));
		}
		

		if (countDTO.getTipoDiFiltri().equals(FiltriEnum.SEARCH)) {
			GruppoModel gruppo;
			GruppoRicercaParamsDTO ricercaGruppo;
			if (!StringUtils.isBlank(countDTO.getIdGruppo())) {
				gruppo = gruppoRepository.findByIdGruppo(Integer.parseInt(countDTO.getIdGruppo()));
				ricercaGruppo = getGruppo(gruppo);
			}
			else {
				ricercaGruppo = new GruppoRicercaParamsDTO();
			}
			
			RicercaFiltriDTO mapFiltriDto = mapFiltriDto(countDTO.getRicercaFiltri());
			if (countDTO.getParametri() == null) {
				countDTO.setParametri(new RicercaAvanzataDTO());
			}
			filtriContatori.getFiltriAvanzati().stream().forEach(filtro ->
				filtro.getFiltri().stream().forEach(codice -> {
					long temp = docRepository.countRicercaAvanzataSEARCH(codice.getCodice(), countDTO.getParam(), countDTO.getParametri(), new Date(), username, ricercaGruppo, mapFiltriDto);
					ContatoriRicercaAvanzataDTO dto = new ContatoriRicercaAvanzataDTO();
					dto.setCodice(codice.getCodice());
					dto.setLabel(codice.getLabel());
					dto.setNumeroElementi(temp);
					result.add(dto);
			}));
		}		

		
		return result;
	}
	
	private GruppoRicercaParamsDTO getGruppo(GruppoModel gruppo) {
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
	 * @param ricercaFiltri
	 * @return
	 */
	private RicercaFiltriDTO mapFiltriDto(List<String> ricercaFiltri) {
		RicercaFiltriDTO ricercafiltroDto = new RicercaFiltriDTO();

		if (ricercaFiltri == null || ricercaFiltri.isEmpty())
			return ricercafiltroDto;

		List<String> listaStatoDocumento = new ArrayList<>();

		ricercaFiltri.stream().forEach(filtro -> {
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
			default:
				listaStatoDocumento.add(filtro);
			}
		});
		ricercafiltroDto.setListaStatiDocumento(listaStatoDocumento);
		return ricercafiltroDto;

	}
}
