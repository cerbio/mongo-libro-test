package it.sogei.libro_firma.data.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DataConfig")
public class DataConfigModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String _id;
	private String valore;
	
	public DataConfigModel() {
		super();
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	
}
