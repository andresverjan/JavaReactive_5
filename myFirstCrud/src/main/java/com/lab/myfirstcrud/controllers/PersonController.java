package com.lab.myfirstcrud.controllers;

import com.lab.myfirstcrud.model.Person;
import com.lab.myfirstcrud.repository.PersonRepository;
import com.lab.myfirstcrud.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Mono<Person> create(@RequestBody Person person) {
        return personService.create(person);
    }
}
