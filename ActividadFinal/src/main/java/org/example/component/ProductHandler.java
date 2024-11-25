package org.example.component;

import org.example.model.Products;
import org.example.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    private final ProductService service;

    public ProductHandler(ProductService service) {
        this.service = service;
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Products.class)
                .flatMap(service::createProduct)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> listProductos(ServerRequest request) {
        return ServerResponse.ok().body(service.listProductos(), Products.class);
    }

    public Mono<ServerResponse> getProductPorId(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.getProductPorId(id)
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Products.class)
                .flatMap(product -> service.updateProduct(id, product))
                .flatMap(productUpdate -> ServerResponse.ok().bodyValue(productUpdate));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.deleteProduct(id)
                .then(ServerResponse.noContent().build());
    }

}