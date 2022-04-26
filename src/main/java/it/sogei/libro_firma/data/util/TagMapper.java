package it.sogei.libro_firma.data.util;

import it.sogei.libro_firma.data.entity.TagModel;
import it.sogei.libro_firma.data.model.TagDTO;

public class TagMapper {

	private TagMapper() {
		super();
	}
	
	public static TagModel tagDtoToTagModel(TagDTO dto) {
		TagModel model = new TagModel();
		model.setDataCreazione(dto.getDataCreazione());
		model.setGruppi(dto.getGruppi());
		model.setNome(dto.getNome());
		model.setUtenteCreatore(dto.getUtenteCreatore());
		if (dto.getUltimaModifica() != null) {
			model.setUltimaModifica(dto.getUltimaModifica());
		}
		return model;
	}

}
