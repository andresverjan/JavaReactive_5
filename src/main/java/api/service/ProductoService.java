package api.service;

import api.model.Producto;
import api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Mono<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Mono<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByName(nombre);
    }

    public Mono<Producto> crear(Producto producto) {
        return productoRepository.save(producto);
    }

    public Mono<String> actualizar(Producto producto) {
        if (producto.getId() != null) {
            return productoRepository.save(producto)
                    .doOnNext(p -> System.out.println("Producto actualizado: " + p))
                    .then(Mono.just("Producto actualizado con éxito"));
        } else {
            return Mono.just("El producto no tiene un ID válido");
        }
    }
    public Mono<Void> eliminar(Long id) {
        return productoRepository.deleteById(id);
    }
    public Mono<String> actualizarStock(Long id, Integer cantidad) {
        return productoRepository.findById(id)
                .flatMap(producto -> {
                    producto.setStock(producto.getStock() + cantidad);
                    return productoRepository.save(producto)
                            .doOnNext(p -> System.out.println("Stock actualizado para el producto: " + p))
                            .then(Mono.just("Stock actualizado con éxito"));
                })
                .switchIfEmpty(Mono.just("Producto no encontrado"));
    }
}
