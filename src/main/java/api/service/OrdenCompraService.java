package api.service;

import api.model.DTO.DetalleOrdenCompraDTO;
import api.model.DetalleOrdenCompra;
import api.model.OrdenCompra;
import api.model.OrdenVenta;
import api.repository.DetalleOrdenCompraRepository;
import api.repository.OrdenCompraRepository;
import api.repository.ProductoRepository;
import api.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenCompraService {
    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleOrdenCompraRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    public OrdenCompraService(OrdenCompraRepository ordenCompraRepository,
                              DetalleOrdenCompraRepository detalleOrdenCompraRepository,
                              ProductoRepository productoRepository,
                              ProveedorRepository proveedorRepository) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.detalleOrdenCompraRepository = detalleOrdenCompraRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public Flux<OrdenCompra> getOrdenes(){
        return ordenCompraRepository.findAll();
    }
    public Mono<OrdenCompra> crearOrdenCompra(Long proveedorId, List<DetalleOrdenCompra> detalles, double totalPrice) {
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setProviderId(proveedorId);
        ordenCompra.setDate(LocalDateTime.now());
        ordenCompra.setTotalPrice(totalPrice);
        ordenCompra.setStatus("Pendiente"); // Establecer estado inicial

        return ordenCompraRepository.save(ordenCompra)
                .flatMap(savedOrden -> {
                    return Flux.fromIterable(detalles)
                            .flatMap(detalle -> {
                                detalle.setOrdenCompraId(savedOrden.getId());
                                return detalleOrdenCompraRepository.save(detalle);
                            })
                            .then(Mono.just(savedOrden));
                });
    }

    public Mono<String> cancelarOrdenCompra(Long ordenCompraId) {
        return ordenCompraRepository.findById(ordenCompraId)
                .flatMap(orden -> {
                    orden.setStatus("Cancelada");
                    return ordenCompraRepository.save(orden)
                            .doOnNext(o -> System.out.println("Orden de compra cancelada: " + o))
                            .then(Mono.just("Orden de compra cancelada con éxito"));
                })
                .switchIfEmpty(Mono.just("Orden de compra no encontrada"));
    }
    public Mono<String> completarOrdenCompra(Long ordenCompraId) {
        return ordenCompraRepository.findById(ordenCompraId)
                .flatMap(orden -> {
                    orden.setStatus("Completada");
                    // Recuperar los detalles de la orden y actualizar el stock
                    return detalleOrdenCompraRepository.findByOrdenCompraId(ordenCompraId)
                            .flatMap(detalle -> {
                                return productoRepository.findById(detalle.getProductId())
                                        .flatMap(producto -> {
                                            producto.setStock(producto.getStock() + detalle.getQuantity());
                                            return productoRepository.save(producto);
                                        });
                            })
                            .then(ordenCompraRepository.save(orden))
                            .then(Mono.just("Orden de compra completada con éxito"));
                })
                .switchIfEmpty(Mono.just("Orden de compra no encontrada"));
    }

    // Listar contenido de una orden de compra
    public Mono<List<DetalleOrdenCompraDTO>> listarContenidoOrdenCompra(Long ordenCompraId) {
        return ordenCompraRepository.findById(ordenCompraId)
                .flatMap(orden ->
                        proveedorRepository.findById(orden.getProviderId())
                                .flatMap(proveedor -> detalleOrdenCompraRepository.findByOrdenCompraId(ordenCompraId)
                                        .flatMap(detalle ->
                                                productoRepository.findById(detalle.getProductId())
                                                        .map(producto -> new DetalleOrdenCompraDTO(
                                                                proveedor.getId(), proveedor.getName(), // Datos del proveedor
                                                                detalle.getProductId(), producto.getName(), // Datos del producto
                                                                detalle.getQuantity(), detalle.getUnitPrice(),
                                                                producto.getStock()))
                                        )
                                        .collectList() // Acumula todos los detalles
                                )
                );
    }
}
