package it.sogei.libro_firma.data.util;

public class GruppoRicercaUtil {
	
	private GruppoRicercaUtil() {
		super();
	}
	
	/**
	 * Query gruppo ricerca
	 */
	public static final String QUERY_GRUPPO_RICERCA = "" +
			"{ '$or': [ " + 
				"{ $expr: { $eq: [:#{#gruppoRicerca.fieldToFilter}, null] } }," +
				"{ '$and': [ " + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.fieldToFilter}, 'statoDocumento'] } }," +
					"{'$or': [" + 
						"{ '$and': [" + 
							"{ 'acl': {" + 
								"'$elemMatch': {" + 
									" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
									" 'statoDocumento': {'$eq' : null} " +
								"}" + 
							"}}," + 
							"{ 'statoDocumento': { '$in': :#{#gruppoRicerca.valori} } }" + 
						"]}," +
							
						"{ 'acl': {" + 
							"'$elemMatch': {" + 
								" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
								" 'statoDocumento': {'$ne' : null}, " +
								" 'statoDocumento': { '$in': :#{#gruppoRicerca.valori} } " +
							"}" + 
						"}}" +
					"]}" +
				" ] }," +
				"{ '$and': [ " + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.fieldToFilter}, 'appProduttore'] } }," +
					"{ 'appProduttore' : { '$in': :#{#gruppoRicerca.valori} } }" +
				" ] }," +
				"{ '$and': [ " + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.fieldToFilter}, 'priorita'] } }," +
					"{'$or': [" + 
						"{ '$and': [" + 
							"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, true] } } ," +
							"{ '$or': [ " + 
								"{ 'urgente': true }," +
								"{ '$and': [ " +
									"{ 'scadenza': true }," +
									"{ 'scaduto': false }" +
								" ] }" +
							"] }" +
						"]}," +
						"{ '$and': [" + 
							"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, false] } } ," +
							"{ '$and': [ " + 
								"{ 'urgente': false }," +
								"{ 'scadenza': false }," +
								"{ 'scaduto': false }" +
							"] }" +
						"]}," +
					"]}" +
				" ] }," +
				"{ '$and': [ " + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.fieldToFilter}, 'numeroDocumenti'] } }," +
					"{'$or': [" + 
						"{ '$and': [" + 
							"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, false] } } ," +
							"{ 'numeroDocumenti': { '$eq': 1 } }" + 
						"]}," +
							
						"{ '$and': [" + 
							"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, true] } } ," +
							"{ 'numeroDocumenti': { '$gt': 1 } }" + 
						"]}," +
					"]}" +
				" ] }" +
			" ] }";
	
	/**
	 * query per il gruppo stato documento
	 
	public static final String QUERY_GRUPPO_STATO_DOCUMENTO = "" +
			"{'$or': [" + 
				"{ '$and': [" + 
					"{ 'acl': {" + 
						"'$elemMatch': {" + 
							" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
							" 'statoDocumento': {'$eq' : null} " +
						"}" + 
					"}}," + 
					"{ 'statoDocumento': { '$in': :#{#gruppoRicerca.valori} } }" + 
				"]}," +
					
				"{ 'acl': {" + 
					"'$elemMatch': {" + 
						" 'utente': { '$regex': :#{#utente}, $options: 'i' }, " + 
						" 'statoDocumento': {'$ne' : null}, " +
						" 'statoDocumento': { '$in': :#{#gruppoRicerca.valori} } " +
					"}" + 
				"}}" +
			"]}";
	*/
	
	/**
	 * query per il gruppo app federata
	 
	public static final String QUERY_GRUPPO_APP_FEDERATA = "" +
			"{ 'appProdduttore' : { '$in': :#{#gruppoRicerca.valori} } }";
	*/
	
	/**
	 * query per il gruppo priorita
	 
	public static final String QUERY_GRUPPO_PRIORITA = "" +
			"{'$or': [" + 
				"{ '$and': [" + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, true] } } ," +
					"{ '$or': [ " + 
						"{ 'urgente': true }," +
						"{ '$and': [ " +
							"{ 'scadenza': true }," +
							"{ 'scaduto': false }" +
						" ] }" +
					"] }" +
				"]}," +
					
				"{ '$and': [" + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, false] } } ," +
					"{ '$and': [ " + 
						"{ 'urgente': false }," +
						"{ 'scadenza': false }," +
						"{ 'scaduto': false }" +
					"] }" +
				"]}," +
			"]}";
	*/
	
	/**
	 * query per il gruppo numero documenti
	 
	public static final String QUERY_GRUPPO_N_DOCUMENTI = "" +
			"{'$or': [" + 
				"{ '$and': [" + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, false] } } ," +
					"{ 'numeroDocumenti': { '$eq': 1 }" + 
				"]}," +
					
				"{ '$and': [" + 
					"{ $expr: { $eq: [:#{#gruppoRicerca.flag}, true] } } ," +
					"{ 'numeroDocumenti': { '$gt': 1 }" + 
				"]}," +
			"]}";
	 */
	
}
