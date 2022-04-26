package it.sogei.libro_firma.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Tag")
public class TagModel implements Serializable {

	private static final long serialVersionUID = 7095458933114768484L;
	
	@Id
	private String id;
	private String nome;
	private List<String> gruppi;
	private String utenteCreatore;
	private Date dataCreazione;
	private Date ultimaModifica;
		
	public TagModel() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String _id) {
		this.id = _id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getUtenteCreatore() {
		return utenteCreatore;
	}
	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<String> getGruppi() {
		return gruppi;
	}

	public void setGruppi(List<String> gruppi) {
		this.gruppi = gruppi;
	}

	public Date getUltimaModifica() {
		return ultimaModifica;
	}

	public void setUltimaModifica(Date ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}
}
