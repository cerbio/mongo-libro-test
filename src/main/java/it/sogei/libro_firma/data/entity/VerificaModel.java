package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Verifica")
public class VerificaModel implements Serializable {

	private static final long serialVersionUID = 8326629849463731005L;

	@Id
	private String id;
	private boolean verificaValida = false;
	private List<EsitoVerifica> esitiVerifica;
	
	public VerificaModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isVerificaValida() {
		return verificaValida;
	}

	public void setVerificaValida(boolean verificaFirma) {
		this.verificaValida = verificaFirma;
	}

	public List<EsitoVerifica> getEsitiVerifica() {
		return esitiVerifica;
	}

	public void setEsitiVerifica(List<EsitoVerifica> esitiVerifica) {
		this.esitiVerifica = esitiVerifica;
	}
}
