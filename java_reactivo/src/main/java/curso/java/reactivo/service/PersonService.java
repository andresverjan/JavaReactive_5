package curso.java.reactivo.service;

import curso.java.reactivo.model.Person;
import curso.java.reactivo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class PersonService  {
    private final PersonRepository personRepository;
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Flux<Person> getPerson() {
        return personRepository.findAll();
    }

    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Mono<Person> updatePerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> addPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Void> deletePerson(Long id) {
        return personRepository.deleteById(id);
    }
}
