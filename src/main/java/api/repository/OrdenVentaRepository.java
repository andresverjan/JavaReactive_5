package api.repository;


import api.model.OrdenCompra;
import api.model.OrdenVenta;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta,Long> {
    // Consulta para obtener órdenes de venta dentro de un intervalo de tiempo
    @Query("SELECT * FROM ordenventa WHERE date BETWEEN :inicio AND :fin")
    Flux<OrdenVenta> findByDateBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // Consulta para obtener órdenes de venta de un cliente específico y dentro de un intervalo de tiempo
    @Query("SELECT * FROM ordenventa WHERE cliente_id = :clienteId AND date BETWEEN :inicio AND :fin")
    Flux<OrdenVenta> findByClienteIdAndDateBetween(@Param("clienteId") Long clienteId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
