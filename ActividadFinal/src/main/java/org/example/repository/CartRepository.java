package org.example.repository;

import org.example.model.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, String> {

    Mono<Cart> findByClientId(String clientId);
}
