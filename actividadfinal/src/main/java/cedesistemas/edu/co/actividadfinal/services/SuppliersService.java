package cedesistemas.edu.co.actividadfinal.services;

import cedesistemas.edu.co.actividadfinal.interfaces.SuppliersServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.Suppliers;
import cedesistemas.edu.co.actividadfinal.repositories.CrudSuppliersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SuppliersService implements SuppliersServiceInterface {

    private final CrudSuppliersRepository crudSuppliersRepository;

    @Override
    public Mono<Suppliers> saveSupplier(Suppliers suppliers) {
        return crudSuppliersRepository.save(suppliers);
    }

    @Override
    public Flux<Suppliers> getAllSuppliers() {
        return crudSuppliersRepository.findAll()
                .switchIfEmpty(Flux.error(new Exception("No suppliers found")));
    }

    @Override
    public Mono<Suppliers> getSupplierById(Integer id) {
        return crudSuppliersRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Supplier not found with id: " + id)));
    }

    @Override
    public Mono<Suppliers> updateSupplier(Suppliers suppliers) {
        return crudSuppliersRepository.findById(suppliers.getId())
                .switchIfEmpty(Mono.error(new Exception("Supplier not found with id: " + suppliers.getId())))
                .then(crudSuppliersRepository.save(suppliers));
    }

    @Override
    public Mono<Void> deleteSupplier(Integer id) {
        return crudSuppliersRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Supplier not found with id: " + id)))
                .then(crudSuppliersRepository.deleteById(id));
    }
}
