package com.artifactory.crud.repository;


import com.artifactory.crud.model.Compra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CompraRepository extends ReactiveCrudRepository<Compra, Long> {
}
