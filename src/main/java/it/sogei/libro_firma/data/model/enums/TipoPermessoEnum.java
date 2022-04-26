package it.sogei.libro_firma.data.model.enums;

public enum TipoPermessoEnum {

	CREATORE,
	FIRMA,
	SIGLA,
	VISTA,
	LETTURA;
	
	private TipoPermessoEnum() {
		//
	}
	
	public static TipoPermessoEnum getByName(String name) {
		for(TipoPermessoEnum en : TipoPermessoEnum.values()) { 
			if(en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		return null;
	}
}
