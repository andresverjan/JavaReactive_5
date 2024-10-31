package com.artifactory.crud.service;

import com.artifactory.crud.model.Person;
import com.artifactory.crud.repository.PersonRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Flux<Person> getPerson(){
        return personRepository.findAll()
                .doOnNext(person -> System.out.println(" Data " + person) );
    }

    public Mono<Person> getPersonById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return personRepository.findById(id)
                .doOnNext(person -> System.out.println(" Data getPersonByid " + person) );
    }

    public Mono<Void> deletePersonById(Long id){
        if(id == null){
            return Mono.empty();
        }
        return personRepository.deleteById(id);
    }

    public Mono<Person> create(Person person){

        return personRepository.save(person);
    }

    public Mono<Person> update(Person person){
        return personRepository.save(person);
    }

    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                        Mono.from(connection.createStatement("select * from javaReactiv.operador ").execute())
                                .doOnNext(result -> System.out.println("Connection successful!"))
                                .doFinally(signalType -> connection.close())        )
                .then();
    }

}
