package it.sogei.libro_firma.data.model.enums;

public enum StatoDocumentoEnum {
	
	DA_FIRMARE,
	FIRMATO,
	RIMOSSO,
	CONDIVISO,
	FIRMA_ATTESA,
	ANNULLATO,
	RIFIUTATO;
	
	private StatoDocumentoEnum() {
		//
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static StatoDocumentoEnum getByName(String name) {
		for(StatoDocumentoEnum en : StatoDocumentoEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}

}
