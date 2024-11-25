package org.example.service;

import org.example.model.Cart;
import org.example.model.ItemCart;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Mono<Cart> addProduct(String clientId, String productId, int quantity) {
        return cartRepository.findByClientId(clientId)
                .defaultIfEmpty(new Cart(null, clientId, new ArrayList<>(), 0.0))
                .flatMap(cart -> productRepository.findById(productId)
                        .flatMap(product -> {
                            ItemCart item = cart.getItems().stream()
                                    .filter(i -> i.getProductId().equals(productId))
                                    .findFirst()
                                    .orElseGet(() -> {
                                        ItemCart nuevoItem = ItemCart.builder()
                                                .productId(productId)
                                                .name(product.getName())
                                                .unitPrice(product.getPrice())
                                                .quantity(0)
                                                .subtotal(0.0)
                                                .build();
                                        cart.getItems().add(nuevoItem);
                                        return nuevoItem;
                                    });

                            item.setQuantity(item.getQuantity() + quantity);
                            item.setSubtotal(item.getQuantity() * item.getUnitPrice());
                            cart.setTotal(cart.getItems().stream().mapToDouble(ItemCart::getSubtotal).sum());

                            return cartRepository.save(cart);
                        })
                );
    }

    public Mono<Cart> deleteProduct(String clientId, String productId) {
        return cartRepository.findByClientId(clientId)
                .flatMap(cart -> {
                    cart.getItems().removeIf(item -> item.getProductId().equals(productId));
                    cart.setTotal(cart.getItems().stream().mapToDouble(ItemCart::getSubtotal).sum());
                    return cartRepository.save(cart);
                });
    }

    public Mono<Cart> emptyCart(String clientId) {
        return cartRepository.findByClientId(clientId)
                .flatMap(cart -> {
                    cart.getItems().clear();
                    cart.setTotal(0.0);
                    return cartRepository.save(cart);
                });
    }

    public Mono<Cart> getCart(String clientId) {
        return cartRepository.findByClientId(clientId);
    }

    public Mono<Double> calculateTotal(String clientId) {
        return cartRepository.findByClientId(clientId)
                .map(Cart::getTotal);
    }
}
