package it.sogei.libro_firma.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.sogei.libro_firma.data.entity.FiltriConfigModel;

@Repository
public interface FiltriConfigRepository extends MongoRepository<FiltriConfigModel, String> {

}
