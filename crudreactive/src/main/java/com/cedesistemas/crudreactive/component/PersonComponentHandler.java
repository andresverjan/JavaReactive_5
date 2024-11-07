package com.cedesistemas.crudreactive.component;

import com.cedesistemas.crudreactive.interfaces.PersonServiceInterface;
import com.cedesistemas.crudreactive.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PersonComponentHandler {

    private final PersonServiceInterface personService;

    public Mono<ServerResponse> getAllPersons(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(personService.getAllPersons(), Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(personService.getPersonById(id), Person.class);
    }

    public Mono<ServerResponse> savePerson(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(personService::savePerson)
                .flatMap(person -> ServerResponse.ok().bodyValue(person));
    }

    public Mono<ServerResponse> updatePerson(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(personService::updatePerson)
                .flatMap(person -> ServerResponse.ok().bodyValue(person));
    }

    public Mono<ServerResponse> deletePerson(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return personService.deletePerson(id)
                .then(ServerResponse.ok().build());
    }
}
