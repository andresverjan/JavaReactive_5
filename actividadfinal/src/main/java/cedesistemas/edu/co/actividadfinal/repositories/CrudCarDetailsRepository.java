package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.CartDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CrudCarDetailsRepository extends ReactiveCrudRepository<CartDetails, Integer> {
    @Query("""
            DELETE FROM ecommerce_schema.carrito_detalles
            WHERE carrito_id = :carritoId
            """)
    Mono<Void> deleteAllByCarritoId(Integer carritoId);
}
