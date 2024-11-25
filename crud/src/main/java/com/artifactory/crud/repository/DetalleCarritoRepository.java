package com.artifactory.crud.repository;

import com.artifactory.crud.model.DetalleCarrito;
import com.artifactory.crud.model.EstudianteNota;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface DetalleCarritoRepository extends ReactiveCrudRepository<DetalleCarrito, Long> {
    @Query(" Select iddetallecarrito, idcarrito from detallecarrito where idcarrito = :idcarrito ")
    Flux<DetalleCarrito> buscarDetalleCarrito(Long idcarrito);
}
