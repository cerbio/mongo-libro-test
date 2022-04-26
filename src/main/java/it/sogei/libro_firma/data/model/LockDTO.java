package it.sogei.libro_firma.data.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class LockDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Lista dei documenti da bloccare/sbloccare")
	private List<String> idDocumenti;
	
	@Schema(description = "Check lock/unlock documenti")
	private boolean lock;
	
	@Schema(description = "Lista documenti che non possono essere lockati")
	private List<DocumentoDTO> documentiNonLockabili;

	public LockDTO() {
		super();
	}

	public List<String> getIdDocumenti() {
		return idDocumenti;
	}

	public void setIdDocumenti(List<String> utenti) {
		this.idDocumenti = utenti;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean invitoFirma) {
		this.lock = invitoFirma;
	}
	
	public List<DocumentoDTO> getDocumentiNonLockabili() {
		return documentiNonLockabili;
	}

	public void setDocumentiNonLockabili(List<DocumentoDTO> documentiNonLockabili) {
		this.documentiNonLockabili = documentiNonLockabili;
	}

}
