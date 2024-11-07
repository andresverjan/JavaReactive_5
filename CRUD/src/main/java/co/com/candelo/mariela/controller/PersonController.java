package co.com.candelo.mariela.controller;

import co.com.candelo.mariela.interfaces.PersonInterface;
import co.com.candelo.mariela.model.Person;
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
@AllArgsConstructor
@RequestMapping(path = "/crud-reactive")
public class PersonController {

    private final PersonInterface personInterface;

    @PostMapping("/save-person")
    public Mono<Person> savePerson(@RequestBody Person person) {
        return personInterface.savePerson(person);
    }

    @GetMapping("/get-all-persons")
    public Flux<Person> getAllPersons() {
        return personInterface.getAllPersons();
    }

    @GetMapping("/get-person-by-id/{id}")
    public Mono<Person> getPersonById(@PathVariable Integer id) {
        return personInterface.getPersonById(id);
    }

    @PutMapping("/update-person")
    public Mono<Person> updatePerson(@RequestBody Person person) {
        return personInterface.updatePerson(person);
    }

    @DeleteMapping("/delete-person/{id}")
    public Mono<Void> deletePerson(@PathVariable Integer id) {
        return personInterface.deletePerson(id);
    }
}
