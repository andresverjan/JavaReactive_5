package api.repository;



import api.model.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<Producto,Long> {
    Mono<Producto> findByName(String name);

    // Consulta para obtener productos por categoría
    @Query("SELECT * FROM producto WHERE categoria = :categoria")
    Flux<Producto> findByCategoria(@Param("categoria") String categoria);

    // Consulta para obtener un producto por su ID
    @Query("SELECT * FROM producto WHERE id = :productId")
    Mono<Producto> findById(@Param("productId") Long productId);
}
