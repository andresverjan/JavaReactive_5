package cedesistemas.edu.co.actividadfinal.interfaces;

import cedesistemas.edu.co.actividadfinal.models.Suppliers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SuppliersServiceInterface {
    Mono<Suppliers> saveSupplier(Suppliers suppliers);

    Flux<Suppliers> getAllSuppliers();

    Mono<Suppliers> getSupplierById(Integer id);

    Mono<Suppliers> updateSupplier(Suppliers suppliers);

    Mono<Void> deleteSupplier(Integer id);
}
