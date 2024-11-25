package com.candelo.mariela.api.producto;

import com.candelo.mariela.model.producto.Producto;
import com.candelo.mariela.usecase.producto.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductoHandler {

    private final ProductoUseCase productoUseCase;

    public Mono<ServerResponse> createProducto(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoUseCase::saveProducto)
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto));

    }

    public Mono<ServerResponse> updateProducto(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoUseCase::updateProducto)
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto));
    }

    public Mono<ServerResponse> deleteProducto(ServerRequest request) {
        return productoUseCase.deleteProductoById(UUID.fromString(request.pathVariable("id")))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getProductoById(ServerRequest request) {
        return productoUseCase.getProductoById(UUID.fromString(request.pathVariable("id")))
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto));
    }

    public Mono<ServerResponse> getAllProductos(ServerRequest request) {
        return ServerResponse.ok().body(productoUseCase.getAllProductos(), Producto.class);
    }

    public Mono<ServerResponse> updateStockProducto(ServerRequest request) {
        return productoUseCase.updateStockProducto(UUID.fromString(request.pathVariable("id")), Integer.parseInt(request.pathVariable("stock")))
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto));
    }

}
