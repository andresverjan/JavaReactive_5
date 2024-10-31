package co.com.demo.controllers;

import co.com.demo.model.Person;
import co.com.demo.service.PersonService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public Flux<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id) {
        return personService.getPersonsById(id);
    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping()
    public Mono<String> updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id);
    }
}
