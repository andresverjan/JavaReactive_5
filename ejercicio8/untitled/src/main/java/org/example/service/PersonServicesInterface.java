package org.example.service;

import org.example.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonServicesInterface {
    Mono<Person> save(Person person);

    Mono<Person> findById(Long id);

    Flux<Person> findAll();

    Mono<String> update(Person person);

    Mono<Void> deletePersonById(Long id);
}
