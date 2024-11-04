package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonServices implements PersonServicesInterface {
    private final PersonRepository repository;

    @Override
    public Mono<Person> save(Person person) {
        return repository.save(person);
    }

    @Override
    public Mono<Person> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Person> findAll() {
        System.out.println("Obteniendo personas");
        return repository.findAll();
    }

    @Override
    public Mono<String> update(Person person) {
        return repository.findById(person.getId())
                .then(repository.save(person))
                .map(p -> "Persona actualizada: " + p.toString())
                .onErrorReturn("Error al actualizar persona");
    }

    @Override
    public Mono<Void> deletePersonById(Long id) {
        return repository.deleteById(id).doOnError(e -> System.out.println("Error al eliminar persona: " + e.getMessage()));
    }

}
