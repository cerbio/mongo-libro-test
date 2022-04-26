package it.sogei.libro_firma.data.model.enums;

public enum TipoFirmaEnum {

	REMOTA("Firma remota"),
	SMARTCARD("Firma locale");

	private String descrizione;
	
	private TipoFirmaEnum(String descrizione) {
		this.descrizione = descrizione;
	}
	
	/**
	 * 
	 * @param tipoFirma
	 * @return
	 */
	public static TipoFirmaEnum getByName(String tipoFirma) {
		for(TipoFirmaEnum en : TipoFirmaEnum.values()) {
			if(en.name().equalsIgnoreCase(tipoFirma)) {
				return en;
			}
		}
		return null;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
}
