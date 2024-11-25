package api.service;

import api.model.*;
import api.model.DTO.DetalleCarritoDTO;
import api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ProductoRepository productoRepository;
    private final OrdenVentaRepository ordenVentaRepository;
    private final DetalleOrdenVentaRepository detalleOrdenVentaRepository;

    public CarritoService(CarritoRepository carritoRepository,
                          DetalleCarritoRepository detalleCarritoRepository,
                          ProductoRepository productoRepository,
                          OrdenVentaRepository ordenVentaRepository,
                          DetalleOrdenVentaRepository detalleOrdenVentaRepository) {
        this.carritoRepository = carritoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.productoRepository = productoRepository;
        this.ordenVentaRepository = ordenVentaRepository;
        this.detalleOrdenVentaRepository = detalleOrdenVentaRepository;
    }

    // Buscar carrito por ID
    public Mono<Carrito> BuscarPorId(Long id) {
        return carritoRepository.findById(id);
    }

    // Listar contenido del carrito
    // Listar contenido del carrito
    public Mono<List<DetalleCarritoDTO>> listarContenidoCarrito(Long carritoId) {
        return detalleCarritoRepository.findByCarritoId(carritoId)
                .flatMap(detalle ->
                        productoRepository.findById(detalle.getProductId())
                                .map(producto -> new DetalleCarritoDTO(
                                        detalle.getProductId(),
                                        producto.getName(),
                                        detalle.getQuantity(),
                                        detalle.getPrice(), // Total en detalle
                                        producto.getStock()))
                )
                .collectList(); // Acumulamos todos los resultados en una lista
    }

    // Agregar producto al carrito
    public Mono<Carrito> agregarProducto(Long carritoId, Long productoId, int cantidad) {
        return productoRepository.findById(productoId)
                .flatMap(producto -> {
                    if (producto.getStock() < cantidad) {
                        return Mono.error(new RuntimeException("Stock insuficiente para el producto"));
                    }
                    DetalleCarrito detalle = new DetalleCarrito();
                    detalle.setCarritoId(carritoId);
                    detalle.setProductId(productoId);
                    detalle.setQuantity(cantidad);
                    detalle.setPrice(producto.getPrice() * cantidad);

                    return detalleCarritoRepository.save(detalle)
                            .then(carritoRepository.findById(carritoId))
                            .flatMap(carrito -> {
                                carrito.setTotal(carrito.getTotal() + detalle.getPrice());
                                return carritoRepository.save(carrito);
                            });
                });
    }

    // Eliminar producto del carrito
    public Mono<Carrito> eliminarProducto(Long carritoId, Long productId) {
        return detalleCarritoRepository.findByCarritoIdAndProductId(carritoId, productId)
                .flatMap(detalle -> detalleCarritoRepository.delete(detalle)
                        .then(carritoRepository.findById(carritoId))
                        .flatMap(carrito -> {
                            carrito.setTotal(carrito.getTotal() - (detalle.getPrice()));  // Actualizar el total del carrito
                            return carritoRepository.save(carrito);
                        }));
    }
    public Mono<Carrito> modificarCantidadProducto(Long carritoId, Long productId, int nuevaCantidad) {
        return detalleCarritoRepository.findByCarritoIdAndProductId(carritoId, productId)
                .flatMap(detalle -> productoRepository.findById(productId)
                        .flatMap(producto -> {
                            // Calcular el nuevo precio total basado en la nueva cantidad
                            double nuevoTotalProducto = producto.getPrice() * nuevaCantidad;

                            // Actualizar el detalle del carrito con la nueva cantidad y el nuevo total
                            detalle.setQuantity(nuevaCantidad);
                            detalle.setPrice(nuevoTotalProducto);

                            return detalleCarritoRepository.save(detalle)
                                    .then(carritoRepository.findById(carritoId))
                                    .flatMap(carrito -> {
                                        // Buscar todos los detalles del carrito para recalcular el total
                                        return detalleCarritoRepository.findByCarritoId(carritoId)
                                                .collectList()  // Recopilamos todos los detalles
                                                .flatMap(detalles -> {
                                                    // Calcular el nuevo total del carrito sumando todos los detalles
                                                    double nuevoTotalCarrito = detalles.stream()
                                                            .mapToDouble(DetalleCarrito::getPrice)
                                                            .sum(); // Sumamos el precio de todos los detalles

                                                    carrito.setTotal(nuevoTotalCarrito); // Actualizamos el total del carrito

                                                    return carritoRepository.save(carrito); // Guardamos el carrito actualizado
                                                });
                                    });
                        }));
    }

    public Mono<Carrito> vaciarCarrito(Long carritoId) {
        return detalleCarritoRepository.findByCarritoId(carritoId)
                .flatMap(detalle -> detalleCarritoRepository.delete(detalle))
                .then(carritoRepository.findById(carritoId))
                .flatMap(carrito -> {
                    carrito.setTotal(0.0);
                    return carritoRepository.save(carrito);
                });
    }

    public Mono<Void> comprarCarrito(Long carritoId) {
        return carritoRepository.findById(carritoId)
                .flatMap(carrito -> {
                    if (carrito.getTotal() == 0.0) {
                        return Mono.error(new RuntimeException("El carrito está vacío"));
                    }

                    // Primero crear la orden de venta y los detalles de la orden
                    return crearOrdenVenta(carrito)
                            .then(vaciarCarrito(carritoId));  // Luego vaciar el carrito
                }).then();
    }
    // Crear la orden de venta
    // Crear la orden de venta y los detalles
    private Mono<Void> crearOrdenVenta(Carrito carrito) {
        OrdenVenta ordenVenta = new OrdenVenta();
        ordenVenta.setClienteId(carrito.getClienteId());
        ordenVenta.setDate(LocalDateTime.now());
        ordenVenta.setTotalPrice(carrito.getTotal());
        ordenVenta.setStatus("Pendiente");  // O "Completada", según tu lógica

        return ordenVentaRepository.save(ordenVenta)
                .flatMap(savedOrdenVenta -> {
                    // Crear los detalles de la orden de venta a partir del carrito
                    return detalleCarritoRepository.findByCarritoId(carrito.getId())
                            .flatMap(detalle -> {
                                DetalleOrdenVenta detalleOrdenVenta = new DetalleOrdenVenta();
                                detalleOrdenVenta.setOrdenVentaId(savedOrdenVenta.getId());
                                detalleOrdenVenta.setProductId(detalle.getProductId());
                                detalleOrdenVenta.setQuantity(detalle.getQuantity());
                                detalleOrdenVenta.setUnitPrice(detalle.getPrice() / detalle.getQuantity());  // Calculamos el precio unitario

                                // Guardar el detalle de la orden de venta
                                return detalleOrdenVentaRepository.save(detalleOrdenVenta)
                                        .then(actualizarStockProducto(detalle.getProductId(), detalle.getQuantity()));
                            })
                            .then(Mono.empty());  // Completar el proceso para todos los detalles del carrito
                });
    }

    // Actualizar el stock de productos
    private Mono<Void> actualizarStockProducto(Long productoId, Integer cantidad) {
        return productoRepository.findById(productoId)
                .flatMap(producto -> {
                    if (producto.getStock() < cantidad) {
                        return Mono.error(new RuntimeException("Stock insuficiente para el producto"));
                    }
                    producto.setStock(producto.getStock() - cantidad);  // Reducir el stock
                    return productoRepository.save(producto).then();
                });
    }
}
