package com.lab.myfirstcrud.service;

import com.lab.myfirstcrud.model.Person;
import com.lab.myfirstcrud.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Mono<Person> createPerson(Person person){
        return personRepository.save(person)
                .doOnNext(p -> System.out.println("Persona guardada: "+ p));
    }

    public Flux<Person> getAllPerson(){
        return personRepository.findAll()
                .doOnNext(p -> System.out.println("Lista de personas: "+ p));
    }

    public Mono<Person> getPersonById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("Persona no encontrada con id: " + id));
        }
        return personRepository.findById(id)
                .doOnNext(p -> System.out.println("Persona Encontrada: "+ p));
    }

    public Mono<Person> updatePerson(Person person){
        if(person.getId() != null){
            return personRepository.save(person)
                    .doOnNext(p -> System.out.println("Persona actualizada: "+ p));
        }else{
            return Mono.error(new IllegalArgumentException("Persona no encontrada con id: " + person.getId()));

        }

    }

    public Mono<Void> deletePerson(Long id){
        if(id == null){
            return Mono.error(new NullPointerException("el id no puede ser nulo: " + id));
        }
        return personRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Persona eliminada: "+ p + " id: " + id));
    }
}
