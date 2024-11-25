package org.example.finalproject.reportes;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Component
public class ReporteHandler {

    private final ReporteService reporteService;

    public ReporteHandler(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public Mono<ServerResponse> obtenerReporteCompras(ServerRequest request) {
        LocalDate fechaInicio = LocalDate.parse(request.queryParam("fechaInicio").orElseThrow(() ->
                new IllegalArgumentException("La fecha de inicio es requerida.")
        ), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate fechaFin = LocalDate.parse(request.queryParam("fechaFin").orElseThrow(() ->
                new IllegalArgumentException("La fecha de fin es requerida.")
        ), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
        LocalDateTime fechaFinDateTime = fechaFin.atTime(LocalTime.MAX);

        return reporteService.obtenerReporteCompras(fechaInicioDateTime, fechaFinDateTime)
                .collectList()
                .flatMap(reporte -> {
                    if (reporte.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(Map.of("error", "No se encontraron compras en el intervalo de tiempo especificado."));
                    }
                    return ServerResponse.ok().bodyValue(reporte);
                })
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> obtenerReporteVentas(ServerRequest request) {
        LocalDate fechaInicio = LocalDate.parse(request.queryParam("fechaInicio").orElseThrow(() ->
                new IllegalArgumentException("La fecha de inicio es requerida.")
        ), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate fechaFin = LocalDate.parse(request.queryParam("fechaFin").orElseThrow(() ->
                new IllegalArgumentException("La fecha de fin es requerida.")
        ), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
        LocalDateTime fechaFinDateTime = fechaFin.atTime(LocalTime.MAX);

        return reporteService.obtenerReporteVentas(fechaInicioDateTime, fechaFinDateTime)
                .collectList()
                .flatMap(reporte -> {
                    if (reporte.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(Map.of("error", "No se encontraron ventas en el intervalo de tiempo especificado."));
                    }
                    return ServerResponse.ok().bodyValue(reporte);
                })
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> obtenerReporteVentasPorCliente(ServerRequest request) {
        Integer clienteId = Integer.parseInt(request.queryParam("clienteId")
                .orElseThrow(() -> new IllegalArgumentException("El clienteId es requerido")));

        String fechaInicioStr = request.queryParam("fechaInicio")
                .orElseThrow(() -> new IllegalArgumentException("La fechaInicio es requerida"));
        String fechaFinStr = request.queryParam("fechaFin")
                .orElseThrow(() -> new IllegalArgumentException("La fechaFin es requerida"));

        LocalDateTime fechaInicio;
        LocalDateTime fechaFin;

        try {
            fechaInicio = parseFecha(fechaInicioStr, true);
            fechaFin = parseFecha(fechaFinStr, false);
        } catch (IllegalArgumentException e) {
            return ServerResponse.badRequest()
                    .bodyValue(Map.of("error", e.getMessage()));
        }

        // Validar que fechaInicio sea antes de fechaFin
        if (fechaInicio.isAfter(fechaFin)) {
            return ServerResponse.badRequest()
                    .bodyValue(Map.of("error", "La fechaInicio no puede ser posterior a la fechaFin"));
        }

        return reporteService.obtenerVentasPorCliente(clienteId, fechaInicio, fechaFin)
                .collectList()
                .flatMap(reporte -> {
                    if (reporte.isEmpty()) {
                        return ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(Map.of("mensaje", "No se encontraron ventas para el cliente en el rango de fechas especificado."));
                    }
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(reporte);
                })
                .onErrorResume(error -> ServerResponse.badRequest()
                        .bodyValue(Map.of("error", error.getMessage())));
    }


    // Método auxiliar para manejar el parsing de fechas
    private LocalDateTime parseFecha(String fechaStr, boolean inicioDelDia) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            return inicioDelDia ? fecha.atStartOfDay() : fecha.atTime(LocalTime.MAX);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha inválida: " + fechaStr);
        }
    }

}
