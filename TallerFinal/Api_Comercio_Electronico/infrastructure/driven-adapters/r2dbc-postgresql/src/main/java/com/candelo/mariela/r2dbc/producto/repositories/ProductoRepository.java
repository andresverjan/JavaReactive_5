package com.candelo.mariela.r2dbc.producto.repositories;

import com.candelo.mariela.model.producto.Producto;
import com.candelo.mariela.r2dbc.producto.entities.ProductoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, UUID> {

    @Query("""
            insert into ecommerce.producto ( nombre, descripcion, precio_unitario, stock, categoria)
            values (:nombre, :descripcion, :precio_unitario, :stock, :categoria)""")
    Mono<ProductoEntity> saveProducto( @Param("nombre") String nombre, @Param("descripcion") String descripcion,
                                       @Param("precio_unitario") int precio_unitario, @Param("stock") int stock,
                                       @Param("categoria") String categoria);

    @Query("""
            select * from ecommerce.producto where nombre = :nombre
            """)
    Mono<ProductoEntity> findByNombre(String nombre);

    @Query("""
            update ecommerce.producto set stock = :stock where id = :id
            """)
    Mono<Void> updateStock(@Param("id") UUID id, @Param("stock")  int stock);

    @Query("""
            select * from producto
            
            """)
    Flux<Producto> findAll(Pageable pageable);
}
