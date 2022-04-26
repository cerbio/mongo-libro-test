package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.OperazioneEnum;
import it.sogei.libro_firma.data.model.enums.StatoOperazioneEnum;

public class StoricoOperazioniDTO implements Serializable, Comparable<StoricoOperazioniDTO> {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Operazione svolta")
	private OperazioneEnum operazione;
	
	@Schema(description = "Stato operazione svolta")
	private StatoOperazioneEnum statoOperazione;

	@Schema(description = "Data operazione")
	private Date dataOperazione;
	
	@Schema(description = "Motivo operazione")
	private String motivo;

	@Schema(description = "Codice fiscale")
	private String cf;
	
	@Schema(description = "Nome")
	private String nome;
	
	@Schema(description = "Cognome")
	private String cognome;
	
	@Schema(description = "Utente destinatario dell'operazione")
	private MittenteDTO ricevente;
	
	public StoricoOperazioniDTO() {
		super();
	}

	public OperazioneEnum getOperazione() {
		return operazione;
	}

	public void setOperazione(OperazioneEnum operazione) {
		this.operazione = operazione;
	}

	public Date getDataOperazione() {
		return dataOperazione;
	}

	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public MittenteDTO getRicevente() {
		return ricevente;
	}

	public void setRicevente(MittenteDTO ricevente) {
		this.ricevente = ricevente;
	}
	
	public StatoOperazioneEnum getStatoOperazione() {
		return statoOperazione;
	}

	public void setStatoOperazione(StatoOperazioneEnum statoOperazione) {
		this.statoOperazione = statoOperazione;
	}
	
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(StoricoOperazioniDTO o) {
		if(this.dataOperazione != null && o.getDataOperazione() != null) {
			if(this.dataOperazione.before(o.getDataOperazione())) return -1;
			if(this.dataOperazione.after(o.getDataOperazione())) return 1;
		}
		return 0;
	}
	
}