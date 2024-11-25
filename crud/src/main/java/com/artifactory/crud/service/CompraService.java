package com.artifactory.crud.service;

import com.artifactory.crud.model.Compra;
import com.artifactory.crud.model.Producto;
import com.artifactory.crud.repository.CompraRepository;
import com.artifactory.crud.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    private final ProductoRepository productoRepository;

    public CompraService(CompraRepository compraRepository, ProductoRepository productoRepository) {
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
    }

    public Flux<Compra> getCompra(){
        return compraRepository.findAll()
                .doOnNext(compra -> System.out.println(" Data " + compra) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

    public Mono<Compra> getCompraById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return compraRepository.findById(id)
                .doOnNext(compra -> System.out.println(" Data getPersonByid " + compra) );
    }

    public Mono<Producto> deleteCompraById(Long id){

        return compraRepository.findById(id)
                        .flatMap(cancelarCompra -> {
                            cancelarCompra.setEstado(false);
                            return compraRepository.save(cancelarCompra)
                                    .flatMap(actualizar ->{
                                        return productoRepository.findById(cancelarCompra.getIdproducto())
                                                .flatMap(actualizarstock -> {
                                                    if(actualizarstock.getStock() > 0 || actualizarstock.getStock() > cancelarCompra.getCantidad()) {
                                                        actualizarstock.setStock(actualizarstock.getStock() - cancelarCompra.getCantidad());
                                                    }else {
                                                        actualizarstock.setStock(cancelarCompra.getCantidad());
                                                    }
                                                    return productoRepository.save(actualizarstock);
                                                });

                                    });
                        });
    }



    public Mono<Producto> realizarCompra(Compra compra) {
        return compraRepository.save(compra)
                .flatMap(saveCompra -> {
                    if (compra.getCantidad() < 1) {
                        return Mono.error(new RuntimeException("Stock insuficiente"));
                    }
                    return  productoRepository.findById(compra.getIdproducto())
                            .flatMap(producto -> {
                                producto.setStock(producto.getStock() + compra.getCantidad());
                                return productoRepository.save(producto);
                        });
                });
    }

    public Mono<Compra> actualizarCompra(Compra compra) {
        return productoRepository.findById(compra.getIdproducto())
                .flatMap(pr -> {
                    if (compra.getCantidad() < 1) {
                        return Mono.error(new RuntimeException("Stock insuficiente"));
                    }
                    return compraRepository.findById(compra.getIdcompra())
                            .flatMap(co -> {
                                if(pr.getStock() > 0 || pr.getStock() > co.getCantidad()) {
                                    pr.setStock((pr.getStock() - co.getCantidad()) + compra.getCantidad());
                                }else {
                                    pr.setStock(pr.getStock() + compra.getCantidad());
                                }
                                return productoRepository.save(pr)
                                        .flatMap(guardarCompra -> {
                                            return compraRepository.save(compra);
                                        });
                            });
                });
    }
}
