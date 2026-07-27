package com.api.estatistica.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.api.estatistica.model.MetricasModel;


public interface MetricasRepository extends MongoRepository<MetricasModel, String> {
    
}
