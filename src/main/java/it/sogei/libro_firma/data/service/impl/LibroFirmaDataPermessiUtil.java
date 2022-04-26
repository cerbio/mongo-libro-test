package it.sogei.libro_firma.data.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.sogei.libro_firma.data.entity.AclModel;
import it.sogei.libro_firma.data.entity.DataConfigModel;
import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.entity.Lock;
import it.sogei.libro_firma.data.entity.OperazioneModel;
import it.sogei.libro_firma.data.model.enums.StatoDocumentoEnum;
import it.sogei.libro_firma.data.model.enums.StatoOperazioneEnum;
import it.sogei.libro_firma.data.model.enums.TipoOperazioneEnum;
import it.sogei.libro_firma.data.repository.DataConfigRepository;
import it.sogei.libro_firma.data.repository.LockRepository;

@Component
public class LibroFirmaDataPermessiUtil {
	
	private static final String LOCK_TIME = "lockTime";
	private static int minuti;
	
	@Autowired
	private DataConfigRepository configRepository;
	
	@Autowired
	private LockRepository lockRepository;
	
	public LibroFirmaDataPermessiUtil() {
		super();
	}
	
	@PostConstruct
	private void init() {
		DataConfigModel defaultTime = new DataConfigModel();
		defaultTime.setValore("120");
		LibroFirmaDataPermessiUtil.minuti = Integer.parseInt(configRepository.findById(LOCK_TIME).orElse(defaultTime).getValore());
	}

	/**
	 * Verifica se l'utente ha i permessi di firma
	 * @param username
	 * @param model 
	 * @return
	 */
	public boolean getForSign(String username, DocumentoModel model) {
		for(OperazioneModel op : model.getOperazioni()) {
			if(TipoOperazioneEnum.FIRMA.name().equalsIgnoreCase(op.getTipo()) &&
					StatoOperazioneEnum.APERTA.name().equalsIgnoreCase(op.getStato()) && 
					op.isActive() &&
					username.equalsIgnoreCase(op.getUtente())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica se l'utente ha i permessi di aggiornamento
	 * @param username
	 * @param model 
	 * @return
	 */
	public boolean getForUpdate(String username, DocumentoModel model) {
		for (OperazioneModel op : model.getOperazioni()) {
			if (TipoOperazioneEnum.CREAZIONE.name().equalsIgnoreCase(op.getTipo())
					&& username.equalsIgnoreCase(op.getUtente())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se l'utente ha i permessi di eliminazione
	 * @param username
	 * @param model 
	 * @return
	 */
	public boolean getForDelete(String username, DocumentoModel model) {
		// eliminazione solo se mittente e (non sono state apposte firme o il documento è annullato)
		for (OperazioneModel op : model.getOperazioni()) {
			if (TipoOperazioneEnum.CREAZIONE.name().equalsIgnoreCase(op.getTipo())
					&& username.equalsIgnoreCase(op.getUtente())
					&& ((model.getFirmeLfuApplicate() == null || model.getFirmeLfuApplicate().isEmpty()) ||
							StatoDocumentoEnum.ANNULLATO.name().equalsIgnoreCase(model.getStatoDocumento()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se l'utente ha i permessi di visualizzazione
	 * @param username
	 * @param model
	 * @return
	 */
	public boolean getForView(String username, DocumentoModel model) {
		for(AclModel acl : model.getAcl()) {
			if(username.equalsIgnoreCase(acl.getUtente()) && acl.isActive()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param username
	 * @param model
	 * @return
	 */
	public boolean getForShare(String username, DocumentoModel model) {
		for (OperazioneModel op : model.getOperazioni()) {
			if (TipoOperazioneEnum.CREAZIONE.name().equalsIgnoreCase(op.getTipo())
					&& username.equalsIgnoreCase(op.getUtente())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param data
	 * @param isStart
	 * @return
	 */
	public Date getRangeDate(Date data, boolean isStart) {
		if(data == null) {
			return null;
		}
		Calendar calDataDa = Calendar.getInstance();
		calDataDa.setTime(data);
		calDataDa.set(Calendar.HOUR_OF_DAY, isStart ? 0 : 22);
		calDataDa.set(Calendar.MINUTE, isStart ? 0 : 59);
		calDataDa.set(Calendar.SECOND, isStart ? 0 : 59);
		return calDataDa.getTime();
	}
	
	
	/**
	 * restituisce true se il documento è bloccato
	 * @param model
	 * @param user
	 * @return
	 */
	public boolean verificaLock(DocumentoModel model, String user) {
		Lock lock = lockRepository.findById(model.getId()).orElse(null);
		if (lock != null) {
			if (lock.getUtenteCreatore().equals(user)) {
				return false;
			}
			else {
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(lock.getData()); 
				cal.add(Calendar.MINUTE, minuti);
				Date dataInc = cal.getTime();
				return dataInc.after(new Date());
			}
		}
		return false;

	}
}
