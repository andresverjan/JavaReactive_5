package api.component.Handler;
import api.model.DetalleCarrito;
import api.model.Request.*;
import api.service.CarritoService;
import api.service.ClienteService;
import api.service.ReportesService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class ReporteHandler {
    private final ReportesService reporteService;

    public ReporteHandler(ReportesService reporteService) {
        this.reporteService = reporteService;
    }

    // Reporte de compras en un intervalo de tiempo
    public Mono<ServerResponse> reporteCompras(ServerRequest request) {
        return request.bodyToMono(ReporteRequest.class)
                .flatMap(payload -> {
                    // Verificar si los parámetros 'inicio' y 'fin' están presentes
                    if (payload.getInicio() == null || payload.getFin() == null) {
                        return Mono.error(new IllegalArgumentException("El parámetro inicio y fin son requeridos"));
                    }

                    // Llamar al servicio para obtener los datos del reporte de compras
                    return reporteService.reporteCompras(payload.getInicio(), payload.getFin());
                })
                .flatMap(reporte -> ServerResponse.ok().bodyValue(reporte))  // Retornar el reporte
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Reporte de ventas en un intervalo de tiempo
    public Mono<ServerResponse> reporteVentas(ServerRequest request) {
        return request.bodyToMono(ReporteRequest.class)
                .flatMap(payload -> {
                    // Verificar si los parámetros 'inicio' y 'fin' están presentes
                    if (payload.getInicio() == null || payload.getFin() == null) {
                        return Mono.error(new IllegalArgumentException("El parámetro inicio y fin son requeridos"));
                    }

                    // Llamar al servicio para obtener los datos del reporte de ventas
                    return reporteService.reporteVentas(payload.getInicio(), payload.getFin());
                })
                .flatMap(reporte -> ServerResponse.ok().bodyValue(reporte))  // Retornar el reporte
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Reporte de compras por proveedor
    public Mono<ServerResponse> reporteComprasPorProveedor(ServerRequest request) {
        return request.bodyToMono(ReporteProveedorRequest.class)
                .flatMap(payload -> {
                    // Verificar si los parámetros 'inicio', 'fin' y 'proveedorId' están presentes
                    if (payload.getInicio() == null || payload.getFin() == null || payload.getProveedorId() == null) {
                        return Mono.error(new IllegalArgumentException("Los parámetros inicio, fin y proveedorId son requeridos"));
                    }

                    // Llamar al servicio para obtener el reporte de compras por proveedor
                    return reporteService.reporteComprasPorProveedor(payload.getProveedorId(), payload.getInicio(), payload.getFin());
                })
                .flatMap(reporte -> ServerResponse.ok().bodyValue(reporte))  // Retornar el reporte
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Reporte de ventas por cliente
    public Mono<ServerResponse> reporteVentasPorCliente(ServerRequest request) {
        return request.bodyToMono(ReporteClienteRequest.class)
                .flatMap(payload -> {
                    // Verificar si los parámetros 'inicio', 'fin' y 'clienteId' están presentes
                    if (payload.getInicio() == null || payload.getFin() == null || payload.getClienteId() == null) {
                        return Mono.error(new IllegalArgumentException("Los parámetros inicio, fin y clienteId son requeridos"));
                    }

                    // Llamar al servicio para obtener el reporte de ventas por cliente
                    return reporteService.reporteVentasPorCliente(payload.getClienteId(), payload.getInicio(), payload.getFin());
                })
                .flatMap(reporte -> ServerResponse.ok().bodyValue(reporte))  // Retornar el reporte
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

}

