package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.enums.AzioneEnum;

public class NotificaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id della notifica")
	private String idNotifica;

	@Schema(description = "cf utente che ha svolto l'operazione")
	private String utenteOperazione;
	
	@Schema(description = "Nome utente operazione")
	private String nomeUtenteOperazione;
	
	@Schema(description = "Cognome utente operazione")
	private String cognomeUtenteOperazione;

	@Schema(description = "utente che deve ricevere la notifica")
	private String utenteDestinatario;

	@Schema(description = "idDocumento che ha subito l'azione")
	private String idDocumento;

	@Schema(description = "azione svolta")
	private AzioneEnum azione;

	@Schema(description = "data notifica")
	private Date data;

	@Schema(description = "nome del documento")
	private String nomeDocumento;

	@Schema(description = "messaggio della notifica")
	private String notificaMsg;
	
	@Schema(description = "Flag app federata")
	private boolean appFed;

	public NotificaDTO() {
		super();
	}

	public String getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUtenteOperazione() {
		return utenteOperazione;
	}

	public void setUtenteOperazione(String utenteOperazione) {
		this.utenteOperazione = utenteOperazione;
	}

	public String getUtenteDestinatario() {
		return utenteDestinatario;
	}

	public void setUtenteDestinatario(String utenteDestinatario) {
		this.utenteDestinatario = utenteDestinatario;
	}

	
	public AzioneEnum getAzione() {
		return azione;
	}

	public void setAzione(AzioneEnum azione) {
		this.azione = azione;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getNotificaMsg() {
		return notificaMsg;
	}

	public void setNotificaMsg(String notificaMsg) {
		this.notificaMsg = notificaMsg;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNomeUtenteOperazione() {
		return nomeUtenteOperazione;
	}

	public void setNomeUtenteOperazione(String nomeUtenteOperazione) {
		this.nomeUtenteOperazione = nomeUtenteOperazione;
	}

	public String getCognomeUtenteOperazione() {
		return cognomeUtenteOperazione;
	}

	public void setCognomeUtenteOperazione(String cognomeUtenteOperazione) {
		this.cognomeUtenteOperazione = cognomeUtenteOperazione;
	}
	
	public boolean isAppFed() {
		return appFed;
	}

	public void setAppFed(boolean appFed) {
		this.appFed = appFed;
	}
	
}
