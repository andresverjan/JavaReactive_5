package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonServiceImp service;

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return service.save(person);
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Flux<Person> getAllPersons() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public Mono<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        person.setId(id);
        return service.update(person);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePerson(@PathVariable Long id) {
        return service.deleteById(id);
    }
}
