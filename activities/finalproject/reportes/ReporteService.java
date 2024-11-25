package org.example.finalproject.reportes;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public Flux<ReporteCompraDTO> obtenerReporteCompras(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reporteRepository.obtenerReporteCompras(fechaInicio, fechaFin)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No se encontraron compras en el intervalo de tiempo especificado.")));
    }
    public Flux<ReporteVentaDto> obtenerReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reporteRepository.obtenerReporteVentas(fechaInicio, fechaFin)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No se encontraron ventas en el intervalo de tiempo especificado.")));
    }

    public Flux<ReporteVentasClienteDto> obtenerVentasPorCliente(Integer clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reporteRepository.obtenerReporteVentasPorCliente(clienteId, fechaInicio, fechaFin)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No se encontraron ventas para el cliente en el intervalo de tiempo especificado.")));
    }

}
