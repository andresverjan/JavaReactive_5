package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.ReportTopFive;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReportTopFiveRepository extends ReactiveCrudRepository<ReportTopFive, Integer> {
    @Query("""
            SELECT
                ovd.producto_id,
                p.name AS producto_nombre,
                SUM(ovd.cantidad) AS total_cantidad_vendida,
                SUM(ovd.cantidad * ovd.precio_unitario) AS total_ingresos
            FROM
                ecommerce_schema.Ordenes_Venta ov
            JOIN
                ecommerce_schema.Ordenes_Venta_Detalles ovd ON ov.id = ovd.orden_id
            JOIN
                ecommerce_schema.Productos p ON ovd.producto_id = p.id
            WHERE
                ov.fecha_orden::DATE BETWEEN :fechaInicial::DATE AND :fechaFinal::DATE
            GROUP BY
                ovd.producto_id, p.name
            ORDER BY
                total_cantidad_vendida DESC
            LIMIT 5;
            """)
    Flux<ReportTopFive> findTopFiveByFechaOrdenBetween(String fechaInicial, String fechaFinal);
}
