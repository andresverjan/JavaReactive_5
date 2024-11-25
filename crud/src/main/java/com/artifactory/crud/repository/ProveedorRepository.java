package com.artifactory.crud.repository;

import com.artifactory.crud.model.Producto;
import com.artifactory.crud.model.Proveedor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProveedorRepository extends ReactiveCrudRepository<Proveedor, Long> {
}
