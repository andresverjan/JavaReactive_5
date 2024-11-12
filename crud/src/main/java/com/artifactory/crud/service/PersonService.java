package com.artifactory.crud.service;

import com.artifactory.crud.model.Person;
import com.artifactory.crud.repository.PersonRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository ;

    public Flux<Person> getPerson(){
        return personRepository.findAll()
                .doOnNext(person -> System.out.println(" Data " + person) )
                .onErrorResume(e-> {
                    System.out.println("Error: " + e.getMessage());
                    return Flux.empty();
                }
                        );
    }

    public Mono<Person> getPersonById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return personRepository.findById(id)
                .doOnNext(person -> System.out.println(" Data getPersonByid " + person) );
    }

    public Mono<Void> deletePersonById(Long id){

        return personRepository.deleteById(id)
                .doOnNext(p-> System.out.println("Borrado :: " + id))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Person> create(Person person){

        return personRepository.save(person);
    }

    public Mono<Person> update(Person person){

        return personRepository.findById(person.getId())
        .flatMap(existingPerson -> {
            existingPerson.setName(person.getName());
            existingPerson.setAge(person.getAge());
            existingPerson.setGender(person.getGender());
            existingPerson.setDateOfBirth(person.getDateOfBirth());
            existingPerson.setBloodType(person.getBloodType());
            return personRepository.save(existingPerson)
                    .doOnNext(updatedPerson -> System.out.println("Person updated: " + updatedPerson));
        })
                .switchIfEmpty(Mono.error(new Exception("Person not found with ID: " + person.getId())));
    }

    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                        Mono.from(connection.createStatement("select * from person ").execute())
                                .doOnNext(result -> System.out.println("Connection successful!"))
                                .doFinally(signalType -> connection.close())        )
                .then();
    }

}
