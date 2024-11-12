package com.crud_basico.crud_basico.handler;

import com.crud_basico.crud_basico.model.Materia;
import com.crud_basico.crud_basico.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MateriaHandler {

    private final MateriaService materiaService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(materiaService.findAll(), Materia.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return materiaService.findById(id)
                .flatMap(materia -> ServerResponse.ok().bodyValue(materia))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Materia.class)
                .flatMap(materiaService::save)
                .flatMap(materia -> ServerResponse.ok().bodyValue(materia));
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return materiaService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
