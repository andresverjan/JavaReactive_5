package cedesistemas.edu.co.actividadfinal.interfaces;

import cedesistemas.edu.co.actividadfinal.models.CartDetails;
import cedesistemas.edu.co.actividadfinal.models.CartDetailsTotal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartDetailsServiceInterface {
    Mono<CartDetails> addProductToCart(CartDetails cartDetails);

    Mono<Void> deleteProductFromCart(Integer id);

    Mono<CartDetails> updateProductInCart(CartDetails cartDetails);

    Flux<CartDetailsTotal> getAllProductsInCart(Integer carritoId);

    Mono<Void> deleteAllProductsInCart(Integer carritoId);

    Flux<CartDetailsTotal> getProductInCartById(Integer id);
}
