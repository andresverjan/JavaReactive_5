package org.example.finalproject.proveedor;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProveedorProductoRepository extends ReactiveCrudRepository<ProveedorProducto, Integer> {
    Flux<ProveedorProducto> findByProveedorId(Integer proveedorId);
    Mono<ProveedorProducto> findByProductoId(Integer productoId);

}
