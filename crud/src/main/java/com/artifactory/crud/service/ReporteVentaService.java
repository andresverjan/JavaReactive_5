package com.artifactory.crud.service;

import com.artifactory.crud.model.*;
import com.artifactory.crud.repository.*;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ReporteVentaService {

    private final VentaRepository ventaRepository;

    private final ClienteRepository clienteRepository;

    private final CarritoRepository carritoRepository;

    private final DetalleCarritoRepository detalleCarritoRepository;

    private final ProductoRepository productoRepository;

    private final DatabaseClient databaseClient;

    public ReporteVentaService(VentaRepository ventaRepository, ClienteRepository clienteRepository, CarritoRepository carritoRepository, DetalleCarritoRepository detalleCarritoRepository, ProductoRepository productoRepository, DatabaseClient databaseClient) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.productoRepository = productoRepository;
        this.databaseClient = databaseClient;
    }

    public Flux<ReporteVenta> reporteVentas(String fechaInicial, String fechaFinal) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);

        return ventaRepository.findByFechaBetween(dateInicial,dateFinal)
                .flatMap(venta -> {
                    Mono<Cliente> clienteMono = clienteRepository.findById(venta.getIdcliente());
                    Mono<Carrito> carritoMono = carritoRepository.findById(venta.getIdcarrito());
                    Flux<Producto> detallesFlux = detalleCarritoRepository.buscarDetalleCarrito(venta.getIdcarrito())
                            .flatMap(detalle -> productoRepository.findById(detalle.getIdproducto())
                                    .map(producto -> {
                                        detalle.setIdproducto(producto.getIdproducto());
                                        return producto;
                                    }));

                    return Mono.zip(clienteMono, carritoMono, detallesFlux.collectList())
                            .map(tuple -> new ReporteVenta(venta, tuple.getT1(), tuple.getT2(), tuple.getT3()));
                });
    }


    public Flux<ReporteVenta> obtenerTop5Ventas(String fechaInicial, String fechaFinal) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);
        return ventaRepository.findByFechaBetween(dateInicial, dateFinal)
                .flatMap(venta -> {
                    Mono<Cliente> clienteMono = clienteRepository.findById(venta.getIdcliente());
                    Mono<Carrito> carritoMono = carritoRepository.findById(venta.getIdcarrito());
                    Flux<Producto> detallesFlux = detalleCarritoRepository.buscarDetalleCarrito(venta.getIdcarrito())
                            .flatMap(detalle -> productoRepository.findById(detalle.getIdproducto())
                                    .map(producto -> {
                                        detalle.setIdproducto(producto.getIdproducto());
                                        return producto;
                                    }));

                    return Mono.zip(clienteMono, carritoMono, detallesFlux.collectList())
                            .map(tuple -> new ReporteVenta(venta, tuple.getT1(), tuple.getT2(), tuple.getT3()));
                })
                .sort((v1, v2) -> Double.compare(v2.getVenta().getTotalventa(), v1.getVenta().getTotalventa()))
                .take(5);
    }

    public Flux<ReporteVenta> reporteVentasCliente(String fechaInicial, String fechaFinal, String idcliente) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);
        Long clienteid = Long.parseLong(idcliente);
        return ventaRepository.findByFechaBetweenCliente(dateInicial,dateFinal, clienteid)
                .flatMap(venta -> {
                    Mono<Cliente> clienteMono = clienteRepository.findById(venta.getIdcliente());
                    Mono<Carrito> carritoMono = carritoRepository.findById(venta.getIdcarrito());
                    Flux<Producto> detallesFlux = detalleCarritoRepository.buscarDetalleCarrito(venta.getIdcarrito())
                            .flatMap(detalle -> productoRepository.findById(detalle.getIdproducto())
                                    .map(producto -> {
                                        detalle.setIdproducto(producto.getIdproducto());
                                        return producto;
                                    }));

                    return Mono.zip(clienteMono, carritoMono, detallesFlux.collectList())
                            .map(tuple -> new ReporteVenta(venta, tuple.getT1(), tuple.getT2(), tuple.getT3()));
                });
    }

    public Flux<ReporteVenta> reporteVentaCategoriaFecha(String categoria, String fechaInicial, String fechaFinal) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);

        return ventaRepository.findByFechaBetween(dateInicial,dateFinal)
                .flatMap(venta -> {
                    Mono<Cliente> clienteMono = clienteRepository.findById(venta.getIdcliente());
                    Mono<Carrito> carritoMono = carritoRepository.findById(venta.getIdcarrito());
                    Flux<Producto> detallesFlux = detalleCarritoRepository.buscarDetalleCarrito(venta.getIdcarrito())
                            .flatMap(detalle -> productoRepository.findByCategoriaById(detalle.getIdproducto(),categoria)
                                    //.filter(producto -> producto.getCategoria().equals(categoria))
                                    .map(producto -> {
                                        detalle.setIdproducto(producto.getIdproducto());
                                        return producto;
                                    }));

                    return Mono.zip(clienteMono, carritoMono, detallesFlux.collectList())
                            .map(tuple -> new ReporteVenta(venta, tuple.getT1(), tuple.getT2(), tuple.getT3()));
                });
    }

    public Flux<ReporteVentas> reporteVentaCategoria(String categoria, String fechaInicial, String fechaFinal) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);


        return  databaseClient.sql("select v.idventa," +
                        " v.totalventa," +
                        " v.fecha as ventafecha," +
                        " c.nombre as nombrecliente," +
                        " c.correo as correocliente," +
                        " c.direccion as direccioncliente," +
                        " c.telefono as telefonocliente," +
                        " p.nombre as nombreproducto," +
                        " p.descripcion as descripcioproducto," +
                        " p.precio as precioproducto," +
                        " p.categoria as categoria," +
                        " dc.cantidad " +
                        " from ventas v" +
                        " inner join cliente c on v.idcliente = c.idcliente" +
                        " inner join carrito ca on ca.idcarrito = v.idcarrito" +
                        " inner join detallecarrito dc on dc.idcarrito = v.idcarrito" +
                        " inner join producto p on p.idproducto = dc.idproducto " +
                        " where fecha between :fechaInicial and :fechaFinal and p.categoria = :categoria")
                .bind("fechaInicial", dateInicial)
                .bind("fechaFinal", dateFinal)
                .bind("categoria" , categoria)
                .map((row, metadata) -> new ReporteVentas(
                        row.get("idventa", Long.class),
                        row.get("totalventa", Integer.class),
                        row.get("ventafecha", LocalDate.class),
                        row.get("nombrecliente", String.class),
                        row.get("correocliente", String.class),
                        row.get("direccioncliente", String.class),
                        row.get("telefonocliente", String.class),
                        row.get("nombreproducto", String.class),
                        row.get("descripcioproducto", String.class),
                        row.get("precioproducto", Integer.class),
                        row.get("categoria", String.class),
                        row.get("cantidad", Integer.class)
                )).all();
    }
}
