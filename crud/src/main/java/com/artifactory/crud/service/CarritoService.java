package com.artifactory.crud.service;

import com.artifactory.crud.model.*;
import com.artifactory.crud.repository.CarritoRepository;
import com.artifactory.crud.repository.DetalleCarritoRepository;
import com.artifactory.crud.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    private static final double IMPUESTO = 0.15; // 15% de impuestos
    private final CarritoRepository carritoRepository;

    private final DetalleCarritoRepository detalleCarritoRepository;

    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository, DetalleCarritoRepository detalleCarritoRepository, ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.productoRepository = productoRepository;
    }

    public Flux<CarritoDetalleRequest> getCarrito(){
        CarritoDetalleRequest carritoDetalleRequest = new CarritoDetalleRequest();
        List<DetalleCarrito> listDetalle = new ArrayList<DetalleCarrito>();
        DetalleCarrito detalleCarrito = new DetalleCarrito();
        return carritoRepository.findAll()
                .flatMap(carrito -> {
                    carritoDetalleRequest.setIdcarrito(carrito.getIdcarrito());
                    carritoDetalleRequest.setFecha(carrito.getFecha_creacion());
                    return detalleCarritoRepository.findById(carrito.getIdcarrito());
                }).flatMap(detalle-> {
                    detalleCarrito.setIdcarrito(detalle.getIdcarrito());
                    detalleCarrito.setCantidad(detalle.getCantidad());
                    detalleCarrito.setIdproducto(detalle.getIdproducto());
                    listDetalle.add(detalle);
                    carritoDetalleRequest.setDetalleCarritos(listDetalle);
                    return Mono.just(carritoDetalleRequest);
                });

    }

    public Mono<Double> getTotalCompraById(Long id){
        TotalVentaResponse totalVentaResponse = new TotalVentaResponse();
        return detalleCarritoRepository.buscarDetalleCarrito(id)
                .flatMap(detalle -> detalleCarritoRepository.findById(detalle.getIddetallecarrito())
                        .flatMap(consultadetalle -> productoRepository.findById(consultadetalle.getIdproducto())
                                    .map(totalproductos -> consultadetalle.getCantidad() * totalproductos.getPrecio())))
                                    .reduce(0.0,Double::sum)
                                    .map(total -> total + (total * IMPUESTO));

    }

    public Mono<Void> deleteProductoDetalleByid(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return detalleCarritoRepository.deleteById(id)
                .doOnNext(carrito -> System.out.println(" Data " + carrito) );
    }

    public Mono<Void> deleteCarritoById(Long id){

        return detalleCarritoRepository.buscarDetalleCarrito(id)
                .collect(Collectors.toList())
                .flatMap(detalles -> {
                    if(detalles.size() > 1 ) {
                        return detalleCarritoRepository.deleteAll(detalles)
                                .then(carritoRepository.deleteById(id));
                    }
                    return Mono.empty();
                });

    }

    public Mono<Carrito> crearCarritoNew(Carrito carrito, List<DetalleCarrito> listDetalleCarrito) {
        return carritoRepository.save(carrito)
                .flatMap(guardarCarrito -> {
                    return Mono.when(
                            listDetalleCarrito.stream()
                                    .map(listadetalle -> {
                                        listadetalle.setIdcarrito(guardarCarrito.getIdcarrito());
                                        System.out.println("detalle :  " + listadetalle.getIdproducto());
                                        return detalleCarritoRepository.save(listadetalle);
                                    }).collect(Collectors.toList())
                    ).thenReturn(guardarCarrito);
               });

    }

    public Mono<Carrito> CrearCarrito(CarritoDetalleRequest carritoDetalleRequest){
        return Flux.fromIterable(carritoDetalleRequest.getDetalleCarritos())
        .flatMap(detalle ->productoRepository.findById(detalle.getIdproducto())
                .flatMap(producto -> {
                    if(producto.getStock() == 0 || producto.getStock() < detalle.getCantidad()){
                        return Mono.error(new RuntimeException("Stock insuficiente para el producto: " + detalle.getIdproducto()));
                    }
                    producto.setStock(producto.getStock() - detalle.getCantidad());
                    return productoRepository.save(producto).thenReturn(detalle);
                }))
                .collect(Collectors.toList())
                .flatMap(detalles -> {
                    Carrito carrito = new Carrito();
                    carrito.setFecha_creacion(carritoDetalleRequest.getFecha());
                    return carritoRepository.save(carrito)
                            .flatMap(guadarDetalle -> {
                                return Mono.when(
                                        carritoDetalleRequest.getDetalleCarritos().stream()
                                                .map(detalleCarrito -> {
                                                    detalleCarrito.setIdcarrito(guadarDetalle.getIdcarrito());
                                                    return detalleCarritoRepository.save(detalleCarrito);
                                                })
                                                .collect(Collectors.toList())
                                ).thenReturn(guadarDetalle);
                            });


                } );

    }

    public Mono<DetalleCarrito> actualizarCompra(DetalleCarrito detalleCarrito) {
        return productoRepository.findById(detalleCarrito.getIdproducto())
                .flatMap(pr -> {
                    if (pr.getStock() < 1) {
                        return Mono.error(new RuntimeException("Stock insuficiente"));
                    }
                    return detalleCarritoRepository.findById(detalleCarrito.getIddetallecarrito())
                            .flatMap(co -> {
                                if( co.getCantidad() > detalleCarrito.getCantidad()) {
                                    pr.setStock(pr.getStock()  +  (co.getCantidad() - detalleCarrito.getCantidad()));
                                }else {
                                    pr.setStock(pr.getStock() - (detalleCarrito.getCantidad() - co.getCantidad()));
                                }
                                return productoRepository.save(pr)
                                        .flatMap(guardarCompra -> {
                                            return detalleCarritoRepository.save(detalleCarrito);
                                        });
                            });
                });
    }
}
