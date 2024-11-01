package com.lab.myfirstcrud.service;

import com.lab.myfirstcrud.model.Person;
import com.lab.myfirstcrud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Mono<Person> create(Person person){
        return personRepository.save(person);
    }

}
