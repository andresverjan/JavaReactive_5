package com.candelo.mariela.model.carritodecompra.gateways;

import com.candelo.mariela.model.carritodecompra.CarritoDeCompra;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CarritoDeCompraGateway {

    Mono<CarritoDeCompra> save(CarritoDeCompra carritoDeCompra);

    Mono<Void> deleteCarritoDeCompraById(UUID id);

    Mono<CarritoDeCompra> getCarritoDeCompraById(UUID id);

    Mono<CarritoDeCompra> updateCarritoDeCompra(CarritoDeCompra carritoDeCompra);

}
