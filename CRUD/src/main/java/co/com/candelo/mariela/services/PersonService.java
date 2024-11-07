package co.com.candelo.mariela.services;

import co.com.candelo.mariela.exceptions.PersonException;
import co.com.candelo.mariela.interfaces.PersonInterface;
import co.com.candelo.mariela.model.Person;
import co.com.candelo.mariela.repositorys.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService implements PersonInterface {

    private final CrudRepository crudRepository;

    @Override
    public Mono<Person> savePerson(Person person) {
        return crudRepository.save(person);
    }

    @Override
    public Mono<Person> getPersonById(Integer id) {
        return crudRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonException("Person not found with id: " + id)));
    }

    @Override
    public Flux<Person> getAllPersons() {
        return crudRepository.findAll();
    }

    @Override
    public Mono<Person> updatePerson(Person person) {
        return crudRepository.findById(person.getId())
                .switchIfEmpty(Mono.error(new PersonException("Person not found with id: " + person.getId())))
                .then(crudRepository.save(person));
    }

    @Override
    public Mono<Void> deletePerson(Integer id) {
        return crudRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonException("Person not found with id: " + id)))
                .then(crudRepository.deleteById(id));
    }
}