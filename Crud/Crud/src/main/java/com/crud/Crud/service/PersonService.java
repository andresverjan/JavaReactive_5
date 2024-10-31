package com.crud.Crud.service;

import com.crud.Crud.model.Person;
import com.crud.Crud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.management.MonitorInfo;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public Flux<Person> getPersons(){
        return personRepository.findAll()
            .doOnNext(person -> System.out.println("Data: " + person));
    }

    public Mono<Person> getPersonById(Long id){
        if (id ==null){
            return Mono.empty();
        }
        return personRepository.findById(id)
            .doOnNext(person -> System.out.println("Data getPersonById: " + person));
    }

    public Mono<Void> deletePersonById(Long id){
        if (id ==null){
            return Mono.error(new IllegalArgumentException("ID can´t be null"));
        }
        return personRepository.deleteById(id)
                .doOnNext(person -> System.out.println("Data deletePersonById : " + person));
    }

    public Mono<Person> create(Person person){
        return personRepository.save(person);
    }

    public Mono<String> update(Person person){
        if(person.getId() != null){
            return personRepository.save(person)
                    .doOnNext(p -> System.out.println("Data Updating: " + p))
                    .then(Mono.just("User update"));
        }else{
            return Mono.just("User is not present");
        }
    }
}
