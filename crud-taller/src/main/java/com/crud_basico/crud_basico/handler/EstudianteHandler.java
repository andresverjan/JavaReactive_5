package com.crud_basico.crud_basico.handler;

import com.crud_basico.crud_basico.model.Estudiante;
import com.crud_basico.crud_basico.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EstudianteHandler {

    private final EstudianteService estudianteService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(estudianteService.findAll(), Estudiante.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.findById(id)
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Estudiante.class)
                .flatMap(estudianteService::save)
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante));
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> reporteAprobados(ServerRequest request) {
        return ServerResponse.ok().body(estudianteService.reporteAprobados(), Estudiante.class);
    }

    public Mono<ServerResponse> reporteReprobados(ServerRequest request) {
        return ServerResponse.ok().body(estudianteService.reporteReprobados(), Estudiante.class);
    }

}
