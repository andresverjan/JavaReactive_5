package com.artifactory.crud.repository;

import com.artifactory.crud.model.Carrito;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CarritoRepository extends ReactiveCrudRepository<Carrito, Long> {
}
