package org.example.finalproject.carrito;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DetalleCarritoRepository extends ReactiveCrudRepository<DetalleCarrito, Integer> {
    Flux<DetalleCarrito> findByCarritoId(Integer carritoId);

    Mono<Void> deleteAllByCarritoId(Integer carritoId);
    Mono<DetalleCarrito> findByCarritoIdAndProductoId(Integer carritoId, Integer productoId);

    Mono<Object> deleteByCarritoIdAndProductoId(Integer ordenId, Integer productoId);

}
