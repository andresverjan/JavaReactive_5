package cedesistemas.edu.co.actividadfinal.interfaces;

import cedesistemas.edu.co.actividadfinal.models.PurchaseOrdersDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseOrderDetailsServiceInterface {
    Mono<PurchaseOrdersDetails> savePurchaseOrderDetails(PurchaseOrdersDetails purchaseOrdersDetails);

    Flux<PurchaseOrdersDetails> getPurchaseOrderDetails();

    Mono<PurchaseOrdersDetails> getPurchaseOrderDetailsById(Integer id);

    Mono<PurchaseOrdersDetails> updatePurchaseOrderDetails(PurchaseOrdersDetails purchaseOrdersDetails);

    Mono<Void> deletePurchaseOrderDetails(Integer id);
}
