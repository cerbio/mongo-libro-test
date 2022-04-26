package it.sogei.libro_firma.data.model.enums;

public enum StatoOperazioneEnum {

	APERTA,
	ANNULLATA,
	COMPLETATA,
	RIFIUTATA;
	
	private StatoOperazioneEnum() {
		//
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static StatoOperazioneEnum getByName(String name) {
		for(StatoOperazioneEnum en : StatoOperazioneEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
	
}
