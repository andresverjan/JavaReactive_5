package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.ReportClients;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReportClientsRepository extends ReactiveCrudRepository<ReportClients, Integer> {
    @Query("""
            SELECT
                ov.id AS orden_venta_id,
                ov.fecha_orden::DATE AS fecha_venta,
                ov.usuario_id AS cliente_id,
                ovd.producto_id,
                p.name AS producto_nombre,
                ovd.cantidad,
                ovd.precio_unitario,
                (ovd.cantidad * ovd.precio_unitario) AS total
            FROM
                ecommerce_schema.Ordenes_Venta ov
            JOIN
                ecommerce_schema.Ordenes_Venta_Detalles ovd ON ov.id = ovd.orden_id
            JOIN
                ecommerce_schema.Productos p ON ovd.producto_id = p.id
            WHERE
                ov.usuario_id = :cliente_id AND
                ov.fecha_orden::DATE BETWEEN :fechaInicial::DATE AND :fechaFinal::DATE
            ORDER BY
                ov.fecha_orden;
            """)
    Flux<ReportClients> findAllByClienteIdAndFechaOrdenBetween(Integer cliente_id, String fechaInicial, String fechaFinal);
}
