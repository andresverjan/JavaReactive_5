package com.cedesistemas.crudreactive.respository;

import com.cedesistemas.crudreactive.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudRepository extends ReactiveCrudRepository<Person, Integer> {
}
