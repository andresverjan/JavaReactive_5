package com.cedesistemas.crudreactive.services;

import com.cedesistemas.crudreactive.exceptions.PersonNotFoundException;
import com.cedesistemas.crudreactive.interfaces.PersonServiceInterface;
import com.cedesistemas.crudreactive.model.Person;
import com.cedesistemas.crudreactive.respository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService implements PersonServiceInterface {

    private final CrudRepository crudRepository;

    @Override
    public Mono<Person> savePerson(Person person) {
        return crudRepository.save(person);
    }

    @Override
    public Mono<Person> getPersonById(Integer id) {
        return crudRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person not found with id: " + id)));
    }

    @Override
    public Flux<Person> getAllPersons() {
        return crudRepository.findAll();
    }

    @Override
    public Mono<Person> updatePerson(Person person) {
        return crudRepository.findById(person.getId())
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person not found with id: " + person.getId())))
                .then(crudRepository.save(person));
    }

    @Override
    public Mono<Void> deletePerson(Integer id) {
        return crudRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person not found with id: " + id)))
                .then(crudRepository.deleteById(id));
    }
}