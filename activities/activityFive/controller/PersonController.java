package org.example.controller;

import org.example.model.Person;
import org.example.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @GetMapping
    public Flux<Person> getAllPersons() {
        log.info("Getting all persons");
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id) {
        log.info("Getting person by ID: {}", id);
        return personService.getPersonById(id);

    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        log.info("Creating person: {}", person);
        return personService.createPerson(person);
    }

//    @PutMapping
//    public Mono<Person> updatePerson( @RequestBody Person person) {
//        log.info("Updating person: {}", person);
//        return personService.updatePerson(person);
//    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id);
    }
}