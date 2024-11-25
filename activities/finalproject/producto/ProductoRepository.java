package org.example.finalproject.producto;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Integer> {
    Flux<Producto> findByNameContainingIgnoreCase(String name);
    Flux<Producto> findByProveedorProductoId(Integer categoriaId);
}
