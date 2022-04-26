package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Gruppo")
public class GruppoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String _id;
	private String tipoGruppo;
	private List<String> valori;
	private boolean valoreBooleano = false;
	private String idGruppo;
	private String label;
	
	public GruppoModel() {
		super();
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTipoGruppo() {
		return tipoGruppo;
	}

	public void setTipoGruppo(String tipoGruppo) {
		this.tipoGruppo = tipoGruppo;
	}

	public List<String> getValori() {
		return valori;
	}

	public void setValori(List<String> valori) {
		this.valori = valori;
	}

	public String getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isValoreBooleano() {
		return valoreBooleano;
	}

	public void setValoreBooleano(boolean valoreBooleano) {
		this.valoreBooleano = valoreBooleano;
	}
	
}
