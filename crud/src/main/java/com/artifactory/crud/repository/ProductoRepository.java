package com.artifactory.crud.repository;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.model.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
}
