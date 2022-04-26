package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class GestisciTag implements Serializable{

	private static final long serialVersionUID = 3080743285462834683L;
	
	@Schema(description = "Id del tag")
	private String id;
	@Schema(description = "Nome corrente del tag")
	private String name;
	@Schema(description = "Nome modificato del tag")
	private String nameNew;
	
	public GestisciTag() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameNew() {
		return nameNew;
	}

	public void setNameNew(String nameNew) {
		this.nameNew = nameNew;
	}
}
