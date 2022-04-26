package it.sogei.libro_firma.data.model.enums;

public enum CampoOrdinamentoEnum {
	
	NOME_DOCUMENTO("nomeDocumento"),
	TIPO_DOCUMENTO("tipoDocumento"),
	STATO_DOCUMENTO("statoDocumento"),
	MITTENTE("utenteCreatore"),
	DATA_CREAZIONE("dataCreazione"),
	URGENTE("urgente"),
	DATA_SCADENZA("dataScadenza"),
	DATA_INVIO("dataInvio"),
	APP_PRODUTTORE("appProduttore"),
	NUMERO_ALLEGATI("documenti"),
	ASSEGNATARI("assegnatari"),
	DESTINATARI("destinatari");

	private String nomeCampo;
	
	private CampoOrdinamentoEnum(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	/**
	 * 
	 * @param campo
	 * @return
	 */
	public static CampoOrdinamentoEnum getByNomeCampo(String campo) {
		for(CampoOrdinamentoEnum en : CampoOrdinamentoEnum.values()) {
			if(en.getNomeCampo().equalsIgnoreCase(campo)) {
				return en;
			}
		}
		return null;
	}
	
}

