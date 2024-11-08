package curso.java.reactivo.router_function.person;

import curso.java.reactivo.model.Person;
import curso.java.reactivo.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class PersonHandler {
    private final PersonService personService;
    public Mono<ServerResponse> getPerson(ServerRequest request) {
        Flux<Person> personFlux = personService.getPerson();
        return ServerResponse.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(personFlux, Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return personService.getPersonById(id)
                .flatMap(person -> ServerResponse.ok().body(Mono.just(person), Person.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addPerson(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(personService::addPerson)
                .flatMap(savedPerson -> ServerResponse.ok().body(Mono.just(savedPerson), Person.class));
    }

    public Mono<ServerResponse> updatePerson(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return personService.getPersonById(id)
                .flatMap(existingPerson -> request.bodyToMono(Person.class)
                        .map(person -> {
                            if (person.getName() != null) {
                                existingPerson.setName(person.getName());
                            }
                            if (person.getAge() != null) {
                                existingPerson.setAge(person.getAge());
                            }
                            if (person.getGender() != null) {
                                existingPerson.setGender(person.getGender());
                            }
                            if (person.getDateOfBirth() != null) {
                                existingPerson.setDateOfBirth(person.getDateOfBirth());
                            }
                            if (person.getBloodType() != null) {
                                existingPerson.setBloodType(person.getBloodType());
                            }
                            return existingPerson;
                        })
                        .flatMap(personService::updatePerson)
                        .flatMap(updatedPerson -> ServerResponse.ok().body(Mono.just(updatedPerson), Person.class))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletePerson(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return personService.getPersonById(id)
                .flatMap(person -> personService.deletePerson(id)
                        .then(ServerResponse.noContent().build())
                );
    }
}
