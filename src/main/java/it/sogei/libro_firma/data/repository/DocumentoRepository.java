package it.sogei.libro_firma.data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.sogei.libro_firma.data.entity.DocumentoModel;
import it.sogei.libro_firma.data.model.RicercaAvanzataDTO;
import it.sogei.libro_firma.data.model.RicercaFiltriDTO;
import it.sogei.libro_firma.data.model.RicercaTabellareDTO;
import it.sogei.libro_firma.data.util.GruppoRicercaParamsDTO;
import it.sogei.libro_firma.data.util.GruppoRicercaUtil;
import it.sogei.libro_firma.data.util.RicercaUtil;

@Repository
public interface DocumentoRepository extends MongoRepository<DocumentoModel, String> {
	
	/**
	 * Recupera i documenti visibili all'utente
	 * @param utente
	 * @return
	 */
	@Query("{ 'utenteCreatore': :#{#utente} }")
	public List<DocumentoModel> findByUtente(@Param("utente") String utente);
	
	/**
	 * Recupera la count dei documenti, visibili all'utente, per app
	 * @param utente
	 * @return
	 
	@Aggregation({
		"{'operazioni': {" + 
				"$elemMatch: {" +
					"{'utente': { '$regex': :#{#utente}, $options: 'i' } }, " + 
					"{'stato': 'APERTA'}" +
				"}" +
		"}",
		"{ '$group': { '_id' : '$appProduttore', cnt:{$sum:1} } }"
	})
	public List<CountAppAggregation> countByApp(@Param("utente") String utente);
	 */
	
	/**
	 * 
	 * @param utente
	 * @param param
	 * @return
	 */
	@Query("{ 'utenteCreatore': :#{#utente}, '$or': [" + 
				"{ 'nomeDocumento': { '$regex': :#{#param}, '$options': 'i'} }" + 
			"] }")
	public List<DocumentoModel> findByParam(@Param("utente") String utente, @Param("param") String param);

	/**
	 * Recupera i documenti per la preview di una ricerca
	 * @param utente, param, valori
	 * @param param
	 
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'acl.utente': { '$regex': :#{#utente}, '$options': 'i'} }," + 
				"{ :#{#stato} : { $in: :#{#valori} }}" +
				"{'$or': [ " + 
					"{ $expr: { $eq: [:#{#param}, null] } }," + 
					"{ 'nomeDocumento': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'tags': { '$regex': ':#{#param}', '$options': 'i'} } " +
				"] }" + 
			" ] }")
	public List<DocumentoModel> findByParamsAndStato(@Param("utente") String utente, @Param("param") String param, @Param("valori") List<String> valori, @Param("stato") String stato, Pageable p);
	*/
	
	/**
	 * Conta i documenti per la preview di una ricerca
	 * @param utente, param, valori
	 * @param param
	 
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'acl.utente': { '$regex': :#{#utente}, '$options': 'i'} }," + 
				"{ :#{#stato} : { $in: :#{#valori} }}" +
				"{'$or': [ " +
					"{ $expr: { $eq: [:#{#param}, null] } }," + 
					"{ 'nomeDocumento': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'tags': { '$regex': ':#{#param}', '$options': 'i'} } " +
				"] }" + 
			" ] }", count = true)
	public int countByParamsAndStato(@Param("utente") String utente, @Param("param") String param, @Param("valori") List<String> valori, @Param("stato") String stato);
	*/
	
	/**
	 * Recupera i documenti per utente e gruppo
	 * @param utente, param, valori
	 * @param param
	 
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'acl.utente': { '$regex': :#{#utente}, '$options': 'i'} }," + 
				"{ 'statoDocumento': { $in: :#{#valori} }}" + // per il gruppo
				"{'$or': [ " + 
					"{ 'nomeDocumento': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'tags': { '$regex': :#{#param}, '$options': 'i'} }, " +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				","+RicercaUtil.RICERCA_FILTRI_QUERY +
			" ] }")
	public List<DocumentoModel> findByParamsAndGroup(@Param("utente") String utente,@Param("param") String param, @Param("valori")List<String> valori, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto, Pageable p);
	*/
	
	/**
	 * Recupera i documenti per utente e nome file
	 * @param utente, param
	 * @param param
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{'acl': " + 
					"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				"{'$or': [ " + 
					"{ $expr: { $eq: [:#{#param}, null] } }," +
					"{ 'nomeDocumento': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'tags': { '$regex': ':#{#param}', '$options': 'i'} } " +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				","+RicercaUtil.RICERCA_FILTRI_QUERY +
			" ] }")
	public List<DocumentoModel> findByUtenteAndFileName(@Param("utente") String utente,@Param("param") String param, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto, Pageable p);
	
	/**
	 * Recupera il numero di documenti per utente e nome file
	 * @param utente, param
	 * @param param
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{'acl': " + 
				"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				"{'$or': [ " + 
					"{ $expr: { $eq: [:#{#param}, null] } }," +
					"{ 'nomeDocumento': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'tags': { '$regex': ':#{#param}', '$options': 'i'} } " +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				","+RicercaUtil.RICERCA_FILTRI_QUERY +
		" ] }",count = true)
	public int countByUtenteAndFileName(@Param("utente") String utente,@Param("param") String param, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);
	
	/**
	 * Recupera il numero di documenti per utente e gruppo
	 * @param utente, param, valori
	 * @param param
	 
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'acl.utente': { '$regex': :#{#utente}, '$options': 'i'} }," + 
				"{ 'statoDocumento': { $in: :#{#valori} }}" + // per il gruppo
				"{'$or': [ " + 
					"{ 'nomeDocumento': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'tags': { '$regex': :#{#param}, '$options': 'i'} }, " +
				"] }," +
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				","+RicercaUtil.RICERCA_FILTRI_QUERY +
			" ] }",count=true)
	public int countByParamsAndGroup(@Param("utente") String utente,@Param("param") String param, @Param("valori")List<String> valori, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);
	*/
	
	/**
	 * Recupera la lista di applicazioni per le quali l'utente ha una operazione da svolgere
	 * @param utente
	 * @return
	 */
	@Aggregation({
		"{ '$match': " + 
				"{ '$or': [" +
					"{'operazioni': " +
						"{ '$elemMatch':" +
							"{ " +
								"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								"'stato': 'APERTA'," +
								"'active': true " + 
							"}" + 
						"} " +
					"}," +
					RicercaUtil.RICERCA_MITTENTE_STATO_DOC_NULL +
				"] } " +
		"},",
		"{ '$group': { '_id' : '$appProduttore' } }"
	})
	public List<String> findDistinctAppProduttore(@Param("utente") String utente);
	
	/**
	 * Recupera il numero di documenti di una data applicazione, per i quali l'utente ha una operazione da svolgere
	 * @param utente
	 * @param appProd
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ '$or': [" +
					"{'operazioni': " +
						"{ '$elemMatch':" +
							"{ " +
								"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								"'stato': 'APERTA'," +
								"'active': true " + 
							"}" + 
						"} " +
					"}," +
					RicercaUtil.RICERCA_MITTENTE_STATO_DOC_NULL +
				"] }, " +
				"{'appProduttore': :#{#appProd} }, " + 
				RicercaUtil.RICERCA_FILTRI_QUERY +
			"]" + 
		"}", count = true)
	public long countToWorkByApp(@Param("utente") String utente, @Param("appProd") String appProd, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);

	/**
	 * Recupera il numero di documenti prioritari di una data applicazione, per i quali l'utente ha una operazione da svolgere
	 * @param utente
	 * @param appProd
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ '$or': [" +
					"{'operazioni': " +
						"{ '$elemMatch':" +
							"{ " +
								"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								"'stato': 'APERTA'," +
								"'active': true " + 
							"}" + 
						"} " +
					"}," +
					RicercaUtil.RICERCA_MITTENTE_STATO_DOC_NULL +
				"] }, " +
				"{'appProduttore': :#{#appProd} }, " + 
				"{'$or': [ " + 
					"{ '$and': [ " +
						"{'urgente': {'$ne' : null} }," + 
						"{'urgente': true}" + 
					"] }, " + 
					"{ '$and': [ " +
						"{'dataScadenza': {'$ne' : null} }," + 
						"{'dataScadenza': { '$gte': :#{#dataScadenza} } }" + 
					"] }" +
				"] }" + 
		" ] }", count = true)
	public long countToWorkByAppPrioritari(@Param("utente") String utente, @Param("appProd") String appProd, @Param("dataScadenza") Date dataScadenza);
	
	/**
	 * Recupera i documenti di una data applicazione, per i quali l'utente ha una operazione da svolgere
	 * @param utente
	 * @param appProd
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ '$or': [" +
					"{'operazioni': " +
						"{ '$elemMatch':" +
							"{ " +
								"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								"'stato': 'APERTA'," +
								"'active': true " + 
							"}" + 
						"} " +
					"}," +
					RicercaUtil.RICERCA_MITTENTE_STATO_DOC_NULL +
				"] }, " +
				"{'appProduttore': :#{#appProd} }, " + 
				RicercaUtil.RICERCA_FILTRI_QUERY +
			"]" + 
		"}")
	public List<DocumentoModel> findToWorkByApp(@Param("utente") String utente, @Param("appProd") String appProd, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto, Pageable p);
	
	/**
	 * Recupera i documenti per i quali l'utente ha una operazione da svolgere
	 * @param utente
	 * @param appProd
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ '$or': [" +
					"{'operazioni': " +
						"{ '$elemMatch':" +
							"{ " +
								"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								"'stato': 'APERTA'," +
								"'active': true " + 
							"}" + 
						"} " +
					"}," +
					RicercaUtil.RICERCA_MITTENTE_STATO_DOC_NULL +
				"] }, " +
				RicercaUtil.RICERCA_FILTRI_QUERY +					
			"]}")
	public List<DocumentoModel> findToWork(@Param("utente") String utente, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto,Pageable p);
	
	/**
	 * Recupera il numero di documenti per i quali l'utente ha una operazione da svolgere
	 * @param utente
	 * @param appProd
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ '$or': [" +
					"{'operazioni': " +
						"{ '$elemMatch':" +
							"{ " +
								"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								"'stato': 'APERTA'," +
								"'active': true " + 
							"}" + 
						"} " +
					"}," +
					RicercaUtil.RICERCA_MITTENTE_STATO_DOC_NULL +
				"] }, " +
				RicercaUtil.RICERCA_FILTRI_QUERY +					
			"]}", count = true)
	public long countToWork(@Param("utente") String utente, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);

	/**
	 * Restituisce il numero di documenti prioritari dato un utente
	 * @param utente
	 * @param ricercaTabellareDTO 
	 * @param dataScadenza
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'operazioni': " +
					"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'stato': 'APERTA' ," +
							"'active': true " + 
						"}" + 
					"}" +
				"}," + 
				RicercaUtil.RICERCA_STATO_DOCUMENTO + "," +
				"{'$or': [ " + 
					"{ '$and': [ " +
						"{'urgente': {'$ne' : null} }," + 
						"{'urgente': true}" + 
					"] }, " + 
					"{ '$and': [ " +
						"{'dataScadenza': {'$ne' : null} }," + 
						"{'dataScadenza': { '$gte': :#{#dataScadenza} } }" + 
					"] }" +
				"] }," +
				"{'$or': [" +
					"{ $expr: { $eq: [:#{#ricercaTabellareDTO.statoDocumento}, null] } }," +
					"{ }" +
				"]}," +
				"{'$or': [ " +
					"{ $expr: { $eq: [:#{#ricercaTabellareDTO.param}, null] } }," +
					RicercaUtil.RICERCA_TABELLARE_QUERY +
				"] }" +
		" ] }", count = true)
	public Long countToWorkPrioritari(@Param("utente") String utente, @Param("ricercaTabellareDTO") RicercaTabellareDTO ricercaTabellareDTO, @Param("dataScadenza") Date dataScadenza);
	
	/**
	 * Count dei contatori per la ricerca avanzata ToWork
	 * @param param
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'operazioni': " +
					"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'stato': 'APERTA'," +
							"'active': true " + 
						"}" + 
					"}" +
				"}" + 
				"{'$or': [ " +
					"{ $expr: { $eq: [:#{#appProd}, null] } } ," +
					"{'appProduttore': :#{#appProd} }" + 
				"]}"+
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				", "+ RicercaUtil.RICERCA_FILTRI_QUERY +				
				",{'$or': [ " +
					RicercaUtil.RICERCA_STATO_DOCUMENTO_PARAM + "," +
					"{'$and': [ " +
						"{ $expr: { $eq: [:#{#param}, 'SCADUTO'] } } ," +
						"{'dataScadenza': { '$lt': :#{#now} } } " + 
					"]},"+
					"{'$and': [ " +
						"{ $expr: { $eq: [:#{#param}, 'IN_SCADENZA'] } } ," +
						"{'dataScadenza': { '$gte': :#{#now} } } " + 
					"]}"+
				"]}"+
			"]" + 
		"}", count = true)
	public Long countRicercaAvanzataTOWORK(@Param("param") String param,@Param("appProd") String appProd, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, 
										   @Param("now") Date date, @Param("utente") String utente,@Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);
	/**
	 * Count dei contatori per la ricerca avanzata Search
	 * @param param
	 * @return
	 
	@Query(value = "{" +
			"'$and': [ " +
				"{ 'acl.utente': { '$regex': :#{#utente}, '$options': 'i'} }," + 
				"{'$and': [ " +
					"{'$or': [" + 
						"{ $expr: { $eq: [:#{#nomeFile}, null] } }," + 
						"{ 'nomeDocumento': { '$regex': ':#{#nomeFile}', '$options': 'i'} }," +
						"{ 'documenti.nomeFile': { '$regex': ':#{#nomeFile}', '$options': 'i'} }, " +
						"{ 'tags': { '$regex': ':#{#nomeFile}', '$options': 'i'} } " +
					"]}," + 
					"{'$or': [" + 
						"{ $expr: { $eq: [:#{#valori}, [] ] } }," + 
						"{'statoDocumento': { $in: :#{#valori} }}" + // per il gruppo
					"]}" +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				", " +
				RicercaUtil.RICERCA_FILTRI_QUERY +
				", " +
				"{'$or': [ " +
//					--"{'statoDocumento': :#{#param} }, "-- + // per il contatore RICERCA_STATO_DOCUMENTO_PARAM
					RicercaUtil.RICERCA_STATO_DOCUMENTO_PARAM + "," +
					"{'$and': [ " +
						"{ $expr: { $eq: [:#{#param}, 'SCADUTO'] } } ," +
						"{'dataScadenza': { '$lt': :#{#now} } } " + 
					"]},"+
					"{'$and': [ " +
						"{ $expr: { $eq: [:#{#param}, 'IN_SCADENZA'] } } ," +
						"{'dataScadenza': { '$gte': :#{#now} } } " + 
					"]}"+
				"]}"+
			"]" + 
		"}", count = true)
	public Long countRicercaAvanzataSEARCH(@Param("param") String param,@Param("nomeFile") String nomeFile, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto,
										   @Param("now") Date date, @Param("utente") String utente, @Param("valori") List<String> valori,@Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);
	*/
	
	/**
	 * TODO SERVE???
	 * @param ricercaAvanzataDto
	 * @return
	 */
	@Query(value =  "{'$and': [ "+ RicercaUtil.RICERCA_AVANZATA_QUERY +"]}")
	public List<DocumentoModel> ricercaAvanzata( @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto);

	/**
	 * Recupera i documenti scaduti
	 * @param dataScadenza
	 * @return
	 */
	@Query("{ " + 
			"'$and': [ " +
				"{'dataScadenza': {'$ne' : null} }," + 
				"{'dataScadenza': { '$lt': :#{#dataScadenza} } }," +
				"{'scaduto': false }" +
			"] " + 
			"}")
	public List<DocumentoModel> findScadutiToCheck(@Param("dataScadenza") Date dataScadenza);

	/**
	 * Controlla se l'utente è il creatore o il firmatario del documento
	 * @param idDoc
	 * @param username
	 * @return
	 */
	@Query(value = 
		   "{"+
			"'$and' : [ "+
				"{'_id' : ObjectId(':#{#idDoc}') },"+
				"{'$or' : [ "+
					"{'utenteCreatore': ':#{#cf}'},"+
					"{'$and' : ["+
						 "{'acl.utente': ':#{#cf}'},"+
						 "{'acl.tipo' : 'FIRMA'}"+
					"]}"+
				"] } "+
			"] "+
		   "}", exists = true)
	public Boolean checkUtenteVerificaFirma(@Param("idDoc") String idDoc,@Param("cf") String cf);

	/**
	 * Verifica se il documento è firmato
	 * @param idDoc
	 * @return
	 */
	@Query(value = 
			   "{"+
				"'$and' : [ "+
					"{'_id' : ObjectId(':#{#idDoc}') },"+
					"{'statoDocumento' : 'FIRMATO'}"+
				"] "+
			   "}", exists = true)
	public Boolean checkFirmaSuDocumento(@Param("idDoc")String idDoc);
	
	/**
	 * Restituisce i documenti prioritari dato un utente
	 * @param utente
	 * @param ricercaTabellareDTO 
	 * @param dataScadenza
	 * @param p 
	 * @return
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{ 'operazioni': " +
					"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'stato': 'APERTA', " +
							"'active': true " + 
						"}" + 
					"}" +
				"}," + 
				RicercaUtil.RICERCA_STATO_DOCUMENTO + "," +
				"{'$or': [ " + 
					"{ '$and': [ " +
						"{'urgente': {'$ne' : null} }," + 
						"{'urgente': true}" + 
					"] }, " + 
					"{ '$and': [ " +
						"{'dataScadenza': {'$ne' : null} }," + 
						"{'dataScadenza': { '$gte': :#{#dataScadenza} } }" + 
					"] }" +
				"] }," +
				"{'$or': [ " +
					"{ $expr: { $eq: [:#{#ricercaTabellareDTO.param}, null] } }," +
					RicercaUtil.RICERCA_TABELLARE_QUERY +
				"] }" +
		" ] }", collation = "en")
	public List<DocumentoModel> findDocPrioritari(@Param("utente") String utente, @Param("ricercaTabellareDTO") RicercaTabellareDTO ricercaTabellareDTO, @Param("dataScadenza") Date dataScadenza, Pageable p);

	/**
	 * Recupera i documenti per la preview di una ricerca
	 * @param utente, param, valori
	 * @param param
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{'acl': " + 
				"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				GruppoRicercaUtil.QUERY_GRUPPO_RICERCA + "," +
				"{'$or': [ " + 
					"{ $expr: { $eq: [:#{#param}, null] } }," + 
					"{ 'nomeDocumento': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'tags': { '$regex': ':#{#param}', '$options': 'i'} } " +
				"] }" + 
			" ] }")
	public List<DocumentoModel> findByParamsAndStato(@Param("utente") String utente, @Param("param") String param, @Param("gruppoRicerca") GruppoRicercaParamsDTO gruppoRicerca, Pageable p);

	@Query(value = "{" + 
			"'$and': [ " +
				"{'acl': " + 
				"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				GruppoRicercaUtil.QUERY_GRUPPO_RICERCA + "," +
				"{'$or': [ " + 
					"{ $expr: { $eq: [:#{#param}, null] } }," + 
					"{ 'nomeDocumento': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': ':#{#param}', '$options': 'i'} }, " +
					"{ 'tags': { '$regex': ':#{#param}', '$options': 'i'} } " +
				"] }" + 
			" ] }", count = true)
	public int countByParamsAndStato(@Param("utente") String utente, @Param("param") String param, @Param("gruppoRicerca") GruppoRicercaParamsDTO gruppoRicerca);

	/**
	 * Recupera i documenti per utente e gruppo
	 * @param utente, param, valori
	 * @param param
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{'acl': " + 
				"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				GruppoRicercaUtil.QUERY_GRUPPO_RICERCA + "," +
				"{'$or': [ " + 
					"{ 'nomeDocumento': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'tags': { '$regex': :#{#param}, '$options': 'i'} }, " +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				","+RicercaUtil.RICERCA_FILTRI_QUERY +
			" ] }")
	public List<DocumentoModel> findByParamsAndGroup(@Param("utente") String utente, @Param("param") String param, @Param("gruppoRicerca") GruppoRicercaParamsDTO gruppoRicerca, 
			@Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto, Pageable p);

	/**
	 * Recupera i documenti per utente e gruppo
	 * @param utente, param, valori
	 * @param param
	 */
	@Query(value = "{" + 
			"'$and': [ " +
				"{'acl': " + 
				"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				GruppoRicercaUtil.QUERY_GRUPPO_RICERCA + "," +
				"{'$or': [ " + 
					"{ 'nomeDocumento': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'documenti.nomeFile': { '$regex': :#{#param}, '$options': 'i'} }, " +
					"{ 'tags': { '$regex': :#{#param}, '$options': 'i'} }, " +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				","+RicercaUtil.RICERCA_FILTRI_QUERY +
			" ] }", count = true)
	public int countByParamsAndGroup(@Param("utente") String utente, @Param("param") String param, @Param("gruppoRicerca") GruppoRicercaParamsDTO gruppoRicerca, 
			@Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);

	/**
	 * Count dei contatori per la ricerca avanzata Search
	 * @param param
	 * @return
	 */
	@Query(value = "{" +
			"'$and': [ " +
				"{'acl': " + 
				"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'active': true " + 
						"}" +
					"}"+
				"}"+
				"{'$and': [ " +
					"{'$or': [" + 
						"{ $expr: { $eq: [:#{#nomeFile}, null] } }," + 
						"{ 'nomeDocumento': { '$regex': ':#{#nomeFile}', '$options': 'i'} }," +
						"{ 'documenti.nomeFile': { '$regex': ':#{#nomeFile}', '$options': 'i'} }, " +
						"{ 'tags': { '$regex': ':#{#nomeFile}', '$options': 'i'} } " +
					"]}," + 
					"{'$or': [" + 
						"{ $expr: { $eq: [:#{#gruppoRicerca}, null ] } }," + 
						GruppoRicercaUtil.QUERY_GRUPPO_RICERCA +
					"]}" +
				"] }," + 
				RicercaUtil.RICERCA_AVANZATA_QUERY +
				", " +
				RicercaUtil.RICERCA_FILTRI_QUERY +
				", " +
				"{'$or': [ " +
//					--"{'statoDocumento': :#{#param} }, "-- + // per il contatore RICERCA_STATO_DOCUMENTO_PARAM
					RicercaUtil.RICERCA_STATO_DOCUMENTO_PARAM + "," +
					"{'$and': [ " +
						"{ $expr: { $eq: [:#{#param}, 'SCADUTO'] } } ," +
						"{'dataScadenza': { '$lt': :#{#now} } } " + 
					"]},"+
					"{'$and': [ " +
						"{ $expr: { $eq: [:#{#param}, 'IN_SCADENZA'] } } ," +
						"{'dataScadenza': { '$gte': :#{#now} } } " + 
					"]}"+
				"]}"+
			"]" + 
		"}", count = true)
	public long countRicercaAvanzataSEARCH(@Param("param") String param,@Param("nomeFile") String nomeFile, @Param("ricercaAvanzataDto") RicercaAvanzataDTO ricercaAvanzataDto,
			   @Param("now") Date date, @Param("utente") String utente, @Param("gruppoRicerca") GruppoRicercaParamsDTO gruppoRicerca, @Param("ricercaFiltriDto") RicercaFiltriDTO ricercaFiltriDto);

	/**
	 * 
	 * @param idDocumento
	 * @param idRichiesta
	 * @return
	 */
	@Query(value = "{" +
				"'$and': ["+
					"{'_id' : ObjectId(':#{#idDocumento}') },"+
					"{'idRichiesta' : :#{#idRichiesta} }"+
				"]"+
			"}")
	public DocumentoModel findByIdAndRichiesta(@Param("idDocumento")String idDocumento,@Param("idRichiesta")String idRichiesta);
	
	@Query(value =
			"{'tags.idTag': :#{#idTag}" + 
			"}")
	public List<DocumentoModel> recuperaDocumentiByTag(@Param("idTag") String idTag);
}
