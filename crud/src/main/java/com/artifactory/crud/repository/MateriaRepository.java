package com.artifactory.crud.repository;

import com.artifactory.crud.model.Materia;
import com.artifactory.crud.model.Person;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MateriaRepository extends ReactiveCrudRepository<Materia, Long> {

}
