package org.example.controller;

import org.example.model.Person;
import org.example.service.PersonServices;
import org.example.service.PersonServicesInterface;
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
//get personas
//get persona por id
//crear persona
//actualizar persona por id
//delete persona por id

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonServicesInterface personServices;

    public PersonController(PersonServices personService) {
        this.personServices = personService;
    }

    @RequestMapping("")
    public Flux<Person> getPersons() {
        return personServices.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable("id") Long id) {
        System.out.println("Buscando persona con id: " + id);
        return personServices.findById(id);
    }

    @PostMapping
    public Mono<Person> create(@RequestBody Person person) {
        System.out.println("Creando persona" + person.toString());
        return personServices.save(person);
    }

    @PutMapping("")
    public Mono<String> update(@RequestBody Person person) {
        System.out.println("Actualizando persona con id: " + person);
        personServices.findById(person.getId())
                .defaultIfEmpty(new Person())
                .then(personServices.update(person));

        return personServices.update(person);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Long id) {
        System.out.println("Eliminando persona con id: " + id);
        return personServices.deletePersonById(id)
                .doOnError(err -> System.out.println("Error al eliminar persona con id: " + id))
                .then(Mono.empty());
    }
}
