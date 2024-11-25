package org.example.component;

import org.example.service.CartService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CartHandler {

    private final CartService service;

    public CartHandler(CartService service) {
        this.service = service;
    }

    public Mono<ServerResponse> addProduct(ServerRequest request) {
        String clientId = request.pathVariable("clientId");
        String productId = request.pathVariable("productId");
        int quantity = Integer.parseInt(request.pathVariable("quantity"));

        return service.addProduct(clientId, productId, quantity)
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String clientId = request.pathVariable("clientId");
        String productId = request.pathVariable("productId");

        return service.deleteProduct(clientId, productId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> emptyCart(ServerRequest request) {
        String clientId = request.pathVariable("clientId");

        return service.emptyCart(clientId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getCart(ServerRequest request) {
        String clientId = request.pathVariable("clientId");

        return service.getCart(clientId)
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> calculateTotal(ServerRequest request) {
        String clientId = request.pathVariable("clientId");

        return service.calculateTotal(clientId)
                .flatMap(total -> ServerResponse.ok().bodyValue(Map.of("total", total)));
    }

}