package org.example.repository;

import org.example.model.Products;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Products, String> {
    Flux<Products> findByNameContaining(String name);

}
