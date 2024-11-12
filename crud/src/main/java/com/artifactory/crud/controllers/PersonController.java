package com.artifactory.crud.controllers;

import com.artifactory.crud.model.Person;
import com.artifactory.crud.service.PersonService;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;
    private ConnectionFactory connectionFactory;
    //private ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:postgresql://postgres:eQr*5vk4omn;v2m@localhost/postgres?currentSchema=javaReactive");

    @GetMapping()
    public Flux<Person> getPersons(){ return personService.getPerson(); }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id){ return personService.getPersonById(id); }

    @PostMapping
    public Mono<Person> create(@RequestBody Person person){ return personService.create(person); }

    @PutMapping
    public Mono<Person> update(@RequestBody Person person){ return personService.update(person); }

    @DeleteMapping()
    public Mono<Void> delete(@RequestParam Long id){ return personService.deletePersonById(id); }

    @RequestMapping("/testConnection")
    public Mono<Void> testConnection() {

        return personService.testConnection(connectionFactory);
    }
}
