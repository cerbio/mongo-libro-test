package it.sogei.libro_firma.data.model.enums;

public enum TipoOperazioneEnum {

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
	
	private TipoOperazioneEnum() {
		//
	}
	
	public static TipoOperazioneEnum getByName(String name) {
		for(TipoOperazioneEnum en : TipoOperazioneEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
}
