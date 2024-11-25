package api.repository;


import api.model.DetalleOrdenCompra;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

public interface DetalleOrdenCompraRepository extends ReactiveCrudRepository<DetalleOrdenCompra,Long> {

    @Query("SELECT * FROM detalleordencompra WHERE orden_compra_id = :ordenCompraId")
    Flux<DetalleOrdenCompra> findByOrdenCompraId(@Param("ordenCompraId") Long ordenCompraId);

    // Consulta para obtener los detalles de la orden de compra por rango de fechas
    @Query("SELECT * FROM detalleordencompra WHERE date BETWEEN :inicio AND :fin")
    Flux<DetalleOrdenCompra> findByDateBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
