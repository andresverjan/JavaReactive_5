package com.crudRF.crudRouterF.component.Handler;

import com.crudRF.crudRouterF.model.Materia;
import com.crudRF.crudRouterF.model.Nota;
import com.crudRF.crudRouterF.service.MateriaService;
import com.crudRF.crudRouterF.service.NotaService;
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
public class NotaComponentHandler {
    private final NotaService notaService;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Nota.class)
                .flatMap(notaService::create)
                .flatMap(createdNota ->
                        ServerResponse.created(URI.create("/notas" + createdNota.getId()))
                                .bodyValue(createdNota))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> obtenerEstudiantesAprobadosPorMateria(ServerRequest request) {
        Long materiaId = Long.valueOf(request.pathVariable("materiaId"));

        return notaService.obtenerEstudiantesAprobadosPorMateria(materiaId)
                .collectList()
                .flatMap(estudiantes -> ServerResponse.ok().bodyValue(estudiantes));
    }

    public Mono<ServerResponse> obtenerEstudiantesReprobadosPorMateria(ServerRequest request) {
        Long materiaId = Long.valueOf(request.pathVariable("materiaId"));

        return notaService.obtenerEstudiantesReprobadosPorMateria(materiaId)
                .collectList()
                .flatMap(estudiantes -> ServerResponse.ok().bodyValue(estudiantes));
    }
}
