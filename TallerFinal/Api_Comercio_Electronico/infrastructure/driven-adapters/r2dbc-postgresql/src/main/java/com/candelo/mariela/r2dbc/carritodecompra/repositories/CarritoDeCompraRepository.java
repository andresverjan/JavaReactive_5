package com.candelo.mariela.r2dbc.carritodecompra.repositories;

import com.candelo.mariela.r2dbc.carritodecompra.entities.CarritoDeCompraEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CarritoDeCompraRepository extends ReactiveCrudRepository<CarritoDeCompraEntity, UUID> {

    @Query("SELECT * FROM carrito_de_compra WHERE id = :id")
    Mono<CarritoDeCompraEntity> findByUsuarioId(String id);

    @Query("DELETE FROM carrito_de_compra WHERE id = :id")
    Mono<Void> deleteById(String id);

    @Query("UPDATE carrito_de_compra SET total = :total WHERE id = :id")
    Mono<CarritoDeCompraEntity> updateTotalById(String id, Double total);


}
