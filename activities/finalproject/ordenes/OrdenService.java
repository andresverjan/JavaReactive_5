package org.example.finalproject.ordenes;

import org.example.finalproject.carrito.CarritoRepository;
import org.example.finalproject.carrito.DetalleCarrito;
import org.example.finalproject.carrito.DetalleCarritoRepository;
import org.example.finalproject.producto.Producto;
import org.example.finalproject.producto.ProductoRepository;
import org.example.finalproject.proveedor.ProveedorProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final DetalleOrdenRepository detalleOrdenRepository;
    private final ProductoRepository productoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ProveedorProductoRepository proveedorProductoRepository;
    private final CarritoRepository carritoRepository;

    public OrdenService(OrdenRepository ordenRepository,
                        DetalleOrdenRepository detalleOrdenRepository,
                        ProductoRepository productoRepository,
                        DetalleCarritoRepository detalleCarritoRepository, ProveedorProductoRepository proveedorProductoRepository,
                        CarritoRepository carritoRepository) {
        this.ordenRepository = ordenRepository;
        this.detalleOrdenRepository = detalleOrdenRepository;
        this.productoRepository = productoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.proveedorProductoRepository = proveedorProductoRepository;
        this.carritoRepository = carritoRepository;
    }


    public Mono<Orden> crearOrdenVentaDesdeCarrito(Integer carritoId) {
        return carritoRepository.findById(carritoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El carrito no existe.")))
                .flatMap(carrito -> {
                    if (!"VENTA".equalsIgnoreCase(carrito.getTipo())) {
                        return Mono.error(new IllegalArgumentException("El carrito no está configurado para ventas."));
                    }

                    return detalleCarritoRepository.findByCarritoId(carritoId)
                            .collectList()
                            .flatMap(detallesCarrito -> {
                                if (detallesCarrito.isEmpty()) {
                                    return Mono.error(new IllegalArgumentException("El carrito está vacío."));
                                }

                                // Calcular el total
                                BigDecimal total = detallesCarrito.stream()
                                        .map(detalle -> BigDecimal.valueOf(detalle.getCantidad()).multiply(detalle.getPrecioUnitario()))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                // Crear la orden
                                Orden nuevaOrden = new Orden();
                                nuevaOrden.setTipoOrden("VENTA");
                                nuevaOrden.setClienteOProveedorId(carrito.getUsuarioId());
                                nuevaOrden.setTotal(total);
                                nuevaOrden.setEstado("TEMPORAL");
                                nuevaOrden.setFechaCreacion(LocalDateTime.now());
                                nuevaOrden.setFechaActualizacion(LocalDateTime.now());

                                return ordenRepository.save(nuevaOrden)
                                        .flatMap(ordenGuardada -> Flux.fromIterable(detallesCarrito)
                                                .flatMap(detalle -> {
                                                    DetalleOrden detalleOrden = new DetalleOrden();
                                                    detalleOrden.setOrdenId(ordenGuardada.getId());
                                                    detalleOrden.setProductoId(detalle.getProductoId());
                                                    detalleOrden.setCantidad(detalle.getCantidad());
                                                    detalleOrden.setPrecioUnitario(detalle.getPrecioUnitario());
                                                    detalleOrden.setFechaDetalle(LocalDateTime.now());
                                                    detalleOrden.setFechaActualizacion(LocalDateTime.now());
                                                    return detalleOrdenRepository.save(detalleOrden);
                                                }).then(Mono.just(ordenGuardada)));
                            });
                });
    }

    // Editar la cantidad de un producto en una orden de venta
    public Mono<Map<String, Object>> editarCantidadProducto(Integer ordenId, Integer productoId, Integer nuevaCantidad) {
        return detalleOrdenRepository.findByOrdenIdAndProductoId(ordenId, productoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no existe en esta orden.")))
                .flatMap(detalle -> {
                    if (nuevaCantidad <= 0) {
                        // Eliminar el producto de la orden y del carrito
                        return detalleOrdenRepository.deleteById(detalle.getId())
                                .then(detalleCarritoRepository.deleteByCarritoIdAndProductoId(ordenId, productoId))
                                .then(recalcularTotalYDetalles(ordenId));
                    }

                    // Actualizar la cantidad en el detalle de la orden
                    detalle.setCantidad(nuevaCantidad);
                    detalle.setFechaActualizacion(LocalDateTime.now());

                    return detalleOrdenRepository.save(detalle)
                            .flatMap(updatedDetalle -> {
                                // Sincronizar el carrito
                                return detalleCarritoRepository.findByCarritoIdAndProductoId(ordenId, productoId)
                                        .flatMap(detalleCarrito -> {
                                            detalleCarrito.setCantidad(nuevaCantidad);
                                            detalleCarrito.setFechaActualizacion(LocalDateTime.now());
                                            return detalleCarritoRepository.save(detalleCarrito);
                                        })
                                        .then(recalcularTotalYDetalles(ordenId)); // Recalcular el total y devolver todos los detalles
                            });
                });
    }

    // Método para recalcular el total y devolver todos los detalles
    private Mono<Map<String, Object>> recalcularTotalYDetalles(Integer ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId)
                .collectList()
                .flatMap(detalles -> {
                    BigDecimal total = detalles.stream()
                            .map(d -> BigDecimal.valueOf(d.getCantidad()).multiply(d.getPrecioUnitario()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Devolver detalles y total
                    Map<String, Object> result = new HashMap<>();
                    result.put("detalles", detalles);
                    result.put("total", total);

                    return ordenRepository.findById(ordenId)
                            .flatMap(orden -> {
                                orden.setTotal(total);
                                orden.setFechaActualizacion(LocalDateTime.now());
                                return ordenRepository.save(orden);
                            }).thenReturn(result);
                });
    }

    // Confirmar una orden de venta
    public Mono<Map<String, Object>> confirmarOrdenVenta(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La orden no existe.")))
                .flatMap(orden -> {
                    if (!"TEMPORAL".equalsIgnoreCase(orden.getEstado())) {
                        return Mono.error(new IllegalArgumentException("La orden ya fue confirmada o no está en estado TEMPORAL."));
                    }

                    return detalleOrdenRepository.findByOrdenId(ordenId)
                            .flatMap(detalle -> {
                                return productoRepository.findById(detalle.getProductoId())
                                        .flatMap(producto -> {
                                            if (producto.getStock() < detalle.getCantidad()) {
                                                return Mono.error(new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getName()));
                                            }
                                            producto.setStock(producto.getStock() - detalle.getCantidad());
                                            producto.setFechaActualizacion(LocalDateTime.now());
                                            return productoRepository.save(producto);
                                        });
                            })
                            .then(Mono.defer(() -> {
                                // Cambiar el estado de la orden a CONFIRMADA
                                orden.setEstado("CONFIRMADA");
                                orden.setFechaActualizacion(LocalDateTime.now());
                                return ordenRepository.save(orden);
                            }))
                            // Guardar detalles del carrito en la tabla de detalles de orden
                            .then(detalleCarritoRepository.findByCarritoId(ordenId)
                                    .flatMap(detalleCarrito -> {
                                        DetalleOrden detalleOrden = new DetalleOrden();
                                        detalleOrden.setOrdenId(ordenId);
                                        detalleOrden.setProductoId(detalleCarrito.getProductoId());
                                        detalleOrden.setCantidad(detalleCarrito.getCantidad());
                                        detalleOrden.setPrecioUnitario(detalleCarrito.getPrecioUnitario());
                                        detalleOrden.setFechaDetalle(LocalDateTime.now());
                                        detalleOrden.setFechaActualizacion(LocalDateTime.now());
                                        return detalleOrdenRepository.save(detalleOrden);
                                    })
                                    .then(detalleCarritoRepository.deleteAllByCarritoId(ordenId)))
                            .then(detalleOrdenRepository.findByOrdenId(ordenId).collectList())
                            .flatMap(detalles -> {
                                BigDecimal total = detalles.stream()
                                        .map(d -> BigDecimal.valueOf(d.getCantidad()).multiply(d.getPrecioUnitario()))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                // Preparar el resultado
                                Map<String, Object> result = new HashMap<>();
                                result.put("orden", orden);
                                result.put("productos", detalles);
                                result.put("total", total);
                                return Mono.just(result);
                            });
                });
    }

    public Flux<DetalleOrden> listarDetallesPorOrdenId(Integer ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId)
                .switchIfEmpty(Flux.error(new IllegalArgumentException("La orden no tiene detalles o no existe.")));
    }


    public Mono<Orden> registrarCompraDesdeCarrito(Integer carritoId) {
        return carritoRepository.findById(carritoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El carrito no existe.")))
                .flatMap(carrito -> {
                    if (!"COMPRA".equalsIgnoreCase(carrito.getTipo())) {
                        return Mono.error(new IllegalArgumentException("El carrito no está configurado para compras."));
                    }

                    if (carrito.getUsuarioId() == null) {
                        return Mono.error(new IllegalArgumentException("El carrito no tiene un usuario asociado."));
                    }

                    return detalleCarritoRepository.findByCarritoId(carritoId)
                            .collectList()
                            .flatMap(detallesCarrito -> {
                                if (detallesCarrito.isEmpty()) {
                                    return Mono.error(new IllegalArgumentException("El carrito está vacío."));
                                }

                                // Calcular el total
                                BigDecimal total = detallesCarrito.stream()
                                        .map(detalle -> BigDecimal.valueOf(detalle.getCantidad())
                                                .multiply(detalle.getPrecioUnitario()))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                System.out.println("Total Calculado: " + total);

                                // Crear nueva orden
                                Orden nuevaOrden = new Orden();
                                nuevaOrden.setTipoOrden("COMPRA");
                                nuevaOrden.setClienteOProveedorId(carrito.getUsuarioId());
                                nuevaOrden.setTotal(total.setScale(2, RoundingMode.HALF_UP));
                                nuevaOrden.setEstado("CREADA");
                                nuevaOrden.setFechaCreacion(LocalDateTime.now());
                                nuevaOrden.setFechaActualizacion(LocalDateTime.now());

                                System.out.println("Nueva Orden: " + nuevaOrden);

                                return ordenRepository.save(nuevaOrden)
                                        .flatMap(ordenGuardada -> procesarDetallesCompra(detallesCarrito)
                                                .then(detalleCarritoRepository.deleteAllByCarritoId(carritoId))
                                                .then(Mono.just(ordenGuardada)));
                            });
                });
    }
    private Mono<Void> procesarDetallesCompra(List<DetalleCarrito> detallesCarrito) {
        return Flux.fromIterable(detallesCarrito)
                .flatMap(detalle -> productoRepository.findByProveedorProductoId(detalle.getProductoId())
                        .flatMap(producto -> {
                            // Actualizar stock si el producto existe
                            producto.setStock(producto.getStock() + detalle.getCantidad());
                            producto.setFechaActualizacion(LocalDateTime.now());
                            return productoRepository.save(producto);
                        })
                        .switchIfEmpty(proveedorProductoRepository.findByProductoId(detalle.getProductoId())
                                .flatMap(proveedorProducto -> {
                                    // Crear nuevo producto con datos de proveedores_productos
                                    Producto nuevoProducto = new Producto();
                                    nuevoProducto.setProveedorProductoId(proveedorProducto.getProductoId());
                                    nuevoProducto.setName(proveedorProducto.getName());
                                    nuevoProducto.setDescription(proveedorProducto.getDescription());
                                    nuevoProducto.setPrice(proveedorProducto.getPrice());
                                    nuevoProducto.setStock(detalle.getCantidad());
                                    nuevoProducto.setFechaActualizacion(LocalDateTime.now());
                                    return productoRepository.save(nuevoProducto);
                                })
                                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                                        "El producto con ID " + detalle.getProductoId() + " no está disponible en proveedores."))))
                ).then();
    }



    public Mono<Orden> confirmarOrdenCompra(Integer carritoId) {
        return carritoRepository.findById(carritoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El carrito no existe.")))
                .flatMap(carrito -> {
                    if (!"COMPRA".equalsIgnoreCase(carrito.getTipo())) {
                        return Mono.error(new IllegalArgumentException("El carrito no está configurado para compras."));
                    }

                    return detalleCarritoRepository.findByCarritoId(carritoId)
                            .collectList()
                            .flatMap(detallesCarrito -> {
                                if (detallesCarrito.isEmpty()) {
                                    return Mono.error(new IllegalArgumentException("El carrito está vacío."));
                                }

                                // Calcular el total
                                BigDecimal total = detallesCarrito.stream()
                                        .map(detalle -> BigDecimal.valueOf(detalle.getCantidad())
                                                .multiply(detalle.getPrecioUnitario()))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                // Crear una nueva orden
                                Orden nuevaOrden = new Orden();
                                nuevaOrden.setTipoOrden("COMPRA");
                                nuevaOrden.setClienteOProveedorId(3);
                                nuevaOrden.setTotal(total);
                                nuevaOrden.setEstado("CREADA");
                                nuevaOrden.setFechaCreacion(LocalDateTime.now());
                                nuevaOrden.setFechaActualizacion(LocalDateTime.now());

                                return ordenRepository.save(nuevaOrden)
                                        .flatMap(ordenGuardada -> Flux.fromIterable(detallesCarrito)
                                                .flatMap(detalleCarrito -> {
                                                    return productoRepository.findByProveedorProductoId(detalleCarrito.getProductoId())
                                                            .flatMap(producto -> {
                                                                // Si el producto ya existe, actualizamos su stock
                                                                producto.setStock(producto.getStock() + detalleCarrito.getCantidad());
                                                                producto.setFechaActualizacion(LocalDateTime.now());
                                                                return productoRepository.save(producto);
                                                            })
                                                            .switchIfEmpty(
                                                                    proveedorProductoRepository.findByProductoId(detalleCarrito.getProductoId())
                                                                            .flatMap(proveedorProducto -> {
                                                                                // Si no existe, lo creamos
                                                                                Producto nuevoProducto = new Producto();
                                                                                nuevoProducto.setProveedorProductoId(proveedorProducto.getProductoId());
                                                                                nuevoProducto.setName(proveedorProducto.getName());
                                                                                nuevoProducto.setDescription(proveedorProducto.getDescription());
                                                                                nuevoProducto.setPrice(proveedorProducto.getPrice());
                                                                                nuevoProducto.setStock(detalleCarrito.getCantidad());
                                                                                nuevoProducto.setFechaActualizacion(LocalDateTime.now());
                                                                                return productoRepository.save(nuevoProducto);
                                                                            })
                                                                            .switchIfEmpty(Mono.error(new IllegalArgumentException(
                                                                                    "El producto con ID " + detalleCarrito.getProductoId() + " no está disponible en proveedores."
                                                                            )))
                                                            )
                                                            .then(detalleOrdenRepository.save(new DetalleOrden(
                                                                    ordenGuardada.getId(),
                                                                    detalleCarrito.getProductoId(),
                                                                    detalleCarrito.getCantidad(),
                                                                    detalleCarrito.getPrecioUnitario(),
                                                                    LocalDateTime.now(),
                                                                    LocalDateTime.now()
                                                            )));
                                                })
                                                .then(Mono.just(ordenGuardada))
                                        )
                                        .flatMap(ordenGuardada -> {
                                            // Vaciar el carrito después de confirmar la orden
                                            return detalleCarritoRepository.deleteAllByCarritoId(carritoId)
                                                    .then(carritoRepository.findById(carritoId))
                                                    .flatMap(carritoRestablecido -> {
                                                        carritoRestablecido.setEstado("COMPLETADO");
                                                        carritoRestablecido.setFechaActualizacion(LocalDateTime.now());
                                                        return carritoRepository.save(carritoRestablecido);
                                                    })
                                                    .then(Mono.just(ordenGuardada));
                                        });
                            });
                });
    }

    // Cancelar una orden
    public Mono<Orden> cancelarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La orden no existe.")))
                .flatMap(orden -> {
                    if (!"TEMPORAL".equalsIgnoreCase(orden.getEstado())) {
                        return Mono.error(new IllegalArgumentException("Solo las órdenes en estado TEMPORAL pueden ser canceladas."));
                    }

                    // Restaurar el stock de los productos
                    return detalleOrdenRepository.findByOrdenId(ordenId)
                            .flatMap(detalle -> {
                                return productoRepository.findById(detalle.getProductoId())
                                        .flatMap(producto -> {
                                            producto.setStock(producto.getStock() + detalle.getCantidad());
                                            producto.setFechaActualizacion(LocalDateTime.now());
                                            return productoRepository.save(producto);
                                        });
                            })
                            .then(Mono.defer(() -> {
                                // Actualizar el estado de la orden
                                orden.setEstado("CANCELADA");
                                orden.setFechaActualizacion(LocalDateTime.now());
                                return ordenRepository.save(orden);
                            }))
                            // Eliminar el carrito asociado
//                            .then(detalleCarritoRepository.deleteAllByCarritoId(ordenId))
                            .thenReturn(orden);
                });
    }


    // Listar órdenes por tipo (Venta o Compra)
    public Flux<Orden> listarOrdenesPorTipo(String tipoOrden) {
        return ordenRepository.findByTipoOrden(tipoOrden);
    }

    // Listar detalles por producto
    public Flux<DetalleOrden> listarDetallesPorProducto(Integer productoId) {
        return detalleOrdenRepository.findByProductoId(productoId);
    }


    public Mono<Void> eliminarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La orden no existe.")))
                .flatMap(orden -> {
                    // Eliminar los detalles asociados a la orden
                    return detalleOrdenRepository.findByOrdenId(ordenId)
                            .flatMap(detalle -> detalleOrdenRepository.deleteById(detalle.getId()))
                            .then(detalleCarritoRepository.deleteAllByCarritoId(ordenId)) // Eliminar el carrito asociado si existe
                            .then(ordenRepository.deleteById(ordenId)); // Eliminar la orden
                });
    }
}
