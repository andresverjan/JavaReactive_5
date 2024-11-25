package org.example.repository;

import org.example.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends ReactiveCrudRepository<SalesOrder, String> {

}
