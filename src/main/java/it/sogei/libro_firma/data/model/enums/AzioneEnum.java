package it.sogei.libro_firma.data.model.enums;

public enum AzioneEnum {

	VIEW,
	DELETE,
	FIRMA,
	VISTA,
	SIGLA,
	RIFIUTO,
	CONDIVISIONE,
	INVITO_FIRMA,
	PRESA_VISIONE,
	ANNULLAMENTO;
	
	private AzioneEnum() {
		//
	}
	
	public static AzioneEnum getByName(String name) {
		for(AzioneEnum en : AzioneEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
}
