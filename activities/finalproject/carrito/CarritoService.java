package org.example.finalproject.carrito;

import org.example.finalproject.producto.ProductoRepository;
import org.example.finalproject.proveedor.ProveedorProductoRepository;
import org.example.finalproject.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProveedorProductoRepository proveedorProductoRepository;

    public CarritoService(CarritoRepository carritoRepository,
                          DetalleCarritoRepository detalleCarritoRepository,
                          ProductoRepository productoRepository,
                          UsuarioRepository usuarioRepository, ProveedorProductoRepository proveedorProductoRepository) {
        this.carritoRepository = carritoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.proveedorProductoRepository = proveedorProductoRepository;
    }

    // Crear o verificar carrito
    public Mono<Carrito> verificarOCrearCarrito(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El usuario no existe.")))
                .flatMap(usuario -> carritoRepository.findByUsuarioIdAndEstado(usuarioId, "ACTIVO")
                        .switchIfEmpty(Mono.defer(() -> {
                            Carrito nuevoCarrito = new Carrito();
                            nuevoCarrito.setUsuarioId(usuarioId);
                            nuevoCarrito.setEstado("ACTIVO");
                            nuevoCarrito.setTipo("VENTA");
                            if ("Administrador".equalsIgnoreCase(usuario.getNombre())) {
                                nuevoCarrito.setTipo("COMPRA");
                            }
                            nuevoCarrito.setFechaCreacion(LocalDateTime.now());
                            nuevoCarrito.setFechaActualizacion(LocalDateTime.now());
                            return carritoRepository.save(nuevoCarrito);
                        }))
                );
    }

    // Agregar un producto al carrito
    public Mono<DetalleCarrito> agregarProducto(Integer carritoId, DetalleCarrito detalleCarrito) {
        return carritoRepository.findById(carritoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El carrito no existe.")))
                .flatMap(carrito -> {
                    if ("COMPRA".equalsIgnoreCase(carrito.getTipo())) {
                        // Validar contra proveedores_productos
                        return proveedorProductoRepository.findByProductoId(detalleCarrito.getProductoId())
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no está disponible en los proveedores.")))
                                .flatMap(proveedorProducto -> {
                                    if (proveedorProducto.getStockDisponible() < detalleCarrito.getCantidad()) {
                                        return Mono.error(new IllegalArgumentException("Stock insuficiente en el proveedor."));
                                    }
                                    // Verificar si el producto ya está en el carrito
                                    return detalleCarritoRepository.findByCarritoIdAndProductoId(carritoId, detalleCarrito.getProductoId())
                                            .flatMap(existingDetalle -> {
                                                // Actualizar cantidad si ya existe
                                                existingDetalle.setCantidad(existingDetalle.getCantidad() + detalleCarrito.getCantidad());
                                                existingDetalle.setFechaActualizacion(LocalDateTime.now());
                                                return detalleCarritoRepository.save(existingDetalle);
                                            })
                                            .switchIfEmpty(Mono.defer(() -> {
                                                // Agregar nuevo producto al carrito si no existe
                                                detalleCarrito.setCarritoId(carritoId);
                                                detalleCarrito.setPrecioUnitario(proveedorProducto.getPrice());
                                                detalleCarrito.setName(proveedorProducto.getName());
                                                detalleCarrito.setDescription(proveedorProducto.getDescription());
                                                detalleCarrito.setFechaAgregado(LocalDateTime.now());
                                                detalleCarrito.setFechaActualizacion(LocalDateTime.now());
                                                return detalleCarritoRepository.save(detalleCarrito);
                                            }));
                                });
                    } else if ("VENTA".equalsIgnoreCase(carrito.getTipo())) {
                        // Validar contra productos
                        return productoRepository.findById(detalleCarrito.getProductoId())
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no está disponible en el inventario.")))
                                .flatMap(producto -> {
                                    if (producto.getStock() < detalleCarrito.getCantidad()) {
                                        return Mono.error(new IllegalArgumentException("Stock insuficiente en el inventario."));
                                    }
                                    // Verificar si el producto ya está en el carrito
                                    return detalleCarritoRepository.findByCarritoIdAndProductoId(carritoId, detalleCarrito.getProductoId())
                                            .flatMap(existingDetalle -> {
                                                // Actualizar cantidad si ya existe
                                                existingDetalle.setCantidad(existingDetalle.getCantidad() + detalleCarrito.getCantidad());
                                                existingDetalle.setFechaActualizacion(LocalDateTime.now());
                                                return detalleCarritoRepository.save(existingDetalle);
                                            })
                                            .switchIfEmpty(Mono.defer(() -> {
                                                // Agregar nuevo producto al carrito si no existe
                                                detalleCarrito.setCarritoId(carritoId);
                                                detalleCarrito.setPrecioUnitario(producto.getPrice());
                                                detalleCarrito.setName(producto.getName());
                                                detalleCarrito.setDescription(producto.getDescription());
                                                detalleCarrito.setFechaAgregado(LocalDateTime.now());
                                                detalleCarrito.setFechaActualizacion(LocalDateTime.now());
                                                return detalleCarritoRepository.save(detalleCarrito);
                                            }));
                                });
                    } else {
                        return Mono.error(new IllegalArgumentException("Tipo de carrito no soportado."));
                    }
                });
    }

    public Flux<Carrito> listarCarritos() {
        return carritoRepository.findAll();
    }

    // Obtener el contenido del carrito
    public Flux<DetalleCarrito> obtenerContenido(Integer carritoId) {
        return detalleCarritoRepository.findByCarritoId(carritoId);
    }

    // Actualizar la cantidad de un producto en el carrito
    public Mono<DetalleCarrito> actualizarCantidad(Integer detalleId, Integer nuevaCantidad) {
        return detalleCarritoRepository.findById(detalleId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El detalle del carrito no existe.")))
                .flatMap(detalle -> productoRepository.findById(detalle.getProductoId())
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no existe.")))
                        .flatMap(producto -> {
                            if (producto.getStock() < nuevaCantidad) {
                                return Mono.error(new IllegalArgumentException("Stock insuficiente para el producto."));
                            }
                            detalle.setCantidad(nuevaCantidad);
                            return detalleCarritoRepository.save(detalle);
                        }));
    }

    public Mono<Void> eliminarProducto(Integer detalleId) {
        return detalleCarritoRepository.deleteById(detalleId);
    }

    // Vaciar el carrito
    public Mono<Void> vaciarCarrito(Integer carritoId) {
        return detalleCarritoRepository.findByCarritoId(carritoId)
                .flatMap(detalle -> detalleCarritoRepository.deleteById(detalle.getId()))
                .then();
    }

    // Calcular el total del carrito
    public Mono<String> calcularTotal(Integer carritoId) {
        return detalleCarritoRepository.findByCarritoId(carritoId)
                .map(detalle -> {
                    BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
                    BigDecimal precioUnitario = detalle.getPrecioUnitario();
                    return cantidad.multiply(precioUnitario);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .map(total -> total.setScale(2, RoundingMode.HALF_UP).toString());
    }
}
