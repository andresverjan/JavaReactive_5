package cedesistemas.edu.co.actividadfinal.services;

import cedesistemas.edu.co.actividadfinal.interfaces.SalesOrdersDetailsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.SalesOrdersDetails;
import cedesistemas.edu.co.actividadfinal.repositories.CrudSaleOrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrdersSalesDetailsService implements SalesOrdersDetailsServiceInterface {

    private final CrudSaleOrderDetailsRepository crudSaleOrderDetails;

    @Override
    public Flux<SalesOrdersDetails> saveSalesOrderDetails(Flux<SalesOrdersDetails> salesOrdersDetails) {
        return crudSaleOrderDetails.saveAll(salesOrdersDetails);
    }

    @Override
    public Mono<SalesOrdersDetails> updateSalesOrderDetails(SalesOrdersDetails salesOrdersDetails) {
        return crudSaleOrderDetails.findById(salesOrdersDetails.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Sales Order Details not found")))
                .then(crudSaleOrderDetails.save(salesOrdersDetails));
    }

    @Override
    public Mono<Void> deleteSalesOrderDetails(Integer id) {
        return crudSaleOrderDetails.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Sales Order Details not found")))
                .then(crudSaleOrderDetails.deleteById(id));
    }
}
