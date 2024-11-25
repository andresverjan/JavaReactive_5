package com.candelo.mariela.usecase.producto;

import com.candelo.mariela.model.producto.Producto;
import com.candelo.mariela.model.producto.gateways.ProductoGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductoUseCase {

    private final ProductoGateway productoGateway;

    public Mono<Producto> saveProducto(Producto producto) {
        var product= productoGateway.saveProducto(producto);
        System.out.println("Producto guardado: " + product);
        return product;
    }

    public Mono<Producto> updateProducto(Producto producto) {
        return productoGateway.updateProducto(producto);
    }

    public Mono<Void> deleteProductoById(UUID id) {
        return productoGateway.deleteProductoById(id);
    }

    public Mono<Producto> getProductoByNombre(String nombre) {
        return productoGateway.getProductoByNombre(nombre);
    }

    public Mono<Producto> getProductoById(UUID id) {
        return productoGateway.getProductoById(id);
    }

    public Mono<List<Producto>> getAllProductos() {
        return productoGateway.getAllProductos();
    }

    public Mono<Void> updateStockProducto(UUID id, int stock) {
        return productoGateway.updateStockProducto(id, stock);
    }
}
