package it.sogei.libro_firma.data.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GruppoRicercaParamsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fieldToFilter;
	private List<String> valori = new ArrayList<>();
	private boolean flag;
	
	public GruppoRicercaParamsDTO() {
		super();
	}

	public List<String> getValori() {
		return valori;
	}

	public void setValori(List<String> valori) {
		this.valori = valori;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getFieldToFilter() {
		return fieldToFilter;
	}

	public void setFieldToFilter(String fieldToFilter) {
		this.fieldToFilter = fieldToFilter;
	}

}
