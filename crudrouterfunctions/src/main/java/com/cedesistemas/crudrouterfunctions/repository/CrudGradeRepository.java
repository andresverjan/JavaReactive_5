package com.cedesistemas.crudrouterfunctions.repository;

import com.cedesistemas.crudrouterfunctions.model.Grade;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudGradeRepository extends ReactiveCrudRepository<Grade, Integer> {
}
