package com.artifactory.crud.service;

import com.artifactory.crud.model.Producto;
import com.artifactory.crud.model.Proveedor;
import com.artifactory.crud.repository.ProveedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProveedorService {

    private ProveedorRepository proveedorRepository;

    public Flux<Proveedor> getProveedor(){
        return proveedorRepository.findAll()
                .doOnNext(proveedor -> System.out.println(" Data " + proveedor) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

    public Mono<Proveedor> getProveedorById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return proveedorRepository.findById(id)
                .doOnNext(proveedor -> System.out.println(" Data getProductoByid " + proveedor) );
    }

    public Mono<Void> deleteProveedorById(Long id){

        return proveedorRepository.deleteById(id)
                .doOnNext(p-> System.out.println("Borrado :: " + id))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Proveedor> create(Proveedor proveedor){

        return proveedorRepository.save(proveedor)
                .doOnNext(p-> System.out.println("creado :: " + p))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Proveedor> update(Proveedor proveedor){

        return proveedorRepository.findById(proveedor.getIdproveedor())
                .flatMap(existing -> {
                    existing.setNombre(proveedor.getNombre());
                    existing.setCorreo(proveedor.getCorreo());
                    existing.setDireccion(proveedor.getDireccion());
                    existing.setTelefono(proveedor.getTelefono());
                    return proveedorRepository.save(existing)
                            .doOnNext(updatedProveedor -> System.out.println("Producto updated: " + updatedProveedor));
                })
                .switchIfEmpty(Mono.error(new Exception("Producto not found with ID: " + proveedor.getIdproveedor())));
    }
}
