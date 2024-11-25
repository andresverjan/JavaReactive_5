package org.example.service;

import org.example.model.Products;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Products> createProduct(Products producto) {
        return repository.save(producto);
    }

    public Flux<Products> listProductos() {
        return repository.findAll();
    }

    public Mono<Products> getProductPorId(String id) {
        return repository.findById(id);
    }

    public Mono<Products> updateProduct(String id, Products newProduct) {
        return repository.findById(id)
                .flatMap(producto -> {
                    producto.setName(newProduct.getName());
                    producto.setPrice(newProduct.getPrice());
                    producto.setStock(newProduct.getStock());
                    return repository.save(producto);
                });
    }

    public Mono<Void> deleteProduct(String id) {
        return repository.deleteById(id);
    }
}
