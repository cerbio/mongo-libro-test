package it.sogei.libro_firma.data.model.enums;

import org.apache.commons.lang3.StringUtils;

public enum TipoFileEnum {

	PRINCIPALE,
	ALLEGATO,
	ALLEGATO_F,
	ALLEGATO_N_F,
	SUPPORTO;
	
	private TipoFileEnum() {
		//
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static TipoFileEnum getByName (String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		for (TipoFileEnum en : TipoFileEnum.values()) {
			if(en.name().equals(name)) {
				return en;
			}
		}
		return null;
	}
}
