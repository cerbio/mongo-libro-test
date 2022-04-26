package it.sogei.libro_firma.data.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum FirmaEnum {
	
	PADES("application/pdf"),
	CADES("ALL"),
	AUTOGRAFA("AUTOGRAFA");
	
	private String contentType;
	
	private FirmaEnum(String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 * 
	 * @param contentType
	 * @return
	 */
	public static List<FirmaEnum> getByContentType(String contentType) {
		List<FirmaEnum> listaEnums = new ArrayList<>();
		for(FirmaEnum en : FirmaEnum.values()) {
			if("ALL".equalsIgnoreCase(en.getContentType()) ||
					en.getContentType().equalsIgnoreCase(contentType)) {
				listaEnums.add(en);
			}
		}
		return listaEnums;
	}
	
	/**
	 * 
	 * @param tipoFirma
	 * @return
	 */
	public static FirmaEnum getByName(String tipoFirma) {
		for(FirmaEnum en : FirmaEnum.values()) {
			if(en.name().equalsIgnoreCase(tipoFirma)) {
				return en;
			}
		}
		return null;
	}

	public String getContentType() {
		return contentType;
	}

}
