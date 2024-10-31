package com.artifactory.crud.repository;

import com.artifactory.crud.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, Long>{
}
