package com.crudRF.crudRouterF.component.Handler;

import com.crudRF.crudRouterF.model.Estudiante;
import com.crudRF.crudRouterF.model.Materia;
import com.crudRF.crudRouterF.repository.NotaRepository;
import com.crudRF.crudRouterF.service.EstudianteService;
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
public class EstudianteComponentHandler {
    private final EstudianteService estudianteService;

    public Mono<ServerResponse> getEstudiantes(ServerRequest request){
        Flux<Estudiante> estudiantes = estudianteService.getEstudiantes();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudiantes,Estudiante.class);
    }

    public Mono<ServerResponse> getEstudianteById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.getEstudianteById(Id)
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Estudiante.class)
                .flatMap(estudianteService::create)
                .flatMap(createdPerson ->
                        ServerResponse.created(URI.create("/estudiantes" + createdPerson.getId()))
                                .bodyValue(createdPerson))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Estudiante.class)
                .flatMap(estudianteService::update)
                .flatMap(updateEstudiante -> ServerResponse.ok().bodyValue(updateEstudiante))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteEstudianteById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.deleteEstudianteById(Id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("ID que será eliminada" + Id))
                .doOnError(err -> System.out.println("Error al eliminar estudiante con Id: " + Id))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
