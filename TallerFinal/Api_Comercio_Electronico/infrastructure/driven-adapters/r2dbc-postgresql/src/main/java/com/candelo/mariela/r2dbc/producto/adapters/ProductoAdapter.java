package com.candelo.mariela.r2dbc.producto.adapters;

import com.candelo.mariela.model.producto.Producto;
import com.candelo.mariela.model.producto.gateways.ProductoGateway;
import com.candelo.mariela.r2dbc.producto.mappers.ProductoMapper;
import com.candelo.mariela.r2dbc.producto.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductoAdapter implements ProductoGateway {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public Mono<Producto> saveProducto(Producto producto) {
        var productoEntity = ProductoMapper.toProductoEntity(producto);
        return productoRepository.saveProducto(productoEntity.getNombre(), productoEntity.getDescripcion(),
                                                          productoEntity.getPrecioUnitario(), productoEntity.getStock(),
                                                          productoEntity.getCategoria())
                .map(ProductoMapper::toProducto)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al guardar el producto" +  error)));

    }

    @Override
    @Transactional
    public Mono<Producto> updateProducto(Producto producto) {
        var productoEntity = ProductoMapper.toProductoEntity(producto);
        return productoRepository.save(productoEntity)
                .map(ProductoMapper::toProducto)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al actualizar el producto")));
    }

    @Override
    @Transactional
    public Mono<Void> deleteProductoById(UUID id) {
        return productoRepository.deleteById(id)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al eliminar el producto")));
    }

    @Override
    @Transactional
    public Mono<Void> updateStockProducto(UUID id, int stock) {
        return productoRepository.updateStock(id, stock)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al actualizar el stock del producto")));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Producto> getProductoById(UUID id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toProducto)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al obtener el producto")));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Producto> getProductoByNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
                .map(ProductoMapper::toProducto)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al obtener el producto")));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<List<Producto>> getAllProductos() {
        return productoRepository.findAll()
                .map(ProductoMapper::toProducto)
                .collectList();

    }

}
