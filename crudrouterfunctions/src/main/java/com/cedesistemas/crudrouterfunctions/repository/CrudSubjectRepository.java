package com.cedesistemas.crudrouterfunctions.repository;

import com.cedesistemas.crudrouterfunctions.model.Subject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudSubjectRepository extends ReactiveCrudRepository<Subject, Integer> {
}
