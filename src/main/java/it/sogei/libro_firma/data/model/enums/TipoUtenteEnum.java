package it.sogei.libro_firma.data.model.enums;

public enum TipoUtenteEnum {
	
	MITTENTE,
	ASSEGNATARIO,
	COLLABORATORE;

	private TipoUtenteEnum() {
		//
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static TipoUtenteEnum getByName(String name) {
		for(TipoUtenteEnum en : TipoUtenteEnum.values()) {
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
	
}
