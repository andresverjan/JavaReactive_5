package api.service;

import api.model.DTO.DetalleOrdenVentaDTO;
import api.model.OrdenVenta;
import api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrdenVentaService {
    private final OrdenVentaRepository ordenVentaRepository;
    private final DetalleOrdenVentaRepository detalleOrdenVentaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;


    @Autowired
    public OrdenVentaService(OrdenVentaRepository ordenVentaRepository,
                             DetalleOrdenVentaRepository detalleOrdenVentaRepository,
                             ProductoRepository productoRepository,
                             ClienteRepository clienteRepository
                             ) {
        this.ordenVentaRepository = ordenVentaRepository;
        this.detalleOrdenVentaRepository = detalleOrdenVentaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }
    // Listar ordenes de venta
    public Flux<OrdenVenta> getOrdenes(){
        return ordenVentaRepository.findAll();
    }

    // Listar contenido de una orden de venta
    public Mono<List<DetalleOrdenVentaDTO>> listarContenidoOrdenVenta(Long ordenVentaId) {
        return ordenVentaRepository.findById(ordenVentaId)
                .flatMap(orden ->
                        clienteRepository.findById(orden.getClienteId())
                                .flatMap(cliente -> detalleOrdenVentaRepository.findByOrdenVentaId(ordenVentaId)
                                        .flatMap(detalle ->
                                                productoRepository.findById(detalle.getProductId())
                                                        .map(producto -> new DetalleOrdenVentaDTO(
                                                                cliente.getId(), cliente.getName(), // Datos del cliente
                                                                detalle.getProductId(), producto.getName(), // Datos del producto
                                                                detalle.getQuantity(), detalle.getUnitPrice()))
                                        )
                                        .collectList() // Acumula todos los detalles
                                )
                );
    }
}
