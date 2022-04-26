package it.sogei.libro_firma.data.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import it.sogei.libro_firma.data.entity.AclModel;
import it.sogei.libro_firma.data.entity.Anagrafica;
import it.sogei.libro_firma.data.entity.AssegnatarioModel;
import it.sogei.libro_firma.data.entity.CountAppAggregation;
import it.sogei.libro_firma.data.entity.DatiProcedimentoModel;
import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.entity.FascicoloModel;
import it.sogei.libro_firma.data.entity.FileDocumentoModel;
import it.sogei.libro_firma.data.entity.FirmaLFUModel;
import it.sogei.libro_firma.data.entity.OperazioneModel;
import it.sogei.libro_firma.data.entity.TagDocumentoModel;
import it.sogei.libro_firma.data.entity.VersioneModel;
import it.sogei.libro_firma.data.model.CountAppDTO;
import it.sogei.libro_firma.data.model.DatiDocumentoDTO;
import it.sogei.libro_firma.data.model.DatiProcedimento;
import it.sogei.libro_firma.data.model.DatiProcedimentoFedDTO;
import it.sogei.libro_firma.data.model.DocumentoDTO;
import it.sogei.libro_firma.data.model.DocumentoFedDTO;
import it.sogei.libro_firma.data.model.FascicoloDTO;
import it.sogei.libro_firma.data.model.FascicoloFedDTO;
import it.sogei.libro_firma.data.model.FileDocumentoDTO;
import it.sogei.libro_firma.data.model.FileFedDTO;
import it.sogei.libro_firma.data.model.FirmaLFUDto;
import it.sogei.libro_firma.data.model.MittenteDTO;
import it.sogei.libro_firma.data.model.StoricoOperazioniDTO;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;
import it.sogei.libro_firma.data.model.enums.FirmaEnum;
import it.sogei.libro_firma.data.model.enums.OperazioneEnum;
import it.sogei.libro_firma.data.model.enums.StatoAssegnazioneEnum;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;
import it.sogei.libro_firma.data.model.enums.StatoOperazioneEnum;
import it.sogei.libro_firma.data.model.enums.TipoFileEnum;
import it.sogei.libro_firma.data.model.enums.TipoFirmaEnum;
import it.sogei.libro_firma.data.model.enums.TipoOperazioneEnum;
import it.sogei.libro_firma.data.model.enums.TipoPermessoEnum;
import it.sogei.libro_firma.data.model.enums.TipoUtenteEnum;

public class DocumentoMapper {
	
	private DocumentoMapper() {
		super();
	}
	
	/**
	 * 
	 * @param listaDocumenti
	 * @return
	 */
	public static List<DocumentoDTO> fromDocumentoModelListToDocumentoDTOList(List<DocumentoModel> listaDocumenti) {
		List<DocumentoDTO> listaDocumentiDto = new ArrayList<>();
		if(listaDocumenti == null || listaDocumenti.isEmpty()) {
			return listaDocumentiDto;
		}
		for(DocumentoModel docModel : listaDocumenti) {
			listaDocumentiDto.add(fromDocumentoModelToDocumentoDTO(docModel));
		}
		return listaDocumentiDto;
	}

	/**
	 * 
	 * @param docModel
	 * @return
	 */
	private static DocumentoDTO fromDocumentoModelToDocumentoDTO(DocumentoModel docModel) {
		DocumentoDTO docDto = new DocumentoDTO();
		if (docModel.getDocumenti() != null && docModel.getDocumenti().size() == 1) {
			docDto.setIdDocumento(docModel.getId());
			docDto.setNomeDocumento(docModel.getNomeDocumento());
			docDto.setContentType(docModel.getDocumenti().get(0).getContentType()); // TEMP
			docDto.setIdEcm(docModel.getDocumenti().get(0).getIdEcm()); // TEMP
			docDto.setStatoDocumento(StatoDocumentoEnum.getByName(docModel.getStatoDocumento()));
		
			return docDto;
		}
		return docDto;
	}

	/**
	 * 
	 * @param listaCountAppModel
	 * @return
	 */
	public static List<CountAppDTO> fromCountAppAggregationListToCountAppDtoList(List<CountAppAggregation> listaCountAppModel) {
		List<CountAppDTO> listaCountApp = new ArrayList<>();
		if(listaCountAppModel == null || listaCountAppModel.isEmpty()) {
			return listaCountApp;
		}
		for(CountAppAggregation aggr : listaCountAppModel) {
			listaCountApp.add(fromCountAppAggregationToCountAppDto(aggr));
		}
		return listaCountApp;
	}

	/**
	 * 
	 * @param aggr
	 * @return
	 */
	private static CountAppDTO fromCountAppAggregationToCountAppDto(CountAppAggregation aggr) {
		CountAppDTO countAppDto = new CountAppDTO();
		countAppDto.setCodiceApplicazione(aggr.get_id());
		countAppDto.setCountDocumenti(aggr.getCnt());
		return countAppDto;
	}

	/**
	 * 
	 * @param docModel
	 * @return
	 */
	public static DatiDocumentoDTO fromDocumentoModelToDatiDocumentoDTO(DocumentoModel documento, String utente) {
		DatiDocumentoDTO documentoDTO = new DatiDocumentoDTO();
		documentoDTO.setCodiceApplicazione(documento.getAppProduttore());
		documentoDTO.setNomeApplicazione(documento.getNomeAppProduttore());
		documentoDTO.setDataCreazione(documento.getDataCaricamento() != null ? documento.getDataCaricamento() : documento.getDataCreazione());
		documentoDTO.setDataInvio(!"LFU".equalsIgnoreCase(documento.getAppProduttore()) ? documento.getDataInvio() : null);
		documentoDTO.setDataScadenza(documento.getDataScadenza());
		documentoDTO.setIdDocumento(documento.getId());
		documentoDTO.setNomeDocumento(documento.getNomeDocumento());
		documentoDTO.setStatoDocumento(getStatoDocumento(documento, utente));
		documentoDTO.setUrgente(documento.isUrgente());
		documentoDTO.setUtenteCreatore(documento.getUtenteCreatore());
		documentoDTO.setMittente(getMittente(documento.getMittente()));
		if(documento.getTags()!=null && !documento.getTags().isEmpty())
			documentoDTO.setTags(documento.getTags().stream().map(TagDocumentoModel::getNome).collect(Collectors.toList()));
		if(documento.getFirmeLfuApplicate() != null && !documento.getFirmeLfuApplicate().isEmpty()) {
			FirmaLFUModel ultimaFirma = documento.getFirmeLfuApplicate().get(documento.getFirmeLfuApplicate().size() - 1);
			documentoDTO.setTipFirmaApplicata(ultimaFirma != null ? FirmaEnum.getByName(ultimaFirma.getTipoFirma()) : null);
			documentoDTO.setModFirmaAttuata(ultimaFirma != null ? TipoFirmaEnum.getByName(ultimaFirma.getModalitaFirma()) : null);
		}
		
		for(OperazioneModel op : documento.getOperazioni()) {
			if(op.getStato().equalsIgnoreCase(StatoOperazioneEnum.APERTA.name()) && op.getUtente().equalsIgnoreCase(utente)) {
				documentoDTO.setAzioneUtente(AzioneEnum.getByName(op.getTipo()));
			}
			else if(op.getStato().equalsIgnoreCase(StatoOperazioneEnum.COMPLETATA.name()) && 
					op.getTipo().equalsIgnoreCase(TipoOperazioneEnum.FIRMA.name())) {
				documentoDTO.setDataFirma(op.getDataCompletamento());
				documentoDTO.setFirmatario(op.getUtente());
			}
		}
		boolean isCollaboratore = checkCollaboratore(documento, utente);
		if(documentoDTO.getAzioneUtente() == null && isCollaboratore) {
			documentoDTO.setAzioneUtente(AzioneEnum.PRESA_VISIONE);
		}
		documentoDTO.setTipoFirmaDaApplicare(FirmaEnum.getByName(documento.getTipoFirmaDaApplicare()));
		documentoDTO.setVerificato(documento.getVerificato());
		documentoDTO.setAltreAzioniUtente(getAltreAzioniUtente(documento, utente, (documentoDTO.getAzioneUtente() != null) && !isCollaboratore));
		documentoDTO.setAssegnatari(getAssegnatari(documento));
		documentoDTO.setCollaboratori(getCollaboratori(documento));
		documentoDTO.setDestinatari(getDestinatari(documento));
		documentoDTO.setDocumenti(fromListFileDocumentoModelToListFileDocumentoDTO(documento.getDocumenti()));
		documentoDTO.setSingleFile(documentoDTO.getDocumenti().size() == 1);
		if(documento.getAssegnazione()!=null ) {
			documentoDTO.setAssegnazionePrioritaria(documento.getAssegnazione().isPrioritario());
		}
		documentoDTO.setStatoAssegnazione(getStatoAssegnazione(documento));
		documentoDTO.setDatiProcedimento(fromDatiProcedimentoModelToDatiProcedimentoDto(documento.getDatiProcedimento()));
		documentoDTO.setRuoloUtenteDocumento(getRuoloUtenteDocumento(documento, utente));
		return documentoDTO;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	private static DatiProcedimento fromDatiProcedimentoModelToDatiProcedimentoDto(DatiProcedimentoModel model) {
		DatiProcedimento dto = new DatiProcedimento();
		if(model == null) {
			return dto;
		}
		dto.setId(model.getId());
		dto.setNomeProcedimento(model.getNomeProcedimento());
		dto.setNumero(model.getNumero());
		dto.setTipologia(model.getTipologia());
		dto.setDocumenti(fromListFileDocumentoModelToListFileDocumentoDTO(model.getDocumenti()));
		return dto;
	}
	
	/**
	 * 
	 * @param documento
	 * @param utente 
	 * @return
	 */
	private static TipoUtenteEnum getRuoloUtenteDocumento(DocumentoModel documento, String utente) {
		if(documento.getUtenteCreatore().equalsIgnoreCase(utente)) {
			return TipoUtenteEnum.MITTENTE;
		}
		String tipoPermesso = null;
		for(AclModel acl : documento.getAcl()) {
			if(acl.isActive() && acl.getUtente().equalsIgnoreCase(utente)) {
				tipoPermesso = acl.getTipo();
				break;
			}
		}
		if(StringUtils.isBlank(tipoPermesso)) {
			return null;
		}
		return TipoPermessoEnum.LETTURA.name().equalsIgnoreCase(tipoPermesso) ? TipoUtenteEnum.COLLABORATORE : TipoUtenteEnum.ASSEGNATARIO;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static StatoAssegnazioneEnum getStatoAssegnazione(DocumentoModel documento) {
		// se esistono assegnatari
		if(documento.getAssegnazione() != null && documento.getAssegnazione().getAssegnatari() != null 
				&& !documento.getAssegnazione().getAssegnatari().isEmpty()) {
			if(StatoDocumentoEnum.FIRMA_ATTESA.name().equalsIgnoreCase(documento.getStatoDocumento())) {
				return StatoAssegnazioneEnum.APERTA;
			}
			if(StatoDocumentoEnum.FIRMATO.name().equalsIgnoreCase(documento.getStatoDocumento())) {
				return StatoAssegnazioneEnum.CONCLUSA;
			}
			if(StatoDocumentoEnum.RIFIUTATO.name().equalsIgnoreCase(documento.getStatoDocumento())) {
				return StatoAssegnazioneEnum.BLOCCATA;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static List<MittenteDTO> getDestinatari(DocumentoModel documento) {
		List<MittenteDTO> destinatari = new ArrayList<>();
		if(documento.getDestinatari() == null || documento.getDestinatari().isEmpty()) {
			return destinatari;
		}
		for(Anagrafica destinatario : documento.getDestinatari()) {
			MittenteDTO destinDto = new MittenteDTO();
			destinDto.setCf(destinatario.getCf());
			destinDto.setCognome(destinatario.getCognome());
			destinDto.setNome(destinatario.getNome());
			destinDto.setEmail(destinatario.getEmail());
			destinatari.add(destinDto);
		}
		return destinatari;
	}

	/**
	 * 
	 * @param mittente
	 * @return
	 */
	private static MittenteDTO getMittente(Anagrafica mittente) {
		if(mittente == null) {
			return null;
		}
		MittenteDTO mitDto = new MittenteDTO();
		mitDto.setCf(mittente.getCf());
		mitDto.setCognome(mittente.getCognome());
		mitDto.setNome(mittente.getNome());
		mitDto.setEmail(mittente.getEmail());
		return mitDto;
	}

	/**
	 * 
	 * @param documento
	 * @param utente
	 * @return
	 */
	private static boolean checkCollaboratore(DocumentoModel documento, String utente) {
		for(AclModel acl : documento.getAcl()) {
			if(acl.getUtente().equalsIgnoreCase(utente) && TipoPermessoEnum.LETTURA.name().equalsIgnoreCase(acl.getTipo())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param documento
	 * @param utente
	 * @return
	 */
	private static StatoDocumentoEnum getStatoDocumento(DocumentoModel documento, String utente) {
		String statoDocACL = null;
		for(AclModel acl : documento.getAcl()) {
			if(acl.getUtente().equalsIgnoreCase(utente) && !StringUtils.isBlank(acl.getStatoDocumento())) {
				statoDocACL = acl.getStatoDocumento();
			}
		}
		if(StringUtils.isBlank(statoDocACL)) {
			return StatoDocumentoEnum.getByName(documento.getStatoDocumento());
		}
		return StatoDocumentoEnum.getByName(statoDocACL);
	}

	/**
	 * 
	 * @param documento
	 * @param utente 
	 * @param hasOpenAction 
	 * @return
	 */
	private static List<AzioneEnum> getAltreAzioniUtente(DocumentoModel documento, String utente, boolean hasOpenAction) {
		List<AzioneEnum> altreAzioniUtente = new ArrayList<>();
		if(documento.getUtenteCreatore().equalsIgnoreCase(utente)) {
			altreAzioniUtente.addAll(getAltreAzioniUtenteMittente(documento));
		}
		else if(hasOpenAction) {
			// non sono il mittente ed ho azioni richieste da svolgere
			altreAzioniUtente.add(AzioneEnum.RIFIUTO);
		}
		return altreAzioniUtente;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static List<AzioneEnum> getAltreAzioniUtenteMittente(DocumentoModel documento) {
		List<AzioneEnum> altreAzioniUtente = new ArrayList<>();
		if(!StatoDocumentoEnum.ANNULLATO.name().equalsIgnoreCase(documento.getStatoDocumento()) &&
				!StatoDocumentoEnum.RIFIUTATO.name().equalsIgnoreCase(documento.getStatoDocumento())) {
			// se il documento è annullato, il mittente non può effettuare condivisioni
			altreAzioniUtente.add(AzioneEnum.CONDIVISIONE);
		}
		if(documento.getAssegnazione() == null || documento.getAssegnazione().getAssegnatari() == null ||
				documento.getAssegnazione().getAssegnatari().isEmpty()) {
			// se non ci sono assegnatari il mittente può effettuare l'invito firma
			altreAzioniUtente.add(AzioneEnum.INVITO_FIRMA);
		}
		// delete sempre concessa per il mittente (controllo ad FE ed a BE)
//		--altreAzioniUtente.add(AzioneEnum.DELETE);--
		if(documento.getFirmeLfuApplicate() == null || documento.getFirmeLfuApplicate().isEmpty() ||
				StatoDocumentoEnum.ANNULLATO.name().equalsIgnoreCase(documento.getStatoDocumento())) {
			// se non sono state apposte firme o se il documento è annullato, il mittente può rimuovere il documento
			altreAzioniUtente.add(AzioneEnum.DELETE);
		}
		if(!StatoDocumentoEnum.ANNULLATO.name().equalsIgnoreCase(documento.getStatoDocumento())) {
			altreAzioniUtente.add(AzioneEnum.ANNULLAMENTO);
		}
		return altreAzioniUtente;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static List<String> getCollaboratori(DocumentoModel documento) {
		List<String> collaboratori = new ArrayList<>();
		for(AclModel acl : documento.getAcl()) {
			if(acl.getUtente().equalsIgnoreCase(documento.getUtenteCreatore()) || !acl.isActive() ||
					collaboratori.contains(acl.getUtente())) {
				continue;
			}
			if(acl.getTipo().equalsIgnoreCase(TipoPermessoEnum.LETTURA.name())) {
				collaboratori.add(acl.getUtente());
			}
		}
		return collaboratori;
	}
	
	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static List<String> getAssegnatari(DocumentoModel documento) {
		List<String> assegnatari = new ArrayList<>();
		if(documento.getAssegnazione() == null) {
			return assegnatari;
		}
		for(AssegnatarioModel assegnatario : documento.getAssegnazione().getAssegnatari()) {
			if(assegnatario.isActive()) {
				assegnatari.add(assegnatario.getUtente());
			}
		}
		return assegnatari;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 
	private static List<String> getAssegnatari(DocumentoModel documento) {
		List<String> assegnatari = new ArrayList<>();
		for(OperazioneModel op : documento.getOperazioni()) {
			if(op.getUtente().equalsIgnoreCase(documento.getUtenteCreatore()) || !op.isActive() ||
					assegnatari.contains(op.getUtente())) {
				continue;
			}
			if(op.getTipo().equalsIgnoreCase(TipoOperazioneEnum.FIRMA.name()) ||
					op.getTipo().equalsIgnoreCase(TipoOperazioneEnum.SIGLA.name()) ||
					op.getTipo().equalsIgnoreCase(TipoOperazioneEnum.VISTA.name())) {
				assegnatari.add(op.getUtente());
			}
		}
		return assegnatari;
	}
	*/

	/**
	 * 
	 * @param documentiModel
	 * @return
	 */
	private static List<FileDocumentoDTO> fromListFileDocumentoModelToListFileDocumentoDTO(List<FileDocumentoModel> documentiModel){
		List<FileDocumentoDTO> documentiDTO = new ArrayList<>();
		if (documentiModel == null || documentiModel.isEmpty()) {
			return documentiDTO;
		}
		for (FileDocumentoModel model : documentiModel) {
			documentiDTO.add(fromFileDocumentoModelToFileDocumentoDTO(model));
		}
		return documentiDTO;
	}
	
	private static FileDocumentoDTO fromFileDocumentoModelToFileDocumentoDTO(FileDocumentoModel model) {
		FileDocumentoDTO dto = new FileDocumentoDTO();
		dto.setTipoFile(TipoFileEnum.getByName(model.getTipoFileFascicolo()));
		if (model.getVersioni() == null || model.getVersioni().isEmpty()) {
			dto.setContentType(model.getContentType());
			dto.setDaFirmare(model.isDaFirmare());
			dto.setIdEcm(model.getIdEcm());
			dto.setNomeFile(model.getNomeFile());
			dto.setTipo(model.getTipo());
			return dto;
		}
		Comparator<VersioneModel> comparator = (o1,o2) -> o1.getDataCreazione().compareTo(o2.getDataCreazione());	
		Optional<VersioneModel> lastVersionOpt = model.getVersioni().stream().max(comparator);
		if (lastVersionOpt.isPresent()) {
			VersioneModel lastVersion = lastVersionOpt.get();
			dto.setContentType(lastVersion.getContentType());
			dto.setDaFirmare(model.isDaFirmare());
			dto.setIdEcm(lastVersion.getIdEcm());
			dto.setNomeFile(lastVersion.getNomeFile());
			dto.setTipo(lastVersion.getTipo());
		}
		return dto;
		
	}
	
	/**
	 * 
	 * @param lista di docModel
	 * @return
	 */
	public static List<DatiDocumentoDTO> fromListDocumentoModelToListDatiDocumentoDTO(List<DocumentoModel> documenti, String utente) {
		List<DatiDocumentoDTO> documentiDTO = new ArrayList<>();
		if (documenti == null || documenti.isEmpty()) {
			return documentiDTO;
		}
		return documenti.stream().map(dto -> fromDocumentoModelToDatiDocumentoDTO(dto, utente)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param operazioneModel
	 * @return
	 */
	public static StoricoOperazioniDTO fromOperazioneModelToStoricoOperazioniDTO(OperazioneModel model) {
		StoricoOperazioniDTO dto = new StoricoOperazioniDTO();
		if (model != null) {
			dto.setOperazione(OperazioneEnum.getByName(model.getTipo()));
			dto.setDataOperazione(model.getDataCompletamento());
			dto.setCf(model.getUtente());
			dto.setRicevente(getRicevente(model));
			dto.setStatoOperazione(StatoOperazioneEnum.getByName(model.getStato()));
			dto.setMotivo(model.getMotivo());
			return dto;
		}
		return dto;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	private static MittenteDTO getRicevente(OperazioneModel model) {
		if(StringUtils.isBlank(model.getUtenteRicevente())) {
			return null;
		}
		MittenteDTO ricevente = new MittenteDTO();
		ricevente.setCf(model.getUtenteRicevente());
		return ricevente;
	}

	/**
	 * 
	 * @param mittente
	 * @return
	 */
	public static Anagrafica fromMittenteDtoToAnagrafica(MittenteDTO mittente) {
		Anagrafica anagrafica = new Anagrafica();
		anagrafica.setCf(mittente.getCf());
		anagrafica.setCognome(mittente.getCognome());
		anagrafica.setEmail(mittente.getEmail());
		anagrafica.setNome(mittente.getNome());
		
		return anagrafica;
	}
	
	
	public static MittenteDTO fromAnagraficaToMittenteDto(Anagrafica anagrafica) {
		MittenteDTO mittenteDto = new MittenteDTO();
		mittenteDto.setCf(anagrafica.getCf());
		mittenteDto.setCognome(anagrafica.getCognome());
		mittenteDto.setEmail(anagrafica.getEmail());
		mittenteDto.setNome(anagrafica.getNome());
		
		return mittenteDto;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	public static DocumentoFedDTO fromDocumentoModelToDocumentoFedDto(DocumentoModel documento) {
		DocumentoFedDTO dto = new DocumentoFedDTO();
		
		dto.setAppProduttore(documento.getAppProduttore());
		AclModel aclModel = documento.getAcl().stream().filter(acl -> !acl.getTipo().equalsIgnoreCase("CREATORE")).findFirst().orElse(null);
		if (aclModel != null) {
			if (aclModel.getTipo().equalsIgnoreCase("FIRMA"))
				dto.setUtenteFirmatario(aclModel.getUtente());

			dto.setAzioneDaEffettuare(AzioneEnum.getByName(aclModel.getTipo()));
		}
		
		dto.setDataScadenza(documento.getDataScadenza());
		dto.setIdDocumento(documento.getId());
		dto.setMittente(fromAnagraficaToMittenteDto(documento.getMittente()));
		dto.setNomeDocumento(documento.getNomeDocumento());
		dto.setStatoDocumento(StatoDocumentoEnum.getByName(documento.getStatoDocumento()));
		dto.setUrgente(documento.isUrgente());
		dto.setUtenteCreatore(documento.getUtenteCreatore());	
		dto.setTipoFirmaDaApplicare(FirmaEnum.getByName(documento.getTipoFirmaDaApplicare()));
//		--documento.getOperazioni().stream()--
//								 --.filter(operazione -> operazione.getTipo().equalsIgnoreCase("FIRMA"))--
//								 --.findFirst()--
//								 --.ifPresent(firma -> dto.setTipoFirmaDaApplicare(FirmaEnum.getByName(firma.getTipoFirmaApplicata())));--
		OperazioneModel op = null;
		for(OperazioneModel operazione : documento.getOperazioni()) {
			if(TipoOperazioneEnum.FIRMA.name().equalsIgnoreCase(operazione.getTipo()) ||
					TipoOperazioneEnum.VISTA.name().equalsIgnoreCase(operazione.getTipo()) ||
					TipoOperazioneEnum.SIGLA.name().equalsIgnoreCase(operazione.getTipo())) {
				op = operazione;
			}
		}
		if (op != null && (StatoOperazioneEnum.RIFIUTATA.name().equalsIgnoreCase(op.getStato()) ||
				StatoOperazioneEnum.COMPLETATA.name().equalsIgnoreCase(op.getStato()))) {
			dto.setDataOperazione(op.getDataCompletamento());
			dto.setStatoOperazione(StatoOperazioneEnum.getByName(op.getStato()));
		}
		
		
		List<FileFedDTO> files;
		files = getFilesFed(documento.getDocumenti());
		dto.setListaFile(files);
		dto.setIdRichiesta(documento.getIdRichiesta());
		documento.getOperazioni().stream()
								 .filter(operazione -> operazione.getTipo().equalsIgnoreCase("FIRMA"))
								 .findFirst()
								 .ifPresent(firma -> dto.setUtenteFirmatario(firma.getUtente()));	
		dto.setDatiProcedimento(fromDatiProcedimentoModelToDatiProcedimentoFedDto(documento.getDatiProcedimento()));
		return dto;
	}
	
	

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static DatiProcedimentoModel fromDatiProcedimentoDtoToDatiProcedimentoModel(DatiProcedimento dto) {
		DatiProcedimentoModel model = new DatiProcedimentoModel();
		model.setId(dto.getId());
		model.setNomeProcedimento(dto.getNomeProcedimento());
		model.setNumero(dto.getNumero());
		model.setTipologia(dto.getTipologia());
		return model;
	}
	
	/**
	 * 
	 * @param documenti
	 * @return
	 */
	private static List<FileFedDTO> getFilesFed(List<FileDocumentoModel> documenti) {
		List<FileFedDTO> listaFile = new ArrayList<>();
		for(FileDocumentoModel documento : documenti) {
			FileFedDTO file = getFileFed(documento);
			listaFile.add(file);
		}
		return listaFile;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static FileFedDTO getFileFed(FileDocumentoModel documento) {
		FileFedDTO file = new FileFedDTO();
		file.setIdFileFed(documento.getIdFileFed());
		file.setTipoFileFascicolo(TipoFileEnum.getByName(documento.getTipoFileFascicolo()));
		VersioneModel ultimaVersione = getUltimaVersione(documento);
		file.setDaFirmare(true);
		file.setContentType(ultimaVersione != null ? ultimaVersione.getContentType() : documento.getContentType());
		file.setIdEcm(ultimaVersione != null ? ultimaVersione.getIdEcm() : documento.getIdEcm());
		file.setNomeFile(ultimaVersione != null ? ultimaVersione.getNomeFile() : documento.getNomeFile());
		return file;
	}

	/**
	 * 
	 * @param documento
	 * @return
	 */
	private static VersioneModel getUltimaVersione(FileDocumentoModel documento) {
		if(documento.getVersioni() != null && !documento.getVersioni().isEmpty()) {
			return documento.getVersioni().get(documento.getVersioni().size() - 1);
		}
		return null;
	}
	
	/**
	 * 
	 * @param firmeLfuApplicate
	 * @return
	 */
	public static List<FirmaLFUDto> fromFirmaLFUModelListToFirmaLFUDtoList(List<FirmaLFUModel> firmeLfuApplicate) {
		List<FirmaLFUDto> response = new ArrayList<>();
		if (firmeLfuApplicate != null && !firmeLfuApplicate.isEmpty()) {
			for(FirmaLFUModel model : firmeLfuApplicate) {
				response.add(fromFirmaLFUModeltoFirmaLFUDto(model));
			}
		}

		return response;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	private static FirmaLFUDto fromFirmaLFUModeltoFirmaLFUDto(FirmaLFUModel model) {
		FirmaLFUDto dto = new FirmaLFUDto();
		dto.setDataFirma(model.getDataFirma());
		dto.setModalitaFirma(model.getModalitaFirma());
		dto.setTipoFirma(model.getTipoFirma());
		dto.setUtente(model.getUtente());
		
		return dto;
	}
	/**
	 * 
	 * @param listModel
	 * @return
	 */
	public static List<FileDocumentoDTO> fromListFileDocumentoModelToListFileDocumentoDTOforStaging(List<FileDocumentoModel> listModel) {
		List<FileDocumentoDTO> toReturn = new ArrayList<>();
		for (FileDocumentoModel fileDocumentoModel : listModel) {
			FileDocumentoDTO fileDocumentoDto = fromFileDocumentoModelToFileDocumentoDTOforStaging(fileDocumentoModel);
			toReturn.add(fileDocumentoDto);
		}
		return toReturn;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public static FileDocumentoDTO fromFileDocumentoModelToFileDocumentoDTOforStaging(FileDocumentoModel model) {
		FileDocumentoDTO dto = new FileDocumentoDTO();
		VersioneModel versioneModel = model.getVersioni().get(model.getVersioni().size() - 1);

		String idEcmOriginale;

		if (model.getVersioni().size() > 1) {
			idEcmOriginale = model.getVersioni().get(model.getVersioni().size() - 2).getIdEcm();
		}
		else {
			idEcmOriginale = model.getIdEcm();
		}

		dto.setContentType(versioneModel.getContentType());
		dto.setIdEcm(versioneModel.getIdEcm());
		dto.setNomeFile(versioneModel.getNomeFile());
		dto.setTipoFile(TipoFileEnum.getByName(model.getTipoFileFascicolo()));
		dto.setIdEcmOriginale(idEcmOriginale);

		return dto;

	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static VersioneModel fromFileDocumentoDTOToVersioneModel(FileDocumentoDTO file) {
		VersioneModel versione = new VersioneModel();		
		versione.setIdEcm(file.getIdEcm());
		versione.setNomeFile(file.getNomeFile());
		versione.setContentType(file.getContentType());
		versione.setDataCreazione(new Date());
		return versione;
	}

	/**
	 * 
	 * @param fascicolo
	 * @return
	 */
	public static FascicoloDTO fromFascicoloModelToFascicoloDto(FascicoloModel model) {
		FascicoloDTO dto = new FascicoloDTO();
		dto.setIdFascicolo(model.getIdFascicolo());
		dto.setNomeFascicolo(model.getNomeFascicolo());
		if (model.getAltriDocumenti() != null && !model.getAltriDocumenti().isEmpty())
			dto.setDocumenti(fromListFileDocumentoModelToListFileDocumentoDTO(model.getAltriDocumenti()));
		if(model.getProcedimenti() != null && !model.getProcedimenti().isEmpty())
			dto.setProcedimenti(fromListDatiProcedimentoModelToListDatiProcedimentoDto(model.getProcedimenti()));
		
		return dto;
	}
	
	/**
	 * 
	 * @param listaModel
	 * @return
	 */
	public static List<DatiProcedimento> fromListDatiProcedimentoModelToListDatiProcedimentoDto(List<DatiProcedimentoModel> listaModel){
		List<DatiProcedimento> listaDto = new ArrayList<>();
		for (DatiProcedimentoModel model : listaModel) {
			listaDto.add(fromDatiProcedimentoModelToDatiProcedimentoDto(model));
		}
		return listaDto;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	public static DatiProcedimentoModel fromDatiProcedimentoFedDtoToDatiProcedimentoModel(DatiProcedimentoFedDTO dto) {
		if(dto == null) {
			return null;
		}
		DatiProcedimentoModel model= new DatiProcedimentoModel();
		model.setId(dto.getId());
		model.setNomeProcedimento(dto.getNomeProcedimento());
		model.setNumero(dto.getNumero());
		model.setTipologia(dto.getTipologia());
		model.setDocumenti(fromListFileFedDtoToListFileDocumentoModel(dto.getListaFileProcedimento()));
		return model;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	private static DatiProcedimentoFedDTO fromDatiProcedimentoModelToDatiProcedimentoFedDto(DatiProcedimentoModel model) {
		if(model == null) {
			return null;
		}
		DatiProcedimentoFedDTO dto= new DatiProcedimentoFedDTO();
		dto.setId(model.getId());
		dto.setNomeProcedimento(model.getNomeProcedimento());
		dto.setNumero(model.getNumero());
		dto.setTipologia(model.getTipologia());
		return dto;
	}
	
   /**
    * 
    * @param dto
    * @return
    */
	public static FascicoloModel fromFascicoloFedDtoToFascicoloModel(FascicoloFedDTO dto) {
		if(dto == null) {
			return null;
		}
		FascicoloModel model= new FascicoloModel();
		model.setAltriDocumenti(fromListFileFedDtoToListFileDocumentoModel(dto.getFileAltriDocumenti()));
		model.setIdFascicolo(dto.getIdFascicolo());
		model.setNomeFascicolo(dto.getNomeFascicolo());
		model.setProcedimenti(fromListDatiProcedimentoFedDTOToListDatiProcedimentoModel(dto.getDatiProcedimento()));
		return model;
	}
    
	/**
	 * 
	 * @param datiProcedimento
	 * @return
	 */
	private static List<DatiProcedimentoModel> fromListDatiProcedimentoFedDTOToListDatiProcedimentoModel(List<DatiProcedimentoFedDTO> datiProcedimento) {
		List<DatiProcedimentoModel> listModel = new ArrayList<>();
		if(datiProcedimento == null || datiProcedimento.isEmpty()) {
			return listModel;
		}
		for (DatiProcedimentoFedDTO dto : datiProcedimento) {
			DatiProcedimentoModel model= new DatiProcedimentoModel();
			model.setId(dto.getId());
			model.setNomeProcedimento(dto.getNomeProcedimento());
			model.setTipologia(dto.getTipologia());
			model.setDocumenti(fromListFileFedDtoToListFileDocumentoModel(dto.getListaFileProcedimento()));
			listModel.add(model);
		}
		return listModel;
	}
	
	/**
	 * 
	 * @param fileAltriDocumenti
	 * @return
	 */
	private static List<FileDocumentoModel> fromListFileFedDtoToListFileDocumentoModel(List<FileFedDTO> fileAltriDocumenti) {
		List<FileDocumentoModel> listModel = new ArrayList<>();
		if (fileAltriDocumenti != null && !fileAltriDocumenti.isEmpty())
			for (FileFedDTO dto : fileAltriDocumenti) {
				FileDocumentoModel model = new FileDocumentoModel();
				model.setContentType(dto.getContentType());
				model.setDaFirmare(dto.isDaFirmare());
				model.setIdEcm(dto.getIdEcm());
				model.setIdFileFed(dto.getIdFileFed());
				model.setNomeFile(dto.getNomeFile());
				model.setTipo("ORIGINALE");
				model.setTipoFileFascicolo(dto.getTipoFileFascicolo().name());
			   listModel.add(model);
			}
		return listModel;
	}
	
}
