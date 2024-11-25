package com.artifactory.crud.repository;


import com.artifactory.crud.model.Venta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VentaRepository extends ReactiveCrudRepository<Venta, Long> {
}
