package api.repository;


import api.model.OrdenCompra;
import api.model.Proveedor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra,Long> {
    // Consulta para obtener órdenes de compra dentro de un intervalo de tiempo
    @Query("SELECT * FROM ordencompra WHERE date BETWEEN :inicio AND :fin")
    Flux<OrdenCompra> findByDateBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // Consulta para obtener órdenes de compra por proveedor y dentro de un intervalo de tiempo
    @Query("SELECT * FROM ordencompra WHERE provider_id = :proveedorId AND date BETWEEN :inicio AND :fin")
    Flux<OrdenCompra> findByDateBetweenAndProveedorId(@Param("proveedorId") Long proveedorId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
