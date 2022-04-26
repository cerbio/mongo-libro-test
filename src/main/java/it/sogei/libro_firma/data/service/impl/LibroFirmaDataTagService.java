package it.sogei.libro_firma.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.util.StringUtils;
import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.entity.TagDocumentoModel;
import it.sogei.libro_firma.data.entity.TagModel;
import it.sogei.libro_firma.data.exception.LibroFirmaDataServiceException;
import it.sogei.libro_firma.data.model.GestisciTag;
import it.sogei.libro_firma.data.model.TagDTO;
import it.sogei.libro_firma.data.model.TagDocumentoDTO;
import it.sogei.libro_firma.data.model.TagRequestDTO;
import it.sogei.libro_firma.data.model.TagResponseDTO;
import it.sogei.libro_firma.data.model.TagsResponseDTO;
import it.sogei.libro_firma.data.repository.DocumentoRepository;
import it.sogei.libro_firma.data.repository.TagRepository;
import it.sogei.libro_firma.data.service.ILibroFirmaDataTagService;
import it.sogei.libro_firma.data.util.TagMapper;

@Service
public class LibroFirmaDataTagService implements ILibroFirmaDataTagService {

	private Logger log = LoggerFactory.getLogger(LibroFirmaDataTagService.class);
	
	public static final long LIMITE_TAG = 3;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired 
	DocumentoRepository docRepository;
	
	public LibroFirmaDataTagService() {
		super();
	}

	/**
	 * @see ILibroFirmaDataTagService
	 */
	@Override
	public String saveTag(TagDTO tagDTO) throws LibroFirmaDataServiceException {
		log.info("saveTag: START - tagName={}, utenteCreatore={}", tagDTO.getNome(),tagDTO.getUtenteCreatore());
		
		if (tagRepository.checkByGruppo(tagDTO.getNome(), tagDTO.getGruppi()))
			throw new LibroFirmaDataServiceException("Tag "+ tagDTO.getNome() + " già esistente");
	
		
		TagModel model = TagMapper.tagDtoToTagModel(tagDTO);
		
		return tagRepository.save(model).getId();
	}

	/**
	 * @see ILibroFirmaDataTagService
	 */
	@Override
	public List<String> getTagsByGroup( String codiceGruppo, String nomeTag,String username) {
		log.info("getTagsByGroup: START -  codiceGruppo={}", codiceGruppo);
		List<String> listGruppi = new ArrayList<>();
		listGruppi.add(codiceGruppo);
		List<TagModel> tagsModel = tagRepository.findByGroupOrUtenteCreatore(listGruppi,nomeTag,username);
		List<String> tags = new ArrayList<>();
		if (tagsModel.isEmpty())
			return tags;
		
		tags = tagsModel.stream().map(TagModel::getNome).collect(Collectors.toList());
		return tags;
	}

	/**
	 * @see ILibroFirmaDataTagService
	 */
	@Override
	public void insertTagSuDocumento(TagDocumentoDTO tagDTO, String username) throws LibroFirmaDataServiceException {
		log.info("insertTagSuDocumento: START -  idDocumento={}, user={}",tagDTO.getIdDocumento(), username);
		
		Optional<DocumentoModel> doc = docRepository.findById(tagDTO.getIdDocumento());
		DocumentoModel docModel;
		//controllo se il documento è presente e lo recupero
		if (!doc.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		docModel = doc.get();
		List<TagDocumentoModel> tags = new ArrayList<>();
		if (docModel.getTags() == null)
			docModel.setTags(tags);
		else
			tags = docModel.getTags();
		// Controllo se il numero di tag è corretto per l'inserimento
		checkNumeroTag(docModel, tagDTO.getTags().size());
		
		
		// Controllo che i tag siano effettivamente presenti sul db e che siano utilizzabili dall'utente
		for (String tag : tagDTO.getTags()) {
			
			if(!tagRepository.existsByGruppoOrUtenteCreatore(tag, tagDTO.getCodiciGruppoUtente(), username)) {
				throw new LibroFirmaDataServiceException("Tag " + tag + " non valido");
			}
			List<TagModel> listatag =tagRepository.findByGroupOrUtenteCreatore(tagDTO.getCodiciGruppoUtente(),tag, username);
			if(listatag == null || listatag.isEmpty()) {
				throw new LibroFirmaDataServiceException("Impossibile recuperare il tag " + tag, HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
						
			//Controllo che il tag non sia già presente sul documento
			if (docModel.getTags().stream().noneMatch(t -> t.getNome().equals(tag))) {
				TagDocumentoModel tagToAdd= new TagDocumentoModel();
				TagModel tmp = listatag.stream().filter(tagTmp -> tagTmp.getUtenteCreatore().equals(username)).findFirst().orElse(listatag.get(0));
				tagToAdd.setIdTag(tmp.getId());
				tagToAdd.setNome(tag);
				tags.add(tagToAdd);
			}
		}

		docModel.setTags(tags);
		docRepository.save(docModel);	
	}
	
	/**
	 * @see ILibroFirmaDataTagService
	 */
	@Override
	public void removeTagSuDocumento(TagDocumentoDTO tagDTO, String username) throws LibroFirmaDataServiceException {
		log.info("removeTagSuDocumento: START -  idDocumento={}, user={}",tagDTO.getIdDocumento(), username);
		
		Optional<DocumentoModel> doc = docRepository.findById(tagDTO.getIdDocumento());
		DocumentoModel docModel;
		//controllo se il documento è presente e lo recupero
		if (!doc.isPresent()) {
			throw new LibroFirmaDataServiceException("Documento non trovato");
		}
		docModel = doc.get();
		if (docModel.getTags() == null || docModel.getTags().isEmpty()) {
			throw new LibroFirmaDataServiceException("Tag  non trovati nel documento "+ tagDTO.getIdDocumento());
		}
		List<TagDocumentoModel> tags  = docModel.getTags();
		
		// Controllo che i tag siano effettivamente presenti sul db e che siano utilizzabili dall'utente
		for (String tag : tagDTO.getTags()) {
				if(!tagRepository.existsByGruppoOrUtenteCreatore(tag, tagDTO.getCodiciGruppoUtente(), username))
					throw new LibroFirmaDataServiceException("Tag " + tag + " non rimuovibile dall'utente ");
			
			//Controllo che il tag sia presente sul documento
			if (docModel.getTags().stream().anyMatch(t -> t.getNome().equals(tag))) {
				tags.removeIf(nome -> nome.getNome().equals(tag));
			}
		}

		docModel.setTags(tags);
		docRepository.save(docModel);	
	}
	
	/**
	 * 
	 * @param doc
	 * @param numeroTagDaInserire
	 * @return
	 * @throws LibroFirmaDataServiceException
	 */
	private boolean checkNumeroTag(DocumentoModel doc, long numeroTagDaInserire) throws LibroFirmaDataServiceException {
		long tagPresenti = doc.getTags().size();
		if (numeroTagDaInserire > LIMITE_TAG) {
			throw new LibroFirmaDataServiceException("Numero di tag da inserire maggiore al numero di tag inseribili");
		}
		if (tagPresenti == LIMITE_TAG) {
			throw new LibroFirmaDataServiceException("Il documento ha già raggiunto il numero massimo di tag assegnabili");
		}
		if ((tagPresenti + numeroTagDaInserire) > LIMITE_TAG) {
			throw new LibroFirmaDataServiceException("Numero massimo di tag aggiungibili: " + (LIMITE_TAG - tagPresenti));
		}
		
		return true;
	}
	
	/**
	 * metodo per la modifica e rimozione di un tag. Se il nome è null allora si
	 * tratta di una rimozione altrimenti rimuovo il vecchcio tag e inserisco il
	 * nuovo
	 */
	@Override
	public void gestisciTag(GestisciTag gestisciTag, String username) throws LibroFirmaDataServiceException {
		log.info("gestisciTag: START - tagName={}, utenteCreatore={}", gestisciTag.getName(), username);

		Optional<TagModel> tagOptional = tagRepository.findById(gestisciTag.getId());
		if (!tagOptional.isPresent())
			throw new LibroFirmaDataServiceException("Tag non esistente");
		TagModel tag = tagOptional.get();
		if (!tag.getUtenteCreatore().equals(username))
			throw new LibroFirmaDataServiceException("Utente " + username + " non associato al tag: " + gestisciTag.getId());
		if(!tag.getNome().equals(gestisciTag.getName()))
			throw new LibroFirmaDataServiceException("Id tag: "+gestisciTag.getId()+" non coincide con il nome tag: "+gestisciTag.getName());
		if (StringUtils.isNotBlank(gestisciTag.getNameNew())) {
			tag.setNome(gestisciTag.getNameNew());
			tag.setUltimaModifica(new Date());
			tagRepository.save(tag);
		}else {
			tagRepository.delete(tag);
		}

		modificaTagDocumenti(gestisciTag.getId(), gestisciTag.getNameNew());

	}

	/*
	 * metodo per la modifica e rimozione di un tag.se il nome è null allora si
	 * tratta di una rimozione altrimenti rimuovo il vechcio tag e inserisco il
	 * nuovo
	 */
	private void modificaTagDocumenti(String id, String nameNew) {
		List<DocumentoModel> listaDocumenti = docRepository.recuperaDocumentiByTag(id);
		for (DocumentoModel documentoModel : listaDocumenti) {

			List<TagDocumentoModel> tags = documentoModel.getTags();
			if (StringUtils.isBlank(nameNew)) {
				tags.removeIf(tag -> tag.getIdTag().equals(id));
			}
			else {
				tags.stream().filter( tag -> tag.getIdTag().equals(id)).findFirst().ifPresent(temp -> temp.setNome(nameNew));
			}
			docRepository.save(documentoModel);
		}
	}

	/**
	 * @see ILibroFirmaDataTagService
	 */
	@Override
	public TagsResponseDTO getTags(TagRequestDTO tagRequestDto, String username) throws LibroFirmaDataServiceException {
		TagsResponseDTO response = new TagsResponseDTO();

		if (tagRequestDto.getNumeroPagina() == null || tagRequestDto.getNumeroPagina() <= 0) {
			tagRequestDto.setNumeroPagina(1);
		}
		if (tagRequestDto.getNumeroElementiPagina() == null || tagRequestDto.getNumeroElementiPagina() <= 0) {
			tagRequestDto.setNumeroElementiPagina(10);
		}

		Pageable p = PageRequest.of(tagRequestDto.getNumeroPagina()-1, tagRequestDto.getNumeroElementiPagina());
		List<TagModel> tagsModel = tagRepository.getByGroupOrUtenteCreatore(tagRequestDto.getIdGruppo(), tagRequestDto.getNomeTag(), username, p);
		int numeroTotali = tagRepository.countByGroupOrUtenteCreatore(tagRequestDto.getIdGruppo(), tagRequestDto.getNomeTag(), username);
		
		List<TagResponseDTO> listTagResponseDTO = new ArrayList<>();
		for (TagModel tagModel : tagsModel) {
			TagResponseDTO responseTag = new TagResponseDTO();
			responseTag.setCreatore(tagModel.getUtenteCreatore().equals(username));
			responseTag.setIdTag(tagModel.getId());
			responseTag.setNomeTag(tagModel.getNome());
			responseTag.setUtenteCreatore(tagModel.getUtenteCreatore());
			listTagResponseDTO.add(responseTag);
		}

		response.setNumeroElementiPagina(tagRequestDto.getNumeroElementiPagina());
		response.setNumeroPagina(tagRequestDto.getNumeroPagina());
		response.setNumeroTotaleElementi(numeroTotali);
		response.setTags(listTagResponseDTO);
		return response;
	}

	/**
	 * @see ILibroFirmaDataTagService
	 */
	@Override
	public void associaTag(TagDTO tagDto, String username) {
		log.info("LibroFirmaDataTagService.associaTag: START - utenteCreatore={}, gruppi={}", tagDto.getUtenteCreatore(), tagDto.getGruppi());
		
		List<TagModel> listaTags = tagRepository.findByGroupOrUtenteCreatore(null, null, tagDto.getUtenteCreatore());
		
		if(listaTags == null || listaTags.isEmpty()) {
			return;
		}
		for(TagModel tag : listaTags) {
			log.info("LibroFirmaDataTagService.associaTag: aggiungo al tag={}, utenteCreatore={}, gruppi={}", tag.getId(), tagDto.getUtenteCreatore(), tagDto.getGruppi());
			addGroups(tag, tagDto.getGruppi());
			log.info("LibroFirmaDataTagService.associaTag: aggiunti al tag={}, utenteCreatore={}, gruppi={}", tag.getId(), tagDto.getUtenteCreatore(), tagDto.getGruppi());
		}
		tagRepository.saveAll(listaTags);
		log.info("LibroFirmaDataTagService.associaTag: END - utenteCreatore={}, gruppi={}", tagDto.getUtenteCreatore(), tagDto.getGruppi());
	}

	/**
	 * 
	 * @param tag
	 * @param gruppi
	 */
	private void addGroups(TagModel tag, List<String> gruppi) {
		if(tag.getGruppi() == null) {
			tag.setGruppi(new ArrayList<>());
		}
		for(String gruppo : gruppi) {
			if(tag.getGruppi().indexOf(gruppo) < 0) {
				tag.getGruppi().add(gruppo);
			}
		}
	}

}

