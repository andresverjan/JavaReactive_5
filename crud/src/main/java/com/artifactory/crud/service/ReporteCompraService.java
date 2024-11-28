package com.artifactory.crud.service;

import com.artifactory.crud.model.Compra;
import com.artifactory.crud.model.ReporteCompra;
import com.artifactory.crud.model.ReporteCompras;
import com.artifactory.crud.repository.CompraRepository;
import com.artifactory.crud.repository.ProductoRepository;
import com.artifactory.crud.repository.ProveedorRepository;
import com.artifactory.crud.repository.ReporteCompraRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class ReporteCompraService {

    private final ReporteCompraRepository reporteCompraRepository;

    private final CompraRepository compraRepository;

    private final ProveedorRepository proveedorRepository;

    private final ProductoRepository productoRepository;

    private final DatabaseClient databaseClient;


    public ReporteCompraService(ReporteCompraRepository reporteCompraRepository, CompraRepository compraRepository, ProveedorRepository proveedorRepository, ProductoRepository productoRepository, DatabaseClient databaseClient) {
        this.reporteCompraRepository = reporteCompraRepository;
        this.compraRepository = compraRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.databaseClient = databaseClient;
    }

    public Flux<ReporteCompras> getReporteCompra(String fechaInicial, String fechaFinal) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);

        return compraRepository.findByFechaBetween(dateInicial,dateFinal)
                .flatMap(compra -> Mono.zip(
                        proveedorRepository.findById(compra.getIdproveedor()),
                        productoRepository.findById(compra.getIdproducto()),
                                (proveedor, producto) -> new ReporteCompras(compra, proveedor, producto)
                        )
                .onErrorStop());
    }

    public Flux<ReporteCompras> getReporteCompraProveedor(String fechaInicial, String fechaFinal, String idproveedor) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);
        Long proveedorid = Long.parseLong(idproveedor);
        return compraRepository.findByFechaBetweenByProveedor(dateInicial,dateFinal,proveedorid)
                .flatMap(compra -> Mono.zip(
                                proveedorRepository.findById(compra.getIdproveedor()),
                                productoRepository.findById(compra.getIdproducto()),
                                (proveedor, producto) -> new ReporteCompras(compra, proveedor, producto)
                        )
                        .onErrorStop());
    }

    public Flux<ReporteCompra> getReporte(String fechaInicial, String fechaFinal) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateInicial = LocalDate.parse(fechaInicial, formato);
        LocalDate dateFinal = LocalDate.parse(fechaFinal, formato);


        return  databaseClient.sql("select " +
                        " c.idcompra," +
                        " c.fecha," +
                        " c.cantidad," +
                        " p.nombre as nombreProducto," +
                        " p.descripcion as descripcionProducto," +
                        " p.precio as precioProducto," +
                        " p.categoria as categoriaProducto," +
                        " pe.nombre as nombreProveedor," +
                        " pe.correo as correoProveedor," +
                        " pe.direccion as direccionProveedor," +
                        " pe.telefono as telefonoProveedor" +
                        " from compra c" +
                        " inner join proveedor pe on c.idproveedor = pe.idproveedor" +
                        " inner join producto p on c.idproducto = p.idproducto" +
                        " where estado = true and fecha between :fechaInicial and :fechaFinal")
                .bind("fechaInicial", dateInicial)
                .bind("fechaFinal", dateFinal)
                .map((row, metadata) -> new ReporteCompra(
                        row.get("idcompra", Long.class),
                        row.get("fecha", String.class),
                        row.get("cantidad", Integer.class),
                        row.get("nombreProducto", String.class),
                        row.get("descripcionProducto", String.class),
                        row.get("precioProducto", Integer.class),
                        row.get("categoriaProducto", String.class),
                        row.get("nombreProveedor", String.class),
                        row.get("correoProveedor", String.class),
                        row.get("direccionProveedor", String.class),
                        row.get("telefonoProveedor", String.class)
                )).all();
    }

}
