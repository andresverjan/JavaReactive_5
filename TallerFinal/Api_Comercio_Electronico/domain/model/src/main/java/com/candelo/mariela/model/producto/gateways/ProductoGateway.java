package com.candelo.mariela.model.producto.gateways;

import com.candelo.mariela.model.producto.Producto;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


public interface ProductoGateway {

    Mono<Producto> saveProducto(Producto producto);

    Mono<Producto> updateProducto(Producto producto);

    Mono<Void> deleteProductoById(UUID id);

    Mono<Producto> getProductoByNombre(String nombre);

    Mono<Producto> getProductoById(UUID id);

    //Mono<Page<Producto>> getAllProductos();

    Mono<Void> updateStockProducto(UUID id, int stock);

    Mono<List<Producto>> getAllProductos();
}
