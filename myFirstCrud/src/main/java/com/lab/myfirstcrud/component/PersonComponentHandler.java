package com.lab.myfirstcrud.component;

import com.lab.myfirstcrud.model.Person;
import com.lab.myfirstcrud.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PersonComponentHandler {

    private final PersonService personService;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(personService::createPerson)
                .flatMap(person -> ServerResponse.ok()
                        .body(Mono.just(person), Person.class)
                );
    }

    public Mono<ServerResponse> getAllPersons(ServerRequest request) {
        Flux<Person> people = personService.getAllPerson();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(people, Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return personService.getPersonById(id)
                .flatMap(person -> ServerResponse.ok()
                        .bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updatePerson(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(personService::updatePerson)
                .flatMap(person -> ServerResponse.ok()
                        .bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletePerson(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return personService.deletePerson(id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("persona borrada con el id: " +id))
                .doOnError(error -> System.err.println(error.getMessage()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


}
