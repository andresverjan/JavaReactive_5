package com.artifactory.crud.repository;


import com.artifactory.crud.model.Compra;
import com.artifactory.crud.model.DetalleCarrito;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;

@Repository
public interface CompraRepository extends ReactiveCrudRepository<Compra, Long> {

    @Query(" select * from compra where estado = true and fecha between :fechaInicial and :fechaFinal")
    Flux<Compra> findByFechaBetween(LocalDate fechaInicial, LocalDate fechaFinal );

    @Query(" select * from compra where estado = true and idproveedor = :idproveedor and fecha between :fechaInicial and :fechaFinal")
    Flux<Compra> findByFechaBetweenByProveedor(LocalDate fechaInicial, LocalDate fechaFinal ,Long idproveedor);
}
