package com.candelo.mariela.usecase.carritodecompra;

import com.candelo.mariela.model.carritodecompra.CarritoDeCompra;
import com.candelo.mariela.model.carritodecompra.gateways.CarritoDeCompraGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class CarritodecompraUseCase {

    private final CarritoDeCompraGateway carritodecompraGateway;

    public Mono<CarritoDeCompra> saveCarritoDeCompra(CarritoDeCompra carritoDeCompra) {
        return carritodecompraGateway.saveCarritoDeCompra(carritoDeCompra);
    }

    public Mono<Void> deleteCarritoDeCompraById(UUID id) {
        return carritodecompraGateway.deleteCarritoDeCompraById(id);
    }

}
