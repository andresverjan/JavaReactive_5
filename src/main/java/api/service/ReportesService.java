package api.service;

import api.model.DTO.*;
import api.repository.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportesService {
    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleOrdenCompraRepository;
    private final OrdenVentaRepository ordenVentaRepository;
    private final DetalleOrdenVentaRepository detalleOrdenVentaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final ProveedorRepository proveedorRepository;

    public ReportesService(ClienteRepository clienteRepository,
                           OrdenCompraRepository ordenCompraRepository,
                           DetalleOrdenCompraRepository detalleOrdenCompraRepository,
                           OrdenVentaRepository ordenVentaRepository,
                           DetalleOrdenVentaRepository detalleOrdenVentaRepository,
                           ProductoRepository productoRepository,
                           ProveedorRepository proveedorRepository)
    {
        this.clienteRepository = clienteRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.detalleOrdenCompraRepository = detalleOrdenCompraRepository;
        this.ordenVentaRepository = ordenVentaRepository;
        this.detalleOrdenVentaRepository = detalleOrdenVentaRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }
    public Mono<List<ReporteCompraDTO>> reporteCompras(LocalDateTime inicio, LocalDateTime fin) {
        return ordenCompraRepository.findByDateBetween(inicio, fin) // Flux<OrdenCompra>
                .flatMap(ordenCompra ->
                        proveedorRepository.findById(ordenCompra.getProviderId()) // Mono<Proveedor>
                                .flatMapMany(proveedor -> detalleOrdenCompraRepository.findByOrdenCompraId(ordenCompra.getId()) // Flux<DetalleOrdenCompra>
                                        .flatMap(detalle -> productoRepository.findById(detalle.getProductId()) // Mono<Producto>
                                                .map(producto -> new ReporteCompraDTO(
                                                        ordenCompra.getId(),
                                                        proveedor.getId(),
                                                        proveedor.getName(),
                                                        ordenCompra.getDate(),
                                                        producto.getId(),
                                                        producto.getName(),
                                                        detalle.getQuantity(),
                                                        detalle.getUnitPrice(),
                                                        detalle.getQuantity() * detalle.getUnitPrice()
                                                ))
                                        )
                                )
                )
                .collectList(); // Al final recolecta en un Mono<List<ReporteCompraDTO>>
    }

    public Mono<List<ReporteVentaDTO>> reporteVentas(LocalDateTime inicio, LocalDateTime fin) {
        return ordenVentaRepository.findByDateBetween(inicio, fin) // Flux<OrdenVenta>
                .flatMap(ordenVenta ->
                        clienteRepository.findById(ordenVenta.getClienteId()) // Mono<Cliente>
                                .flatMapMany(cliente -> detalleOrdenVentaRepository.findByOrdenVentaId(ordenVenta.getId()) // Flux<DetalleOrdenVenta>
                                        .flatMap(detalle -> productoRepository.findById(detalle.getProductId()) // Mono<Producto>
                                                .map(producto -> new ReporteVentaDTO(
                                                        ordenVenta.getId(),
                                                        cliente.getId(),
                                                        cliente.getName(),
                                                        ordenVenta.getDate(),
                                                        producto.getId(),
                                                        producto.getName(),
                                                        detalle.getQuantity(),
                                                        detalle.getUnitPrice(),
                                                        detalle.getQuantity() * detalle.getUnitPrice()
                                                ))
                                        )
                                )
                )
                .collectList(); // Recolecta en Mono<List<ReporteVentaDTO>>
    }

    public Mono<List<ReporteCompraDTO>> reporteComprasPorProveedor(Long proveedorId, LocalDateTime inicio, LocalDateTime fin) {
        return proveedorRepository.findById(proveedorId) // Mono<Proveedor>
                .flatMapMany(proveedor -> ordenCompraRepository.findByDateBetweenAndProveedorId(proveedorId,inicio, fin) // Flux<OrdenCompra>
                        .flatMap(ordenCompra ->
                                detalleOrdenCompraRepository.findByOrdenCompraId(ordenCompra.getId()) // Flux<DetalleOrdenCompra>
                                        .flatMap(detalle -> productoRepository.findById(detalle.getProductId()) // Mono<Producto>
                                                .map(producto -> new ReporteCompraDTO(
                                                        ordenCompra.getId(),
                                                        proveedor.getId(),
                                                        proveedor.getName(), // Nombre del proveedor
                                                        ordenCompra.getDate(),
                                                        producto.getId(),
                                                        producto.getName(),
                                                        detalle.getQuantity(),
                                                        detalle.getUnitPrice(),
                                                        detalle.getQuantity() * detalle.getUnitPrice()
                                                ))
                                        )
                        )
                )
                .collectList(); // Recolecta en Mono<List<ReporteCompraDTO>>
    }
    public Mono<List<ReporteVentaDTO>> reporteVentasPorCliente(Long clienteId, LocalDateTime inicio, LocalDateTime fin) {
        return clienteRepository.findById(clienteId) // Mono<Cliente>
                .flatMapMany(cliente -> ordenVentaRepository.findByClienteIdAndDateBetween( clienteId,inicio, fin) // Flux<OrdenVenta>
                        .flatMap(ordenVenta ->
                                detalleOrdenVentaRepository.findByOrdenVentaId(ordenVenta.getId()) // Flux<DetalleOrdenVenta>
                                        .flatMap(detalle -> productoRepository.findById(detalle.getProductId()) // Mono<Producto>
                                                .map(producto -> new ReporteVentaDTO(
                                                        ordenVenta.getId(),
                                                        cliente.getId(),
                                                        cliente.getName(), // Nombre del cliente
                                                        ordenVenta.getDate(),
                                                        producto.getId(),
                                                        producto.getName(),
                                                        detalle.getQuantity(),
                                                        detalle.getUnitPrice(),
                                                        detalle.getQuantity() * detalle.getUnitPrice()
                                                ))
                                        )
                        )
                )
                .collectList(); // Recolecta en Mono<List<ReporteVentaDTO>>
    }

}
