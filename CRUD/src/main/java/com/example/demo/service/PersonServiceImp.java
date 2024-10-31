package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonServiceImp implements PersonService{
    private final PersonRepository repository;

    @Override
    public Mono<Person> save(Person person) {
        return repository.save(person);
    }

    @Override
    public Mono<Person> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Person> update(Person person) {
        return repository.update(person);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
