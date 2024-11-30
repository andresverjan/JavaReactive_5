package com.candelo.mariela.api.cliente;

import com.candelo.mariela.model.cliente.Cliente;
import com.candelo.mariela.usecase.cliente.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClienteHandler {

    private final ClienteUseCase clienteUseCase;

    public Mono<ServerResponse> createCliente(ServerRequest request) {
        return request.bodyToMono(Cliente.class)
                .flatMap(clienteUseCase::saveCliente)
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente));

    }

    public Mono<ServerResponse> updateCliente(ServerRequest request) {
        return request.bodyToMono(Cliente.class)
                .flatMap(clienteUseCase::updateCliente)
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente));
    }

    public Mono<ServerResponse> deleteCliente(ServerRequest request) {
        return clienteUseCase.deleteClienteByDocumentNumber(Integer.parseInt(request.pathVariable("documentNumber")))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getClienteByDocumentNumber(ServerRequest request) {
        return clienteUseCase.getClienteByDocumentNumber(Integer.parseInt(request.pathVariable("documentNumber")))
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
