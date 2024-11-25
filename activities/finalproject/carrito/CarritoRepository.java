package org.example.finalproject.carrito;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarritoRepository extends ReactiveCrudRepository<Carrito, Integer> {
    Mono<Carrito> findByUsuarioIdAndEstado(Integer usuarioId, String estado);


}
