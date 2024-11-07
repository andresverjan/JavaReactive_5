package co.com.candelo.mariela.interfaces;

import co.com.candelo.mariela.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonInterface {

    Mono<Person> savePerson(Person person);

    Mono<Person> getPersonById(Integer id);

    Flux<Person> getAllPersons();

    Mono<Person> updatePerson(Person person);

    Mono<Void> deletePerson(Integer id);
}
