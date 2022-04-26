package it.sogei.libro_firma.data.model.enums;

public enum OperazioneEnum {
	
	CREAZIONE,
	FIRMA,
	SIGLA,
	VISTA,
	CONDIVISIONE,
	INVITO_FIRMA,
	RIFIUTO,
	ANNULLAMENTO_INVITO_FIRMA,
	ANNULLAMENTO_CONDIVISIONE,
	ANNULLAMENTO;
	
	private OperazioneEnum() {
		//
	}
	
	public static OperazioneEnum getByName(String name) {
		for(OperazioneEnum en : OperazioneEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
}
