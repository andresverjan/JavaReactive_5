package api.repository;



import api.model.DetalleCarrito;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface DetalleCarritoRepository extends ReactiveCrudRepository<DetalleCarrito,Long> {
    @Query("SELECT * FROM DetalleCarrito WHERE carrito_id = :carritoId")
    Flux<DetalleCarrito> findByCarritoId(Long CarritoId);

    @Query("SELECT * FROM DetalleCarrito WHERE carrito_id = :carritoId AND product_id = :productId")
    Mono<DetalleCarrito> findByCarritoIdAndProductId(Long carritoId, Long productId);

}
