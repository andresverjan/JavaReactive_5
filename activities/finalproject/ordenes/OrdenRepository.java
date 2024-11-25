package org.example.finalproject.ordenes;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

public interface OrdenRepository extends ReactiveCrudRepository<Orden, Integer> {
    Flux<Orden> findByTipoOrden(String tipoOrden);


}


