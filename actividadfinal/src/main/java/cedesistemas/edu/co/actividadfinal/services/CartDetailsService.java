package cedesistemas.edu.co.actividadfinal.services;

import cedesistemas.edu.co.actividadfinal.exceptions.NotFoundException;
import cedesistemas.edu.co.actividadfinal.interfaces.CartDetailsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.CartDetails;
import cedesistemas.edu.co.actividadfinal.models.CartDetailsTotal;
import cedesistemas.edu.co.actividadfinal.repositories.CrudCarDetailsRepository;
import cedesistemas.edu.co.actividadfinal.repositories.CrudCartDetailsTotalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartDetailsService implements CartDetailsServiceInterface {

    private final CrudCarDetailsRepository crudCarDetailsRepository;
    private final CrudCartDetailsTotalRepository crudCartDetailsTotalRepository;

    @Override
    public Mono<CartDetails> addProductToCart(CartDetails cartDetails) {
        return crudCarDetailsRepository.save(cartDetails);
    }

    @Override
    public Mono<Void> deleteProductFromCart(Integer id) {
        return crudCarDetailsRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + id)))
                .then(crudCarDetailsRepository.deleteById(id));
    }

    @Override
    public Mono<CartDetails> updateProductInCart(CartDetails cartDetails) {
        return crudCarDetailsRepository.findById(cartDetails.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + cartDetails.getId())))
                .then(crudCarDetailsRepository.save(cartDetails));
    }

    @Override
    public Flux<CartDetailsTotal> getAllProductsInCart(Integer carritoId) {
        return crudCartDetailsTotalRepository.findAllByCarritoId(carritoId)
                .switchIfEmpty(Flux.error(new NotFoundException("No products found")));
    }

    @Override
    public Mono<Void> deleteAllProductsInCart(Integer carritoId) {
        return crudCarDetailsRepository.deleteAllByCarritoId(carritoId);
    }

    @Override
    public Flux<CartDetailsTotal> getProductInCartById(Integer id) {
        return crudCartDetailsTotalRepository.findAllByCarritoIdWithTotal(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + id)));
    }
}
