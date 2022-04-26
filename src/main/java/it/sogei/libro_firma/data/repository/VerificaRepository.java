package it.sogei.libro_firma.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.sogei.libro_firma.data.entity.VerificaModel;

public interface VerificaRepository extends MongoRepository<VerificaModel, String>{
	
}
