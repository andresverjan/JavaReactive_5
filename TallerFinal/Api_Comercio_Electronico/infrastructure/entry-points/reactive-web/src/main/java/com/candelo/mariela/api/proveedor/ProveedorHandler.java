package com.candelo.mariela.api.proveedor;

import com.candelo.mariela.model.proveedor.Proveedor;
import com.candelo.mariela.usecase.proveedor.ProveedorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProveedorHandler {

    private final ProveedorUseCase proveedorUseCase;

    public Mono<ServerResponse> saveProveedor(ServerRequest request) {
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorUseCase::saveProveedor)
                .flatMap(proveedor -> ServerResponse.ok().bodyValue(proveedor));
    }

    public Mono<ServerResponse> getProveedorByDocumentNumber(ServerRequest request) {
        return proveedorUseCase.getProveedorByDocumentNumber((Integer.parseInt(request.pathVariable("documentNumber"))))
                .flatMap(proveedorResponse -> ServerResponse.ok().bodyValue(proveedorResponse))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateProveedor(ServerRequest request) {
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorUseCase::updateProveedor)
                .flatMap(proveedor -> ServerResponse.ok().bodyValue(proveedor));
    }

    public Mono<ServerResponse> deleteProveedorByDocumentNumber(ServerRequest request) {
        return proveedorUseCase.deleteProveedorByDocumentNumber(Integer.parseInt(request.pathVariable("documentNumber")))
                .then(ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }


}
