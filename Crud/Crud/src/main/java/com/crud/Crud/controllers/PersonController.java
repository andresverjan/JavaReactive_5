package com.crud.Crud.controllers;

import com.crud.Crud.model.Person;
import com.crud.Crud.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.monitor.MonitorNotification;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Flux<Person> getPersons(){return personService.getPersons();}

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id){return personService.getPersonById(id);}

    @PostMapping
    public Mono<Person> create(@RequestBody Person p){return personService.create(p);}

    @PutMapping
    public Mono<String> update(@RequestBody Person p){return personService.update(p);}

    @DeleteMapping("/{id}")
    public Mono<Void> deletePersonById(@PathVariable Long id){
        return personService.deletePersonById(id)
            .doOnSubscribe(subscription -> System.out.println("Id to be deleted:" + id))
            .doOnError(err -> System.out.println("Issues deleting the user with id: " + id))
            .then(Mono.empty());
    }
}
