package org.example.finalproject.ordenes;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DetalleOrdenRepository extends ReactiveCrudRepository<DetalleOrden, Integer> {
    Flux<DetalleOrden> findByOrdenId(Integer ordenId);
    Flux<DetalleOrden> findByProductoId(Integer productoId);
    Mono<DetalleOrden> findByOrdenIdAndProductoId(Integer ordenId, Integer productoId);


}
