package it.sogei.libro_firma.data.model.enums;

public enum FiltriEnum {
	
	SEARCH,
	TOWORK,
	AVANZATI_STATO,
	MOTIVO_RIFIUTO;
	
	private FiltriEnum() {}
	
	public static FiltriEnum getByName(String name) {
		for(FiltriEnum en : FiltriEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
}
