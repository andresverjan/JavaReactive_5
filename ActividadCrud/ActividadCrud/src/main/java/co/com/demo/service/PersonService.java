package co.com.demo.service;

import co.com.demo.model.Person;
import co.com.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Flux<Person> getPersons() {
        return personRepository.findAll()
                .doOnNext(person -> System.out.println("Information Person: " + person));
    }

    public Mono<Person> getPersonsById(Long id) {
        if (id == null) {
            return Mono.empty();
        }
        return personRepository.findById(id)
                .doOnNext(person -> System.out.println("Information Person: " + person));
    }

    public Mono<Person> createPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<String> updatePerson(Person person) {
        if (person.getId() != null) {
            return personRepository.save(person)
                    .doOnNext(person1 -> System.out.println("Information person update: " + person1))
                    .then(Mono.just("Person update"));
        } else {
            return Mono.just("Person no present");
        }
    }

    public Mono<Void> deletePerson(Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Id can not be null"));
        }
        return personRepository.deleteById(id)
                .doOnNext(person -> System.out.println("Information person delete: " + person));
    }
}
