package com.example.demo.service;

import com.example.demo.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<Person> save(Person person);

    Mono<Person> findById(Long id);

    Flux<Person> findAll();

    Mono<Person> update(Person person);

    Mono<Void> deleteById(Long id);
}
