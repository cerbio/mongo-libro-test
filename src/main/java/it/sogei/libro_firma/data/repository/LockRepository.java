package it.sogei.libro_firma.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.sogei.libro_firma.data.entity.Lock;

@Repository
public interface LockRepository extends MongoRepository<Lock, String> {

}
