package it.sogei.libro_firma.data.service.impl.client.fed.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import it.sogei.libro_firma.data.model.FileFedDTO;
import it.sogei.libro_firma.data.model.enums.StatoOperazioneEnum;

public class OperazioneCompletataDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificatio documento")
	private String idDocumento;
	
	@Schema(description = "Identificatio richiesta")
	private String idRichiesta;
	
	@Schema(description = "CF utente assegnatario del documento")
	private String utenteAssegnatario;
	
	@Schema(description = "Tipo di azione da effettuare sul documento")
	private String azioneDaEffettuare;
	
	@Schema(description = "Lista dei files presenti")
	private List<FileFedDTO> files;
	
	@Schema(description = "Stato operazione richiesta")
	private StatoOperazioneEnum statoOperazione;
	
	@Schema(description = "Data operazione")
	private Date dataOperazione;
	
	public OperazioneCompletataDTO() {
		super();
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getUtenteAssegnatario() {
		return utenteAssegnatario;
	}

	public void setUtenteAssegnatario(String utenteAssegnatario) {
		this.utenteAssegnatario = utenteAssegnatario;
	}

	public List<FileFedDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileFedDTO> files) {
		this.files = files;
	}

	public StatoOperazioneEnum getStatoOperazione() {
		return statoOperazione;
	}

	public void setStatoOperazione(StatoOperazioneEnum statoOperazione) {
		this.statoOperazione = statoOperazione;
	}

	public Date getDataOperazione() {
		return dataOperazione;
	}

	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getAzioneDaEffettuare() {
		return azioneDaEffettuare;
	}

	public void setAzioneDaEffettuare(String azioneDaEffettuare) {
		this.azioneDaEffettuare = azioneDaEffettuare;
	}
	
}
