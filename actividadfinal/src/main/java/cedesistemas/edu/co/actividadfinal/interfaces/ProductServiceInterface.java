package cedesistemas.edu.co.actividadfinal.interfaces;

import cedesistemas.edu.co.actividadfinal.models.Products;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductServiceInterface {
    Mono<Products> saveProduct(Products product);

    Mono<Products> updateProduct(Products product);

    Flux<Products> getAllProducts();

    Mono<Products> getProductById(Integer id);

    Mono<Products> getProductByName(String name);

    Mono<Void> deleteProduct(Integer id);
}
