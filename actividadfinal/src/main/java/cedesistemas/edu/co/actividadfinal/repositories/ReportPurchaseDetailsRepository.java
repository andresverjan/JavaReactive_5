package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.ReportPurchaseDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReportPurchaseDetailsRepository extends ReactiveCrudRepository<ReportPurchaseDetails, Integer> {
    @Query("""
            SELECT
                oc.id AS orden_compra_id,
                oc.fecha_orden::DATE AS fecha_compra,
                p.id AS proveedor_id,
                p.nombre AS proveedor_nombre,
                ocd.producto_id,
                prod.name AS producto_nombre,
                ocd.cantidad,
                ocd.precio_unitario,
                (ocd.cantidad * ocd.precio_unitario) AS total
            FROM
                ecommerce_schema.Ordenes_Compra oc
            JOIN
                ecommerce_schema.Ordenes_Compra_Detalles ocd ON oc.id = ocd.orden_id
            JOIN
                ecommerce_schema.Proveedores p ON oc.proveedor_id = p.id
            JOIN
                ecommerce_schema.Productos prod ON ocd.producto_id = prod.id
            WHERE
                oc.fecha_orden::DATE BETWEEN :fechaInicial::DATE AND :fechaFinal::DATE
            ORDER BY
                oc.fecha_orden;
            """)
    Flux<ReportPurchaseDetails> findAllByFechaOrdenBetween(String fechaInicial, String fechaFinal);
}
