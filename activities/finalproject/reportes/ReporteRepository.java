package org.example.finalproject.reportes;

import org.example.finalproject.ordenes.Orden;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ReporteRepository extends ReactiveCrudRepository<Orden, Integer> {

    @Query("""
                SELECT
                                                    o.id AS orden_id,
                                                    o.fecha_creacion AS fecha_compra,
                                                    p.id AS proveedor_id,
                                                    p.nombre AS proveedor_name,
                                                    d.producto_id AS producto_id,
                                                    COALESCE(pr.name, pp.name) AS producto_name,
                                                    d.cantidad AS cantidad,
                                                    d.precio_unitario AS precio_unitario,
                                                    (d.cantidad * d.precio_unitario) AS total_producto
                                                FROM
                                                    ordenes o
                                                INNER JOIN detalles_ordenes d ON o.id = d.orden_id
                                                LEFT JOIN productos pr ON d.producto_id = pr.id
                                                LEFT JOIN proveedores_productos pp ON pr.proveedor_producto_id = pp.id
                                                LEFT JOIN proveedores p ON pp.proveedor_id = p.id
                                                WHERE
                                                    o.tipo_orden = 'COMPRA'
                                                    AND o.fecha_creacion BETWEEN :fechaInicio AND :fechaFin
                                                ORDER BY
                                                    o.fecha_creacion;
            """)
    Flux<ReporteCompraDTO> obtenerReporteCompras(LocalDateTime fechaInicio, LocalDateTime fechaFin);


    @Query("""
                SELECT
                    o.id AS orden_id,
                    o.fecha_creacion AS fecha_venta,
                    u.id AS cliente_id,
                    u.nombre AS cliente_name,
                    d.producto_id,
                    COALESCE(pr.name, pp.name) AS producto_name,
                    d.cantidad,
                    d.precio_unitario,
                    (d.cantidad * d.precio_unitario) AS total_producto
                FROM
                    ordenes o
                INNER JOIN detalles_ordenes d ON o.id = d.orden_id
                LEFT JOIN productos pr ON d.producto_id = pr.id
                LEFT JOIN proveedores_productos pp ON pr.proveedor_producto_id = pp.id
                LEFT JOIN usuarios u ON o.cliente_o_proveedor_id = u.id
                WHERE
                    o.tipo_orden = 'VENTA'
                    AND o.fecha_creacion BETWEEN :fechaInicio AND :fechaFin
                ORDER BY
                    o.fecha_creacion;
            """)
    Flux<ReporteVentaDto> obtenerReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("""
        SELECT
            o.id AS orden_id,
            o.fecha_creacion AS fecha_venta,
            u.id AS cliente_id,
            u.nombre AS cliente_nombre,
            d.producto_id AS producto_id,
            p.name AS producto_nombre,
            d.cantidad AS cantidad,
            d.precio_unitario AS precio_unitario,
            (d.cantidad * d.precio_unitario) AS total_producto
        FROM
            ordenes o
        INNER JOIN detalles_ordenes d ON o.id = d.orden_id
        LEFT JOIN productos p ON d.producto_id = p.id
        LEFT JOIN usuarios u ON o.cliente_o_proveedor_id = u.id
        WHERE
            o.tipo_orden = 'VENTA'
            AND o.fecha_creacion BETWEEN :fechaInicio AND :fechaFin
            AND u.id = :clienteId
        ORDER BY
            o.fecha_creacion
    """)
    Flux<ReporteVentasClienteDto> obtenerReporteVentasPorCliente(
            @Param("clienteId") Integer clienteId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}
