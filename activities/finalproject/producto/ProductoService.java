package org.example.finalproject.producto;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<Producto> crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Mono<Producto> editarProducto(Integer id, Producto productoActualizado) {
        return productoRepository.findById(id)
                .flatMap(producto -> {
                    producto.setName(productoActualizado.getName());
                    producto.setPrice(productoActualizado.getPrice());
                    producto.setDescription(productoActualizado.getDescription());
                    producto.setImageUrl(productoActualizado.getImageUrl());
                    producto.setStock(productoActualizado.getStock());
                    return productoRepository.save(producto);
                });
    }

    public Flux<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Mono<Producto> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    public Flux<Producto> buscarPorNombre(String name) {
        return productoRepository.findByNameContainingIgnoreCase(name);
    }

    public Mono<Producto> modificarStock(Integer id, Integer nuevoStock) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no existe.")))
                .flatMap(producto -> {
                    producto.setStock(nuevoStock);
                    return productoRepository.save(producto);
                });
    }

    public Mono<Void> eliminarProducto(Integer id) {
        return productoRepository.deleteById(id);
    }
}
