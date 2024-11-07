package com.crud_basico.crud_basico.handler;

import com.crud_basico.crud_basico.model.Persona;
import com.crud_basico.crud_basico.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class PersonaHandler {

    private final PersonaService personaService;

    public Mono<ServerResponse> getPersonasR(ServerRequest request) {
        System.out.println("getPersonas RouterFunction");

        return ServerResponse.ok()
                .body(personaService.findAll(), Persona.class);
    }

    public Mono<ServerResponse> getPersonaByIdR(ServerRequest request) {
        System.out.println("getPersonaById RouterFunction");

        Long id = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok()
                .body(personaService.findById(id), Persona.class);
    }

    public Mono<ServerResponse> createPersona(ServerRequest request) {
        System.out.println("createPersona RouterFunction");
        return request.bodyToMono(Persona.class)
                .flatMap(personaService::save)
                .flatMap(personaCreada -> ServerResponse.created(URI.create("/personaR/" + personaCreada.getId()))
                        .bodyValue(personaCreada)
                        .switchIfEmpty(ServerResponse.badRequest().build()));

    }

    public Mono<ServerResponse> deletePersona(ServerRequest request){
        System.out.println("deletePersona RouterFunction");
        var id = Long.valueOf(request.pathVariable("id"));
        return personaService.deletePersonaById(id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(delete -> System.out.println("Persona eliminada con id: "+ id))
                .doOnError(error -> System.out.println("Ocurrió un error eliminando la persona con id: "+ id))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
