package it.sogei.libro_firma.data.util;

import java.util.ArrayList;
import java.util.List;

import it.sogei.libro_firma.data.entity.EsitoVerifica;
import it.sogei.libro_firma.data.entity.FileVerificaModel;
import it.sogei.libro_firma.data.entity.FirmatarioVerifica;
import it.sogei.libro_firma.data.entity.VerificaModel;
import it.sogei.libro_firma.data.model.EsitoVerificaDTO;
import it.sogei.libro_firma.data.model.FileVerificaDTO;
import it.sogei.libro_firma.data.model.FirmatarioVerificaDTO;
import it.sogei.libro_firma.data.model.VerificaDTO;
import it.sogei.libro_firma.data.model.enums.TipoFileEnum;

public class VerificaMapper {

	private VerificaMapper() {
		super();
	}

	/**
	 * 
	 * @param dto
	 * @param username 
	 * @return
	 */
	public static VerificaModel fromVerificaDtoToVerificaModel(VerificaDTO dto, String username) {
		VerificaModel model = new VerificaModel();
		if(model.getEsitiVerifica()==null || model.getEsitiVerifica().isEmpty()) {
			model.setEsitiVerifica(new ArrayList<>());
		}
		model.getEsitiVerifica().add(fromEsitoVerificaDtoToEsitoVerifica(dto.getEsito(), username));
		model.setId(dto.getIdDocumento());
		model.setVerificaValida(dto.isVerificaValida());
		return model;
	}

	/**
	 * 
	 * @param username 
	 * @param esitiVerifica
	 * @return
	 */
//	private static List<EsitoVerifica> fromEsitiVerificaDtoListToEsitiVerificaList(	EsitoVerificaDTO esitiVerificaDTO, String username) {
//		List<EsitoVerifica> esitiVerifica = new ArrayList<>();
//		for(EsitoVerificaDTO esito : esitiVerificaDTO) {
//			esitiVerifica.add(fromEsitoVerificaDtoToEsitoVerifica(esito, username));
//		}
//		
//		return esitiVerifica;
//	}

	/**
	 * 
	 * @param username 
	 * @param esito
	 * @return
	 */
	private static EsitoVerifica fromEsitoVerificaDtoToEsitoVerifica(EsitoVerificaDTO esitoDTO, String username) {
		EsitoVerifica esito = new EsitoVerifica();
		esito.setDataVerifica(esitoDTO.getDataVerifica());
		esito.setEsito(esitoDTO.isEsito());
		esito.setUtenteCreatore(username);

		esito.setListaFile(fromListaFileDtoToListaFileModel(esitoDTO.getListaFile()));
		return esito;
	}

	/**
	 * 
	 * @param listaFileDto
	 * @return
	 */
	private static List<FileVerificaModel> fromListaFileDtoToListaFileModel(List<FileVerificaDTO> listaFileDto) {
		List<FileVerificaModel> listaFileModel = new ArrayList<>();

		for (FileVerificaDTO fileVerificaDTO : listaFileDto) {
			listaFileModel.add(fromFileVerificaDtoToFileVerificaModel(fileVerificaDTO));

		}
		return listaFileModel;

	}

	/**
	 * 
	 * @param fileVerificaDto
	 * @return
	 */
	private static FileVerificaModel fromFileVerificaDtoToFileVerificaModel(FileVerificaDTO fileVerificaDto) {
		FileVerificaModel fileVerificaModel = new FileVerificaModel();
		fileVerificaModel.setEsito(fileVerificaDto.isEsito());
		fileVerificaModel
				.setFirmatari(fromFirmatariVerificaDtoListToFirmatariVerificaList(fileVerificaDto.getFirmatari()));
		fileVerificaModel.setIdEcm(fileVerificaDto.getIdEcm());
		fileVerificaModel.setIdEcmOriginale(fileVerificaDto.getIdEcmOriginale());
		fileVerificaModel.setTipologiaFile(fileVerificaDto.getTipologiaFile().name());
		return fileVerificaModel;
	}

	/**
	 * 
	 * @param firmatari
	 * @return
	 */
	private static List<FirmatarioVerifica> fromFirmatariVerificaDtoListToFirmatariVerificaList(List<FirmatarioVerificaDTO> firmatariDTO) {
		List<FirmatarioVerifica> firmatari = new ArrayList<>();
		if(firmatariDTO == null || firmatariDTO.isEmpty()) {
			return firmatari;
		}
		for(FirmatarioVerificaDTO firmatario : firmatariDTO) {
			firmatari.add(fromFirmatariVerificaDtoToFirmatariVerifica(firmatario));
		}
		return firmatari;
	}

	/**
	 * 
	 * @param firmatario
	 * @return
	 */
	private static FirmatarioVerifica fromFirmatariVerificaDtoToFirmatariVerifica(FirmatarioVerificaDTO firmatarioDTO) {
		FirmatarioVerifica firmatario = new FirmatarioVerifica();
		firmatario.setCf(firmatarioDTO.getCodiceFiscale());
		firmatario.setCognome(firmatarioDTO.getCognome());
		firmatario.setDataFineValiditaCertificato(firmatarioDTO.getDataFineValiditaCertificato());
		firmatario.setDataFirma(firmatarioDTO.getDataFirma());
		firmatario.setDataInizioValiditaCertificato(firmatarioDTO.getDataInizioValiditaCertificato());
		firmatario.setMotivo(firmatarioDTO.getMotivo());
		firmatario.setNome(firmatarioDTO.getNome());
		firmatario.setStatoDelCertificato(firmatarioDTO.getStatoDelCertificato());
		firmatario.setTipoFirma(firmatarioDTO.getTipologiaFirma());
		String firmaValida = "";
		if (firmatarioDTO.isFirmaValida() == null) {
			firmaValida = "WARNING";
		} else if (Boolean.TRUE.equals(firmatarioDTO.isFirmaValida())) {
			firmaValida = "OK";
		} else
			firmaValida = "KO";
		firmatario.setFirmaValida(firmaValida);
		firmatario.setAlgoritmoDigest(firmatarioDTO.getAlgoritmoDigest());
		firmatario.setEnteCertificatore(firmatarioDTO.getEnteCertificatore());
		
		return firmatario;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static VerificaDTO fromVerificaModelToVerificaDto(VerificaModel model) {
		VerificaDTO dto = new VerificaDTO();
		dto.setEsito(fromEsitoVerificaModelToEsitoDto(model.getEsitiVerifica().get(model.getEsitiVerifica().size()-1)));
		dto.setIdDocumento(model.getId());
		dto.setVerificaValida(model.isVerificaValida());
		return dto;
	}

	/**
	 * 
	 * @param esitiVerifica
	 * @return
	 */
//	private static List<EsitoVerificaDTO> fromEsitiVerificaModelListToEsitiVerificaDtoList(List<EsitoVerifica> esitiVerifica) {
//		List<EsitoVerificaDTO> esitiVerificaDTO = new ArrayList<>();
//		if(esitiVerifica == null || esitiVerifica.isEmpty()) {
//			return esitiVerificaDTO;
//		}
//		// restituisco solo l'ultimo esito
//		esitiVerificaDTO.add(fromEsitoVerificaModelToEsitoDto(esitiVerifica.get(esitiVerifica.size() - 1)));
//		return esitiVerificaDTO;
//	}

	/**
	 * 
	 * @param esito
	 * @return
	 */
	private static EsitoVerificaDTO fromEsitoVerificaModelToEsitoDto(EsitoVerifica esito) {
		EsitoVerificaDTO esitoDto = new EsitoVerificaDTO();
		esitoDto.setDataVerifica(esito.getDataVerifica());
		esitoDto.setEsito(esito.getEsito());
		esitoDto.setListaFile(fromListaFileModelToListaFileDto(esito.getListaFile()));
		esitoDto.setUtenteCreatore(esito.getUtenteCreatore());
		return esitoDto;
	}

	/**
	 * 
	 * @param listaFile
	 * @return
	 */
	private static List<FileVerificaDTO> fromListaFileModelToListaFileDto(List<FileVerificaModel> listaFile) {
		List<FileVerificaDTO> listFileVerificaDto = new ArrayList<>();
		for (FileVerificaModel fileVerificaModel : listaFile) {
			listFileVerificaDto.add(fromFileVerificaModelToFileVerificaDto(fileVerificaModel));
			
		}
		return listFileVerificaDto;
	}
 /**
  * 
  * @param fileVerificaModel
  * @return
  */
	private static FileVerificaDTO fromFileVerificaModelToFileVerificaDto(FileVerificaModel fileVerificaModel) {
		FileVerificaDTO fileVerificaDTO= new FileVerificaDTO();
		fileVerificaDTO.setEsito(fileVerificaModel.isEsito());
		fileVerificaDTO.setIdEcm(fileVerificaModel.getIdEcm());
		fileVerificaDTO.setIdEcmOriginale(fileVerificaModel.getIdEcmOriginale());
		fileVerificaDTO.setTipologiaFile(TipoFileEnum.getByName(fileVerificaModel.getTipologiaFile()));
		fileVerificaDTO.setFirmatari(fromFirmatariVerificaModelListToFirmatariVerificaDtoList(fileVerificaModel.getFirmatari()));
		return fileVerificaDTO;
	}

	
	/**
	 * 
	 * @param firmatari
	 * @return
	 */
	private static List<FirmatarioVerificaDTO> fromFirmatariVerificaModelListToFirmatariVerificaDtoList(List<FirmatarioVerifica> firmatari) {
		List<FirmatarioVerificaDTO> firmatariDto = new ArrayList<>();
		if(firmatari == null || firmatari.isEmpty()) {
			return firmatariDto;
		}
		for(FirmatarioVerifica firmatario : firmatari) {
			firmatariDto.add(fromFirmatariVerificaModelToFirmatariVerificaModel(firmatario));
		}
		return firmatariDto;
	}

	/**
	 * 
	 * @param firmatario
	 * @return
	 */
	private static FirmatarioVerificaDTO fromFirmatariVerificaModelToFirmatariVerificaModel(FirmatarioVerifica firmatario) {
		FirmatarioVerificaDTO firmatarioDto = new FirmatarioVerificaDTO();
		firmatarioDto.setCodiceFiscale(firmatario.getCf());
		firmatarioDto.setCognome(firmatario.getCognome());
		firmatarioDto.setDataFineValiditaCertificato(firmatario.getDataFineValiditaCertificato());
		firmatarioDto.setDataFirma(firmatario.getDataFirma());
		firmatarioDto.setDataInizioValiditaCertificato(firmatario.getDataInizioValiditaCertificato());
		firmatarioDto.setMotivo(firmatario.getMotivo());
		firmatarioDto.setNome(firmatario.getNome());
		firmatarioDto.setStatoDelCertificato(firmatario.getStatoDelCertificato());
		firmatarioDto.setTipologiaFirma(firmatario.getTipoFirma());
		Boolean firmaValida = null;
		if (firmatario.isFirmaValida().equals("OK")) {
			firmaValida = true;
		} else if (firmatario.isFirmaValida().equals("KO"))
			firmaValida = false;
		firmatarioDto.setFirmaValida(firmaValida);
		firmatarioDto.setAlgoritmoDigest(firmatario.getAlgoritmoDigest());
		firmatarioDto.setEnteCertificatore(firmatario.getEnteCertificatore());
		
		return firmatarioDto;
	}
}
