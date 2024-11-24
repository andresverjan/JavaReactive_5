package cedesistemas.edu.co.actividadfinal.interfaces;

import cedesistemas.edu.co.actividadfinal.models.SalesOrdersDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesOrdersDetailsServiceInterface {

    Flux<SalesOrdersDetails> saveSalesOrderDetails(Flux<SalesOrdersDetails> salesOrdersDetails);

    Mono<SalesOrdersDetails> updateSalesOrderDetails(SalesOrdersDetails salesOrdersDetails);

    Mono<Void> deleteSalesOrderDetails(Integer id);
}
