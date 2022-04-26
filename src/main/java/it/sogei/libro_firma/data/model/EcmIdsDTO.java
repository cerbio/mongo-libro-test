package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

public class EcmIdsDTO implements Serializable{

	private static final long serialVersionUID = 1767924693168137203L;
	
	private String idEcmOriginale;
	private List<String> idEcmVersioni;
	
	public EcmIdsDTO() {
		super();
	}

	public String getIdEcmOriginale() {
		return idEcmOriginale;
	}

	public void setIdEcmOriginale(String idEcmOriginale) {
		this.idEcmOriginale = idEcmOriginale;
	}

	public List<String> getIdEcmVersioni() {
		return idEcmVersioni;
	}

	public void setIdEcmVersioni(List<String> idEcmVersioni) {
		this.idEcmVersioni = idEcmVersioni;
	}
}
