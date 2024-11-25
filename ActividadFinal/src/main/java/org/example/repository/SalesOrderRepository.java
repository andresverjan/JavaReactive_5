package org.example.repository;

import org.example.model.SalesOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface SalesOrderRepository extends ReactiveCrudRepository<SalesOrder, String> {

    Flux<SalesOrder> findByDateBetween(LocalDate dateInitial, LocalDate dateEnd);

    Flux<SalesOrder> findByClientIdAndDateBetween(String clientId, LocalDate dateInitial, LocalDate dateEnd);
}
