package cedesistemas.edu.co.actividadfinal.services;

import cedesistemas.edu.co.actividadfinal.exceptions.NotFoundException;
import cedesistemas.edu.co.actividadfinal.interfaces.ProductServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.Products;
import cedesistemas.edu.co.actividadfinal.repositories.CrudProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface {

    private final CrudProductsRepository crudProductsRepository;

    @Override
    public Mono<Products> saveProduct(Products product) {
        return crudProductsRepository.save(product);
    }

    @Override
    public Mono<Products> updateProduct(Products product) {
        return crudProductsRepository.findById(product.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + product.getId())))
                .then(crudProductsRepository.save(product));
    }

    @Override
    public Flux<Products> getAllProducts() {
        return crudProductsRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No products found")));
    }

    @Override
    public Mono<Products> getProductById(Integer id) {
        return crudProductsRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + id)));
    }

    @Override
    public Mono<Products> getProductByName(String name) {
        return crudProductsRepository.findAll()
                .filter(product -> product.getName().equals(name))
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with name: " + name)));
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        return crudProductsRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + id)))
                .then(crudProductsRepository.deleteById(id));
    }
}
