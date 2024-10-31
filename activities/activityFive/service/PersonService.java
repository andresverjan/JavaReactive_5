package org.example.service;

import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    public Flux<Person> getAllPersons() {
        return personRepository.findAll()
                .doOnNext(person -> System.out.println("Retrieved person: " + person))
                .onErrorResume(e -> {
                    System.err.println("Error fetching persons: " + e.getMessage());
                    return Flux.empty();
                });
    }

    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id)
                .doOnNext(person -> System.out.println("Retrieved person by ID: " + person))
                .onErrorResume(e -> {
                    System.err.println("Error fetching person by ID: " + e.getMessage());
                    return Mono.empty(); // Retorna un `Mono` vacío en caso de error
                });
    }

    public Mono<Person> createPerson(Person person) {
        log.info("Creating person: {}", person);
        return personRepository.save(person);
//                .doOnNext(savedPerson -> System.out.println("Created person: " + savedPerson))
//                .onErrorResume(e -> {
//                    System.err.println("Error creating person: " + e.getMessage());
//                    return Mono.error(new RuntimeException("Failed to create person"));
//                });
    }

//    public Mono<Person> updatePerson(Person person) {
//        if (person.getId() != null) {
//            return personRepository.findById(person.getId())
//                    .flatMap(existingPerson -> {
//                        existingPerson.setName(person.getName());
//                        existingPerson.setAge(person.getAge());
//                        existingPerson.setGender(person.getGender());
//                        existingPerson.setDateOfBirth(person.getDateOfBirth());
//                        existingPerson.setBloodType(person.getBloodType());
//                        return personRepository.save(existingPerson)
//                                .doOnNext(updatedPerson -> System.out.println("Person updated: " + updatedPerson));
//                    })
//                    .switchIfEmpty(Mono.error(new Exception("Person not found with ID: " + person.getId())))
//                    .onErrorResume(e -> {
//                        System.err.println("Error updating person: " + e.getMessage());
//                        return Mono.error(new RuntimeException("Failed to update person"));
//                    });
//        } else {
//            return Mono.error(new Exception("Person ID is missing"));
//        }
//    }

    public Mono<Void> deletePerson(Long id) {
        return personRepository.deleteById(id)
                .doOnSuccess(unused -> System.out.println("Deleted person with ID: " + id))
                .onErrorResume(e -> {
                    System.err.println("Error deleting person: " + e.getMessage());
                    return Mono.error(new RuntimeException("Failed to delete person"));
                });
    }
}