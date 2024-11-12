package com.artifactory.crud.component;

import com.artifactory.crud.model.Person;
import com.artifactory.crud.service.PersonService;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@AllArgsConstructor
public class PersonComponentHandler {

    private final PersonService personService;

    public Mono<ServerResponse> getPersons(ServerRequest request){
    Flux<Person> person = personService.getPerson();
        System.out.println(person);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(person, Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return personService.getPersonById(Long.valueOf(id))
                .flatMap(person -> ServerResponse.ok().bodyValue(person)
                        .switchIfEmpty(ServerResponse.notFound().build())); }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(Person.class)
                .flatMap(personService::create)
                .flatMap(createPerson -> ServerResponse.created(URI.create("/person/" + createPerson.getId()))
                        .bodyValue(createPerson))
                        .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Person.class)
                        .flatMap(personService::update)
                                .flatMap(updatePerson -> ServerResponse.ok().bodyValue(updatePerson))
                                        .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deletePersonByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return personService.deletePersonById(Long.valueOf(id))
                .flatMap(person -> ServerResponse.ok().bodyValue(person)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }

}
