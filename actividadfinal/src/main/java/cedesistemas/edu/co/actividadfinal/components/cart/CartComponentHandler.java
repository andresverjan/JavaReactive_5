package cedesistemas.edu.co.actividadfinal.components.cart;

import cedesistemas.edu.co.actividadfinal.interfaces.CartDetailsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.CartDetails;
import cedesistemas.edu.co.actividadfinal.models.CartDetailsTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartComponentHandler {

    private final CartDetailsServiceInterface cartDetailsServiceInterface;

    public Mono<ServerResponse> addProductToCart(ServerRequest request) {
        return request.bodyToMono(CartDetails.class)
                .flatMap(cartDetailsServiceInterface::addProductToCart)
                .flatMap(cartDetails -> ServerResponse.ok().bodyValue(cartDetails));
    }

    public Mono<ServerResponse> deleteProductFromCart(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return cartDetailsServiceInterface.deleteProductFromCart(id)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> updateProductInCart(ServerRequest request) {
        return request.bodyToMono(CartDetails.class)
                .flatMap(cartDetailsServiceInterface::updateProductInCart)
                .flatMap(cartDetails -> ServerResponse.ok().bodyValue(cartDetails));
    }

    public Mono<ServerResponse> getAllProductsInCart(ServerRequest request) {
        var carritoId = Integer.parseInt(request.pathVariable("carritoId"));
        return ServerResponse.ok().body(cartDetailsServiceInterface.getAllProductsInCart(carritoId), CartDetailsTotal.class);
    }

    public Mono<ServerResponse> deleteAllProductsInCart(ServerRequest request) {
        var carritoId = Integer.parseInt(request.pathVariable("carritoId"));
        return cartDetailsServiceInterface.deleteAllProductsInCart(carritoId)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getProductInCartById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(cartDetailsServiceInterface.getProductInCartById(id), CartDetailsTotal.class);
    }
}
