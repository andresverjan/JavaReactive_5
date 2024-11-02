package com.lab.myfirstcrud.controllers;

import com.lab.myfirstcrud.model.Person;
import com.lab.myfirstcrud.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/create")
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping
    public Flux<Person> getPersonas() {
        return personService.getAllPerson();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPerson(@PathVariable Long id) {
        return personService.getPersonById(id)
                .doOnSubscribe(subscription -> System.out.println("persona encontrada con el ID: " +id))
                .doOnError(error -> System.err.println("Persona no encontrada con el ID: " + id));
    }

    @PutMapping("/update")
    public Mono<Person> updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Person> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id)
                .doOnSubscribe(subscription -> System.out.println("persona borrada con el id: " +id))
                .doOnError(error -> System.err.println("Persona no encontrada con el ID: " + id))
                .then(Mono.empty());
    }
}
