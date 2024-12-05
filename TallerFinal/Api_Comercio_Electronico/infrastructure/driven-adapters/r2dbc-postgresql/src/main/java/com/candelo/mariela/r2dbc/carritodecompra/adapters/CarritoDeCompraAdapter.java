package com.candelo.mariela.r2dbc.carritodecompra.adapters;


import com.candelo.mariela.model.carritodecompra.CarritoDeCompra;
import com.candelo.mariela.model.carritodecompra.gateways.CarritoDeCompraGateway;
import com.candelo.mariela.r2dbc.carritodecompra.mappers.CarritoDeCompraMapper;
import com.candelo.mariela.r2dbc.carritodecompra.repositories.CarritoDeCompraRepository;
import com.candelo.mariela.r2dbc.item.mappers.ItemMapper;
import com.candelo.mariela.r2dbc.item.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CarritoDeCompraAdapter implements CarritoDeCompraGateway {

    private final CarritoDeCompraRepository carritoDeCompraRepository;

    @Override
    public Mono<CarritoDeCompra> save(CarritoDeCompra carritoDeCompra) {
        return carritoDeCompraRepository.save(carritoDeCompra)
                .flatMap(savedCarrito -> items
                        .doOnNext(item -> item.setId(savedCarrito.getId()))
                        .flatMap(ItemRepository::save)
                        .then(Mono.just(savedCarrito)))
                .map(CarritoDeCompraMapper::toModel)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error al guardar el carrito de compra", e)));

    }



    @Override
    public Mono<Void> deleteCarritoDeCompraById(UUID id) {
        return carritoDeCompraRepository.deleteById(id);
    }

    @Override
    public Mono<CarritoDeCompra> getCarritoDeCompraById(UUID id) {
        return carritoDeCompraRepository.findById(id)
                .flatMap(carrito -> itemRepository.findByCarritoId(carrito.getId())
                        .map(ItemMapper::toModel)
                        .collectList()
                        .map(items -> {
                            carrito.setItems(items);
                            return CarritoDeCompraMapper.toModel(carrito);
                        }));
    }

    @Override
    public Mono<CarritoDeCompra> updateCarritoDeCompra(CarritoDeCompra carritoDeCompra) {
        return carritoDeCompraRepository.save(carritoDeCompra);
    }


}
