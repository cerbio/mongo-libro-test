package it.sogei.libro_firma.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.sogei.libro_firma.data.entity.GruppoModel;

@Repository
public interface GruppoRepository extends MongoRepository<GruppoModel, String> {
	
	/**
	 * Recupera i gruppi dato il tipo gruppo
	 * @param utente
	 * @return
	 */
	@Query("{ 'tipoGruppo': :#{#groupByParam} }")
	public List<GruppoModel> findByTipoGruppo(@Param("groupByParam") String groupByParam);
	
	/**
	 * Recupera i gruppi dato l'idGruppo
	 * @param utente
	 * @return
	 */
	@Query("{ 'idGruppo': :#{#groupByIdGruppo} }")
	public GruppoModel findByIdGruppo(@Param("groupByIdGruppo") Integer idGruppo);
}
