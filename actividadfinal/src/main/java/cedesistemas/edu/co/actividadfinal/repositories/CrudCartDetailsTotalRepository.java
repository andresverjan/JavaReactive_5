package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.CartDetailsTotal;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CrudCartDetailsTotalRepository extends ReactiveCrudRepository<CartDetailsTotal, Integer> {
    @Query("""
                SELECT
                c.id AS carrito_id,
                p.id AS producto_id,
                p.name AS nombre_producto,
                p.descripcion,
                p.imageUrl,
                cd.cantidad,
                p.precio,
                (cd.cantidad * p.precio) AS total
            FROM 
                ecommerce_schema.carrito c
            JOIN 
                ecommerce_schema.Carrito_Detalles cd ON c.id = cd.carrito_id
            JOIN 
                ecommerce_schema.Productos p ON cd.producto_id = p.id
            WHERE 
                c.id = :carritoId;
            """)
    Flux<CartDetailsTotal> findAllByCarritoId(Integer carritoId);

    @Query("""
            WITH carrito_totales AS (
                SELECT
                    c.id AS carrito_id,
                    p.id AS producto_id,
                    p.name AS nombre_producto,
                    p.descripcion,
                    p.imageUrl,
                    cd.cantidad,
                    p.precio,
                    (cd.cantidad * p.precio) AS total_producto,
                    (cd.cantidad * p.precio) * 1.19 AS total_con_impuestos
                FROM
                    ecommerce_schema.Carrito c
                JOIN
                    ecommerce_schema.Carrito_Detalles cd ON c.id = cd.carrito_id
                JOIN
                    ecommerce_schema.Productos p ON cd.producto_id = p.id
                WHERE
                    c.id = :carritoId
            )
            SELECT
                carrito_id,
                producto_id,
                nombre_producto,
                descripcion,
                imageUrl,
                cantidad,
                precio,
                total_producto,
                total_con_impuestos,
                SUM(total_con_impuestos) OVER (PARTITION BY carrito_id) + 5.00 AS total_general
            FROM
                carrito_totales;
            """)
    Flux<CartDetailsTotal> findAllByCarritoIdWithTotal(Integer carritoId);
}
