package it.sogei.libro_firma.data.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.repository.DocumentoRepository;

@Component
public class LibroFirmaDataScadenzaScheduler {
	
	private Logger log = LoggerFactory.getLogger(LibroFirmaDataScadenzaScheduler.class);
	
	@Autowired
	private DocumentoRepository docRepository;
	
	/**
	 * Verifica ed aggiorna il flag scaduto ai documenti con scadenza
	 */
	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void checkDocumentiScaduti() {
		log.info("LibroFirmaDataScadenzaScheduler.checkDocumentiScaduti: START");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		List<DocumentoModel> listaDocumentiScaduti = docRepository.findScadutiToCheck(cal.getTime());
		if(listaDocumentiScaduti == null || listaDocumentiScaduti.isEmpty()) {
			log.info("LibroFirmaDataScadenzaScheduler.checkDocumentiScaduti: nessun documento scaduto");
			return;
		}
		log.info("LibroFirmaDataScadenzaScheduler.checkDocumentiScaduti: numero documenti scaduti={}", listaDocumentiScaduti.size());
		for(DocumentoModel doc : listaDocumentiScaduti) {
			doc.setScaduto(true);
			docRepository.save(doc);
		}
		log.info("LibroFirmaDataScadenzaScheduler.checkDocumentiScaduti: documenti scaduti aggiornati");
	}

}
