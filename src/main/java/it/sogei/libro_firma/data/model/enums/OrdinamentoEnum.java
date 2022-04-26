package it.sogei.libro_firma.data.model.enums;

public enum OrdinamentoEnum {

	ULTIMI_ARRIVATI("dataCreazione-DESC"),
	PRIORITARI("scaduto-ASC", "scadenza-DESC", "dataScadenza-ASC", "urgente-DESC"),
	FILENAME_A_Z("nomeDocumento-ASC"),
	FILENAME_Z_A("nomeDocumento-DESC");
	
	private String[] campiDaOrdinare;
		
	private OrdinamentoEnum(String... campiDaOrdinare) {
		this.campiDaOrdinare = campiDaOrdinare;
	}

	public String[] getCampiDaOrdinare() {
		return campiDaOrdinare;
	}
	
}
