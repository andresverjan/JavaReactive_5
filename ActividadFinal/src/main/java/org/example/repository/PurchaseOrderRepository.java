package org.example.repository;

import org.example.model.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, String> {
    Flux<PurchaseOrder> findByDateBetween(LocalDate dateInitial, LocalDate dateEnd);

    Flux<PurchaseOrder> findByProveedorIdAndDateBetween(String proveedorId, LocalDate dateInitial, LocalDate dateEnd);
}
