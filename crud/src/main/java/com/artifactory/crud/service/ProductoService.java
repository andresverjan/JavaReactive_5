package com.artifactory.crud.service;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.model.Producto;
import com.artifactory.crud.repository.ClienteRepository;
import com.artifactory.crud.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class ProductoService {
    private ProductoRepository productoRepository;
    public Flux<Producto> getProducto(){
        return productoRepository.findAll()
                .doOnNext(producto -> System.out.println(" Data " + producto) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

    public Mono<Producto> getProductoById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return productoRepository.findById(id)
                .doOnNext(producto -> System.out.println(" Data getProductoByid " + producto) );
    }

    public Mono<Void> deleteClienteById(Long id){

        return productoRepository.deleteById(id)
                .doOnNext(p-> System.out.println("Borrado :: " + id))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Producto> create(Producto producto){

        return productoRepository.save(producto)
                .doOnNext(p-> System.out.println("creado :: " + p))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Producto> update(Producto producto){

        return productoRepository.findById(producto.getIdproducto())
                .flatMap(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setDescripcion(producto.getDescripcion());
                    existing.setPrecio(producto.getPrecio());
                    existing.setStock(producto.getStock());
                    return productoRepository.save(existing)
                            .doOnNext(updatedProducto -> System.out.println("Producto updated: " + updatedProducto));
                })
                .switchIfEmpty(Mono.error(new Exception("Producto not found with ID: " + producto.getIdproducto())));
    }
}
