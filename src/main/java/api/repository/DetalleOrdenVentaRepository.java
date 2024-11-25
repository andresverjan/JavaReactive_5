package api.repository;


import api.model.DetalleOrdenVenta;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

public interface DetalleOrdenVentaRepository extends ReactiveCrudRepository<DetalleOrdenVenta,Long> {
    Flux<DetalleOrdenVenta> findByOrdenVentaId(Long ordenVentaId);

    // Consulta para obtener los detalles de una orden de venta por rango de fechas
    @Query("SELECT * FROM detalleordenventa WHERE date BETWEEN :inicio AND :fin")
    Flux<DetalleOrdenVenta> findByDateBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
