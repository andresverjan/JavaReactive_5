package com.artifactory.crud.component;

import com.artifactory.crud.model.ReporteCompra;
import com.artifactory.crud.model.ReporteCompras;
import com.artifactory.crud.service.ReporteCompraService;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class ReporteCompraComponentHandler {

    private final ReporteCompraService reporteCompraService;

    public ReporteCompraComponentHandler(ReporteCompraService reporteService) {
        this.reporteCompraService = reporteService;
    }

    public Mono<ServerResponse> getReporteCompras(ServerRequest request) {

        String fechaInicial = request.queryParam("fechaInicial")
                .orElseThrow(() -> new IllegalArgumentException("fechaInicial is required"));
        String  fechaFinal = request.queryParam("fechaFinal")
                .orElseThrow(() -> new IllegalArgumentException("fechaFinal is required"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteCompraService.getReporteCompra(fechaInicial,fechaFinal), ReporteCompras.class);

    }
    public Mono<ServerResponse> getReporteCompraProveedor(ServerRequest request) {

        String fechaInicial = request.queryParam("fechaInicial")
                .orElseThrow(() -> new IllegalArgumentException("fechaInicial is required"));
        String  fechaFinal = request.queryParam("fechaFinal")
                .orElseThrow(() -> new IllegalArgumentException("fechaFinal is required"));
        String  idproveedor = request.queryParam("idproveedor")
                .orElseThrow(() -> new IllegalArgumentException("idproveedor is required"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteCompraService.getReporteCompraProveedor(fechaInicial,fechaFinal, idproveedor), ReporteCompras.class);

    }
}
