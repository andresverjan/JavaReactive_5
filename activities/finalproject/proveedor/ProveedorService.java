package org.example.finalproject.proveedor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorProductoRepository proveedorProductoRepository;

    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorProductoRepository proveedorProductoRepository) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorProductoRepository = proveedorProductoRepository;
    }

    public Mono<Proveedor> crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Flux<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Mono<Proveedor> actualizarProveedor(Integer id, Proveedor proveedor) {
        return proveedorRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El proveedor no existe.")))
                .flatMap(existing -> {
                    existing.setNombre(proveedor.getNombre());
                    existing.setContacto(proveedor.getContacto());
                    existing.setDisponible(proveedor.getDisponible());
                    return proveedorRepository.save(existing);
                });
    }

    public Mono<Void> eliminarProveedor(Integer id) {
        return proveedorRepository.deleteById(id);
    }

    public Mono<ProveedorProducto> agregarProductoAProveedor(ProveedorProducto proveedorProducto) {
        return proveedorProductoRepository.findByProductoId(proveedorProducto.getProductoId())
                .flatMap(existingProducto -> {
                    // Validar si el producto pertenece al mismo proveedor
                    if (!existingProducto.getProveedorId().equals(proveedorProducto.getProveedorId())) {
                        return Mono.error(new IllegalArgumentException(
                                "El producto ya está asociado a otro proveedor. No se puede agregar."));
                    }

                    // Si pertenece al mismo proveedor, actualizamos el stock
                    existingProducto.setStockDisponible(existingProducto.getStockDisponible() + proveedorProducto.getStockDisponible());
                    existingProducto.setFechaActualizacion(LocalDateTime.now());
                    return proveedorProductoRepository.save(existingProducto);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // Si el producto no existe, lo creamos
                    proveedorProducto.setFechaActualizacion(LocalDateTime.now());
                    return proveedorProductoRepository.save(proveedorProducto);
                }));
    }

    public Flux<ProveedorProducto> listarProductosDeProveedor(Integer proveedorId) {
        return proveedorProductoRepository.findByProveedorId(proveedorId);
    }

    public Flux<ProveedorProducto> listarTodosLosProductosDeProveedores() {
        return proveedorProductoRepository.findAll();
    }
}