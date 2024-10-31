package com.crud.Crud.repository;

import com.crud.Crud.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository <Person, Long> {
}
