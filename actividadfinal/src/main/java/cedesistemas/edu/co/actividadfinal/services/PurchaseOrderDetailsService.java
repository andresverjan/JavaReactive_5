package cedesistemas.edu.co.actividadfinal.services;

import cedesistemas.edu.co.actividadfinal.exceptions.NotFoundException;
import cedesistemas.edu.co.actividadfinal.interfaces.PurchaseOrderDetailsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.PurchaseOrdersDetails;
import cedesistemas.edu.co.actividadfinal.repositories.CrudPurchaseOrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PurchaseOrderDetailsService implements PurchaseOrderDetailsServiceInterface {

    private final CrudPurchaseOrderDetailsRepository crudPurchaseOrderDetails;

    @Override
    public Mono<PurchaseOrdersDetails> savePurchaseOrderDetails(PurchaseOrdersDetails purchaseOrdersDetails) {
        return crudPurchaseOrderDetails.save(purchaseOrdersDetails);
    }

    @Override
    public Flux<PurchaseOrdersDetails> getPurchaseOrderDetails() {
        return crudPurchaseOrderDetails.findAll()
                .switchIfEmpty(Mono.error(new NotFoundException("Purchase Order Details not found")));
    }

    @Override
    public Mono<PurchaseOrdersDetails> getPurchaseOrderDetailsById(Integer id) {
        return crudPurchaseOrderDetails.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Purchase Order Details not found")));
    }

    @Override
    public Mono<PurchaseOrdersDetails> updatePurchaseOrderDetails(PurchaseOrdersDetails purchaseOrdersDetails) {
        return crudPurchaseOrderDetails.findById(purchaseOrdersDetails.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Purchase Order Details not found")))
                .then(crudPurchaseOrderDetails.save(purchaseOrdersDetails));
    }

    @Override
    public Mono<Void> deletePurchaseOrderDetails(Integer id) {
        return crudPurchaseOrderDetails.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Purchase Order Details not found")))
                .then(crudPurchaseOrderDetails.deleteById(id));
    }
}
