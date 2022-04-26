package it.sogei.libro_firma.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import it.sogei.libro_firma.data.entity.TagModel;

@Repository
public interface TagRepository extends MongoRepository<TagModel, String> {

	/**
	 * 
	 * Recupero dei tag a partire dal gruppo o dall'utente creatore
	 * @param codiceGruppo
	 * @param cfUtente
	 * @return
	 */
	@Query(value = "{" + 
			"'$and' : [" +
				"{ '$or': [ " + 
					"{ '$or': [ " +
						"{ $expr: { $eq: [':#{#codiceGruppo}', 'null'] } } ," +
						"{'gruppi':{$in : :#{#codiciGruppo}}}" +
					"] }, " + 
					"{ 'utenteCreatore': { '$regex': '^:#{#cfUtente}$', $options: 'i' } }" +
				"] },"+
				"{ '$or': [ " + 
					"{ $expr: { $eq: [':#{#nomeTag}', 'null'] } }," +
					"{'nome': { '$regex': :#{#nomeTag}, $options: 'i' } }" + 				
				"] }"+
			"] }")
	public List<TagModel> findByGroupOrUtenteCreatore(@Param("codiciGruppo") List<String> codiciGruppi, @Param("nomeTag") String nomeTag ,@Param("cfUtente") String cfUtente);

	/**
	 * 
	 * Recupero dei tag a partire dal gruppo o dall'utente creatore
	 * @param codiceGruppo
	 * @param cfUtente
	 * @param p 
	 * @return
	 */
	@Query(value = "{" + 
			"'$and' : [" +
				"{ '$or': [ " + 
					"{ '$or': [ " + // deve essere una and
						"{ $expr: { $eq: [':#{#codiceGruppo}', 'null'] } } ," +
						"{'gruppi': ':#{#codiceGruppo}' }" + 
					"] }, " + 
					"{ 'utenteCreatore': { '$regex': '^:#{#cfUtente}$', $options: 'i' } }" +
				"] },"+
				"{ '$or': [ " + 
					"{ $expr: { $eq: [':#{#nomeTag}', 'null'] } }," +
					"{'nome': { '$regex': ':#{#nomeTag}', $options: 'i' } }" + 				
				"] }"+
			"] }")
	public List<TagModel> getByGroupOrUtenteCreatore(@Param("codiceGruppo") String codiceGruppo, @Param("nomeTag") String nomeTag ,@Param("cfUtente") String cfUtente, Pageable p);

	/**
	 * 
	 * Recupero dei tag a partire dal gruppo o dall'utente creatore
	 * @param codiceGruppo
	 * @param cfUtente
	 * @return
	 */
	@Query(value = "{" + 
			"'$and' : [" +
				"{ '$or': [ " + 
					"{ '$or': [ " +
						"{ $expr: { $eq: [':#{#codiceGruppo}', 'null'] } } ," +
						"{'gruppi': ':#{#codiceGruppo}' }" + 
					"] }, " + 
					"{ 'utenteCreatore': { '$regex': '^:#{#cfUtente}$', $options: 'i' } }" +
				"] },"+
				"{ '$or': [ " + 
					"{ $expr: { $eq: [':#{#nomeTag}', 'null'] } }," +
					"{'nome': { '$regex': ':#{#nomeTag}', $options: 'i' } }" + 				
				"] }"+
			"] }", count = true)
	public int countByGroupOrUtenteCreatore(@Param("codiceGruppo") String codiceGruppo, @Param("nomeTag") String nomeTag ,@Param("cfUtente") String cfUtente);

	
	/**
	 * Controllo dell'esistenza di un tag in un gruppo 
	 * @param nomeTag
	 * @param codiceGruppo
	 * @return
	 */
	@Query(value = "{" +
			" '$and': [ " +
				"{'nome': { '$regex': '^:#{#nomeTag}$', $options: 'i' }} " +
				"{'gruppi':{$in : :#{#codiciGruppo}}}" + 
			"] } ",exists = true)
	public boolean checkByGruppo(@Param("nomeTag") String nomeTag,@Param("codiciGruppo") List<String> codiciGruppo);
	
	/**
	 * Controllo dell'esistenza di un tag in un gruppo o di un'utente
	 * @param nomeTag
	 * @param codiceGruppo
	 * @param utenteCreatore
	 * @return
	 */
	@Query(value = "{" +
			" '$and': [ " +
				"{'nome': { '$regex': '^:#{#nomeTag}$', $options: 'i' }} " +
				"{'$or': [ " + 
					"{ '$and': [ " +
						"{'gruppi':{$in : :#{#codiciGruppo}}}" +
					"] }, " + 
				"{ 'utenteCreatore': { '$regex': '^:#{#cfUtente}$', $options: 'i' } }" +
				"] }" +
			"] } ",exists = true)
	public boolean existsByGruppoOrUtenteCreatore(@Param("nomeTag") String nomeTag, @Param("codiciGruppo") List<String> codiciGruppo,@Param("cfUtente") String utenteCreatore);
	
	/**
	 * Controllo dell'esistenza di un tag
	 * @param nomeTag
	 * @return
	 */
	@Query(value = "{'nome': { '$regex': '^:#{#nomeTag}$', $options: 'i' }} "
			,exists = true)
	public boolean existsByNome(@Param("nomeTag") String nomeTag);

	/**
	 * 
	 * @param nomeTag
	 * @return
	 */
	@Query(value = "{'nome': { '$regex': ':#{#nomeTag}', $options: 'i' }} ")
	public List<TagModel> tagsByNome(@Param("nomeTag") String nomeTag,Pageable p);
	
	
	/**
	 * 
	 * @param nomeTag
	 * @return
	 */
	@Query(value = "{'nome': { '$regex': ':#{#nomeTag}', $options: 'i' }} ",count = true)
	public int countByNome(@Param("nomeTag") String nomeTag);
}
