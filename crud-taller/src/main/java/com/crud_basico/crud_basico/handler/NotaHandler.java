package com.crud_basico.crud_basico.handler;

import com.crud_basico.crud_basico.model.Nota;
import com.crud_basico.crud_basico.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NotaHandler {

    private final NotaService notaService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(notaService.findAll(), Nota.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return notaService.findById(id)
                .flatMap(nota -> ServerResponse.ok().bodyValue(nota))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Nota.class)
                .flatMap(notaService::save)
                .flatMap(nota -> ServerResponse.ok().bodyValue(nota));
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return notaService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
