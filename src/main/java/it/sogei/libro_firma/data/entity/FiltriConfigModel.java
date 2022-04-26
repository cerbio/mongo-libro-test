package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FiltriConfig")
public class FiltriConfigModel implements Serializable {

	private static final long serialVersionUID = 5123727668132247612L;
	
	@Id
	private String _id;
	private List<FiltroAvanzato> filtriAvanzati;
	
	public FiltriConfigModel() {
		super();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public List<FiltroAvanzato> getFiltriAvanzati() {
		return filtriAvanzati;
	}

	public void setFiltriAvanzati(List<FiltroAvanzato> filtriAvanzati) {
		this.filtriAvanzati = filtriAvanzati;
	}
}
