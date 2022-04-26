package it.sogei.libro_firma.data.util;

public class RicercaUtil {
	
	public static final  String RICERCA_AVANZATA_QUERY = ""
//			+ "{'$and': [ " 
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.urgente}, null] } } ," 
			+"{'urgente': :#{#ricercaAvanzataDto.urgente} }" 
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.nomeDocumento}, null] } } ," +
			"{ 'nomeDocumento': { '$regex': ':#{#ricercaAvanzataDto.nomeDocumento}', '$options': 'i'} }," +
			"{ 'documenti.nomeFile': { '$regex': ':#{#ricercaAvanzataDto.nomeDocumento}', '$options': 'i'} } "
		+"]},"
		+ "{'$or': [" + 
			"{ $expr: { $eq: [:#{#ricercaAvanzataDto.statoDocumento}, null] } } ," + 
			"{ '$and': [" + 
				"{ 'acl': {" + 
					"'$elemMatch': {" + 
						" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
						" 'statoDocumento': {'$eq' : null} " +
					"}" + 
				"}}," + 
				"{ 'statoDocumento': :#{#ricercaAvanzataDto.statoDocumento} }" + 
			"]}," +
				
			"{ 'acl': {" + 
				"'$elemMatch': {" + 
					" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
					" 'statoDocumento': {'$ne' : null}, " +
					" 'statoDocumento': :#{#ricercaAvanzataDto.statoDocumento} " +
				"}" + 
			"}}" +
		   "]}," 
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.nomeApplicazione}, null] } } ,"
			+ "{ 'appProduttore' : :#{#ricercaAvanzataDto.nomeApplicazione} }"
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.mittente}, null] } } ,"
			+ "{ 'utenteCreatore' : { '$regex': '^:#{#ricercaAvanzataDto.mittente}$', '$options': 'i'} }"
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr:{ $eq: [:#{#ricercaAvanzataDto.dataCaricamentoDa}, null] } } ,"
			+"{'dataCreazione': { '$gte': :#{#ricercaAvanzataDto.dataCaricamentoDa} } }" 
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.dataCaricamentoA}, null] } } ,"
			+"{'dataCreazione': { '$lte': :#{#ricercaAvanzataDto.dataCaricamentoA} } }" 
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.dataFirmaDa}, null] } } ,"
			+"{'operazioni': {"
				+"$elemMatch: {"
					+"'tipo': 'FIRMA', "
					+"'dataCompletamento': {'$ne' : null},"
					+"'dataCompletamento': { '$gte': :#{#ricercaAvanzataDto.dataFirmaDa} }"
				+"}"
			+"}}"
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.dataFirmaA}, null] } } ,"
			+"{'operazioni': {"
				+"$elemMatch: {"
					+"'tipo': 'FIRMA', "
					+"'dataCompletamento': {'$ne' : null},"
					+"'dataCompletamento': { '$lte': :#{#ricercaAvanzataDto.dataFirmaA} }"
				+"}"
			+"}}"
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.dataScadenzaDa}, null] } } ,"
			+"{$and: ["
				+"{'dataScadenza': {'$ne' : null}},"
				+"{'dataScadenza': { '$gte': :#{#ricercaAvanzataDto.dataScadenzaDa} } }"
			+"]}"
		+"]},"
		+ "{'$or': [ " 
			+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.dataScadenzaA}, null] } } ,"
			+"{$and: ["
				+"{'dataScadenza': {'$ne' : null}},"
				+"{'dataScadenza': { '$lte': :#{#ricercaAvanzataDto.dataScadenzaA} } }"
			+"]}"
	+ "]},"
	+ "{'$or': [ " 
		+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.fascicolo}, null] } } ,"
		+"{$and: ["
			+"{'fascicolo.nomeFascicolo': {'$ne' : null}},"
			+"{'fascicolo.nomeFascicolo': :#{#ricercaAvanzataDto.fascicolo} }"
		+"]}"
	+ "]},"
	+ "{'$or': [ " 
		+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.protocollo}, null] } } ,"
		+"{$and: ["
			+"{'$or' : [ "
				+"{$and: ["
					+"{'datiProcedimento.id': {'$ne' : null}},"
					+"{'datiProcedimento.id': :#{#ricercaAvanzataDto.protocollo} }"
				+"]},"
				+"{$and: ["
					+"{'datiProcedimento.numero': {'$ne' : null}}"
					+"{'datiProcedimento.numero': :#{#ricercaAvanzataDto.protocollo} }"
				+"]}"
			+"]}"
		+"]}"
	+ "]}"
	+ "{'$or': [ " 
		+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.assegnatario}, null] } } ,"
		+"{'assegnazione.assegnatari': {"
			+"$elemMatch: {"
				+"'utente': :#{#ricercaAvanzataDto.assegnatario},"
				+"'active': true"
			+"}"
		+"}}"
	+ "]},"
	+ "{'$or': [ " 
		+"{ $expr: { $eq: [:#{#ricercaAvanzataDto.collaboratore}, null] } } ,"
		+"{'acl': {"
			+"$elemMatch: {"
				+"'statoDocumento': 'CONDIVISO',"
				+"'utente': :#{#ricercaAvanzataDto.collaboratore},"
				+"'active': true"
			+"}"
		+"}}"
	+ "]}";
	
	public static final  String RICERCA_FILTRI_QUERY = "" +
		"{'$or': [ " +
			"{ $expr: { $eq: [:#{#ricercaFiltriDto.urgente}, null] } } ," +
			"{'urgente': :#{#ricercaFiltriDto.urgente} }" +
		"]}," +
		"{'$or': [ " +
			"{ $expr: { $eq: [:#{#ricercaFiltriDto.listaStatiDocumento}, []] } } ," +
			"{ '$and': [" + 
				"{ 'acl': {" + 
					"'$elemMatch': {" + 
						" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
						" 'statoDocumento': {'$eq' : null} " +
					"}" + 
				"}}," + 
				"{ 'statoDocumento': { $in: :#{#ricercaFiltriDto.listaStatiDocumento} } }" + 
			"] }," +
				
			"{ 'acl': {" + 
				"'$elemMatch': {" + 
					" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
					" 'statoDocumento': {'$ne' : null}, " +
					" 'statoDocumento': { $in: :#{#ricercaFiltriDto.listaStatiDocumento} }" +
				"}" + 
			"} }" +
		"] }," +
		"{'$or': [ " +
			"{ $expr: { $eq: [:#{#ricercaFiltriDto.scaduto}, false] } } ," +
			"{ 'dataScadenza' : { '$lte': new Date() } }" +
		"]}," +
		"{'$or': [ " + 
			"{ $expr: { $eq: [:#{#ricercaFiltriDto.inScadenza}, false] } } ,"+
			"{ '$and': [ " +
				"{'dataScadenza': {'$ne' : null} }," + 
				"{'dataScadenza': { '$gte':  new Date() } }" + 
			"]}" +
		"]}"+
		"{'$or': [ " + 
			"{ $expr: { $eq: [:#{#ricercaFiltriDto.verificato}, null] } } ,"+
			"{'verificato': {'$ne' : null} }" + 
		"]}" +
		"{'$or': [ " + 
			"{ $expr: { $eq: [:#{#ricercaFiltriDto.condiviso}, null] } } ,"+
			"{ $or: [ " +
				"{ '$and': [" +
					"{'utenteCreatore': { '$regex': '^:#{#utente}$', $options: 'i' } }," +
					"{ 'acl': {" + 
						"'$elemMatch': {" + 
							" 'active': true, " + 
							" 'statoDocumento': 'CONDIVISO'" +
						"}" + 
					"}}," +
				"] }" +
				"{ 'acl': {" + 
					"'$elemMatch': {" + 
						" 'active': true, " + 
						" 'statoDocumento': 'CONDIVISO', " +
						" 'utente': { '$regex': '^:#{#utente}$', $options: 'i' } " +
					"}" + 
				"}}" +
			" ] }" +
		"]}"; 
	
	public static final String RICERCA_TABELLARE_QUERY = "" +
			"{$or: [" + 
				"{ 'nomeDocumento': { '$regex': ':#{#ricercaTabellareDTO.param}', '$options': 'i'} }," +
				"{ 'tipoDocumento': { '$regex': ':#{#ricercaTabellareDTO.param}', '$options': 'i'} }," + 
				"{$and: [" +
					"{ $expr: { $ne: [:#{#ricercaTabellareDTO.utente}, null] } }," +
					"{ 'operazioni.utente' : { '$regex': '^:#{#ricercaTabellareDTO.utente}$', '$options': 'i'} }" +
				"] }," +
//				--"{ {$dateToString: {date: 'dataCreazione'}} : { '$regex': ':#{#ricercaTabellareDTO.param}', '$options': 'i'} }," +--
				"{ 'appProduttore': { '$regex': ':#{#ricercaTabellareDTO.param}', '$options': 'i'} }," +
				"{ 'nomeAppProduttore': { '$regex': ':#{#ricercaTabellareDTO.param}', '$options': 'i'} }" +
			"]}";
	
	public static final String RICERCA_STATO_DOCUMENTO = "" +
			"{'$or': [" + 
				"{ $expr: { $eq: [:#{#ricercaTabellareDTO.statoDocumento}, null] } } ," + 
				"{ '$and': [" + 
					"{ 'acl': {" + 
						"'$elemMatch': {" + 
							" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							" 'statoDocumento': {'$eq' : null} " +
						"}" + 
					"}}," + 
					"{ 'statoDocumento': :#{#ricercaTabellareDTO.statoDocumento} }" + 
				"]}," +
					
				"{ 'acl': {" + 
					"'$elemMatch': {" + 
						" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
						" 'statoDocumento': {'$ne' : null}, " +
						" 'statoDocumento': :#{#ricercaTabellareDTO.statoDocumento} " +
					"}" + 
				"}}" +
			"]}";
	
	public static final String RICERCA_STATO_DOCUMENTO_PARAM = "" +
			"{'$or': [" + 
				"{ '$and': [" + 
					"{ 'acl': {" + 
						"'$elemMatch': {" + 
							" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							" 'statoDocumento': {'$eq' : null} " +
						"}" + 
					"}}," + 
					"{ 'statoDocumento': :#{#param} }" + 
				"]}," +
					
				"{ 'acl': {" + 
					"'$elemMatch': {" + 
						" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
						" 'statoDocumento': {'$ne' : null}, " +
						" 'statoDocumento': :#{#param} " +
					"}" + 
				"}}" +
			"]}";
	
	public static final String RICERCA_MITTENTE_STATO_DOC_NULL = "" +
			"{'$and': [" +
				"{'utenteCreatore': { '$regex': :#{#utente}, $options: 'i' } }, " +
				"{'acl': " +
					"{ '$elemMatch':" +
						"{ " +
							"'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							"'statoDocumento': {'$eq': null}, " +
							"'active': true " + 
						"}" + 
					"} " +
				"}," +
				"{'$or': [" +
					"{'statoDocumento': { '$in': ['ANNULLATO', 'RIFIUTATO', 'FIRMA_ATTESA'] } }, " +
					"{'$and': [" +
						"{'assegnazione': {'$ne': null} }," +
						"{'assegnazione': {'$ne': []} }," +
						"{'statoDocumento': 'FIRMATO' }" +
					"]}" +
				"]}" +
			"]}";

	private RicercaUtil() {
		//
	}

}
