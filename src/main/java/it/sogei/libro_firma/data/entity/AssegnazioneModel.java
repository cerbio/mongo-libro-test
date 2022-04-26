package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.List;

public class AssegnazioneModel implements Serializable{

	private static final long serialVersionUID = -4404270981645848494L;
	
	private Boolean prioritario;
	private List<AssegnatarioModel> assegnatari;
	
	public AssegnazioneModel() {
		super();
	}

	public Boolean isPrioritario() {
		return prioritario;
	}

	public void setPrioritario(Boolean prioritario) {
		this.prioritario = prioritario;
	}

	public List<AssegnatarioModel> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<AssegnatarioModel> assegnatari) {
		this.assegnatari = assegnatari;
	}
}
