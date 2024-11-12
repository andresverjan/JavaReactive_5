package com.crudRF.crudRouterF.component.Handler;

import com.crudRF.crudRouterF.model.Estudiante;
import com.crudRF.crudRouterF.model.Materia;
import com.crudRF.crudRouterF.service.MateriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@AllArgsConstructor
public class MateriaComponentHandler {
    private final MateriaService materiaService;

    public Mono<ServerResponse> getMaterias(ServerRequest request){
        Flux<Materia> materias = materiaService.getMaterias();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(materias,Materia.class);
    }

    public Mono<ServerResponse> getMateriaById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return materiaService.getMateriaById(Id)
                .flatMap(materia -> ServerResponse.ok().bodyValue(materia))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Materia.class)
                .flatMap(materiaService::create)
                .flatMap(createdPerson ->
                        ServerResponse.created(URI.create("/materias" + createdPerson.getId()))
                                .bodyValue(createdPerson))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Materia.class)
                .flatMap(materiaService::update)
                .flatMap(updateMateria -> ServerResponse.ok().bodyValue(updateMateria))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteMateriaById(ServerRequest request) {
        Long Id = Long.valueOf(request.pathVariable("id"));
        return materiaService.deleteMateriaById(Id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("ID que será eliminada" + Id))
                .doOnError(err -> System.out.println("Error al eliminar materia con Id: " + Id))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
