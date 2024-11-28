package com.artifactory.crud.repository;


import com.artifactory.crud.model.Compra;
import com.artifactory.crud.model.Venta;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface VentaRepository extends ReactiveCrudRepository<Venta, Long> {

    Flux<Venta> findByFechaBetween(LocalDate fechaInicial, LocalDate fechaFinal );
    @Query(" select * from ventas where idcliente = :clienteid and fecha between :fechaInicial and :fechaFinal")
    Flux<Venta> findByFechaBetweenCliente(LocalDate fechaInicial, LocalDate fechaFinal ,Long clienteid);
}
