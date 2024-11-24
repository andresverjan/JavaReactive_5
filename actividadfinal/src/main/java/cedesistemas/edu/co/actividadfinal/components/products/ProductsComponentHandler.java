package cedesistemas.edu.co.actividadfinal.components.products;

import cedesistemas.edu.co.actividadfinal.interfaces.ProductServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductsComponentHandler {

    private final ProductServiceInterface productServiceInterface;

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productServiceInterface.getAllProducts(), Products.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(productServiceInterface.getProductById(id), Products.class);
    }

    public Mono<ServerResponse> getProductByName(ServerRequest request) {
        var name = request.pathVariable("name");
        return ServerResponse.ok().body(productServiceInterface.getProductByName(name), Products.class);
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        return request.bodyToMono(Products.class)
                .flatMap(productServiceInterface::saveProduct)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        return request.bodyToMono(Products.class)
                .flatMap(productServiceInterface::updateProduct)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return productServiceInterface.deleteProduct(id)
                .then(ServerResponse.ok().build());
    }
}
