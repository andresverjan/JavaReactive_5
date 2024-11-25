package api.repository;


import api.model.Carrito;
import api.model.Proveedor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CarritoRepository extends ReactiveCrudRepository<Carrito,Long> {
    Mono<Carrito> findByClienteId(Long clienteId);
}
