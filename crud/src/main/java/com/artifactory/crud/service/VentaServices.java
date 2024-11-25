package com.artifactory.crud.service;

import com.artifactory.crud.model.Venta;
import com.artifactory.crud.repository.VentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class VentaServices {

    private final VentaRepository ventaRepository;


    public Flux<Venta> getVentas(){
        return ventaRepository.findAll()
                .doOnNext(compra -> System.out.println(" Data " + compra) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

    public Mono<Venta> getVentaById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return ventaRepository.findById(id)
                .doOnNext(compra -> System.out.println(" Data getVentaByid " + compra) );
    }

    public Mono<Void> deleteVentaById(Long id){
        return ventaRepository.deleteById(id)
                .doOnNext(p-> System.out.println("Borrado :: " + id))
                .doOnError(e-> System.out.println(e));
    }



    public Mono<Venta> realizarVenta(Venta venta) {
        return ventaRepository.save(venta)
                .doOnNext(p-> System.out.println("creado :: " + p))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Venta> actualizarVenta(Venta venta) {
        return ventaRepository.findById(venta.getIdcliente())
                .flatMap(existing -> {
                    existing.setIdcarrito(venta.getIdcarrito());
                    existing.setIdcliente(venta.getIdcliente());
                    existing.setTotalventa(venta.getTotalventa());
                    return ventaRepository.save(existing)
                            .doOnNext(updatedVenta -> System.out.println("Venta updated: " + updatedVenta));
                })
                .switchIfEmpty(Mono.error(new Exception("Venta not found with ID: " + venta.getIdventa())));

    }


}
