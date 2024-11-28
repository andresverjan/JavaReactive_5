package com.artifactory.crud.component;

import com.artifactory.crud.model.ReporteCompra;
import com.artifactory.crud.model.ReporteVenta;
import com.artifactory.crud.model.ReporteVentas;
import com.artifactory.crud.service.ReporteVentaService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReporteVentaComponentHandler {

    private final ReporteVentaService reporteVentaService;

    public ReporteVentaComponentHandler(ReporteVentaService reporteVentaService) {
        this.reporteVentaService = reporteVentaService;
    }


    public Mono<ServerResponse> getReporteVenta(ServerRequest request) {
        String fechaInicial = request.queryParam("fechaInicial")
                .orElseThrow(() -> new IllegalArgumentException("fechaInicial is required"));
        String  fechaFinal = request.queryParam("fechaFinal")
                .orElseThrow(() -> new IllegalArgumentException("fechaFinal is required"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteVentaService.reporteVentas(fechaInicial,fechaFinal), ReporteVenta.class);

    }

    public Mono<ServerResponse> getTop5ReporteVenta(ServerRequest request) {
        String fechaInicial = request.queryParam("fechaInicial")
                .orElseThrow(() -> new IllegalArgumentException("fechaInicial is required"));
        String  fechaFinal = request.queryParam("fechaFinal")
                .orElseThrow(() -> new IllegalArgumentException("fechaFinal is required"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteVentaService.obtenerTop5Ventas(fechaInicial,fechaFinal), ReporteVenta.class);

    }

    public Mono<ServerResponse> getReporteVentaCliente(ServerRequest request) {
        String fechaInicial = request.queryParam("fechaInicial")
                .orElseThrow(() -> new IllegalArgumentException("fechaInicial is required"));
        String  fechaFinal = request.queryParam("fechaFinal")
                .orElseThrow(() -> new IllegalArgumentException("fechaFinal is required"));
        String  idcliente = request.queryParam("idcliente")
                .orElseThrow(() -> new IllegalArgumentException("idcliente is required"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteVentaService.reporteVentasCliente(fechaInicial,fechaFinal,idcliente), ReporteVenta.class);

    }

    public Mono<ServerResponse> getReporteVentaCategoriaFecha(ServerRequest request) {
        String fechaInicial = request.queryParam("fechaInicial")
                .orElseThrow(() -> new IllegalArgumentException("fechaInicial is required"));
        String  fechaFinal = request.queryParam("fechaFinal")
                .orElseThrow(() -> new IllegalArgumentException("fechaFinal is required"));
        String categoria = request.queryParam("categoria").orElse("");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteVentaService.reporteVentaCategoria(categoria,fechaInicial,fechaFinal), ReporteVentas.class);

    }
}
