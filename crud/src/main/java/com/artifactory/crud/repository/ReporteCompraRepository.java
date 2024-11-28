package com.artifactory.crud.repository;


import com.artifactory.crud.model.ReporteCompra;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReporteCompraRepository extends ReactiveCrudRepository<ReporteCompra, Long> {

}
