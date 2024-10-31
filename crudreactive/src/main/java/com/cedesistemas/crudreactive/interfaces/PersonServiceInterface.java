package com.cedesistemas.crudreactive.interfaces;

import com.cedesistemas.crudreactive.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonServiceInterface {
    Mono<Person> savePerson(Person person);

    Mono<Person> getPersonById(Integer id);

    Flux<Person> getAllPersons();

    Mono<Person> updatePerson(Person person);

    Mono<Void> deletePerson(Integer id);
}
