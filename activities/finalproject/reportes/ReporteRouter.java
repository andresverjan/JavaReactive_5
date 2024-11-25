package org.example.finalproject.reportes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReporteRouter {

    @Bean
    public RouterFunction<ServerResponse> reporteRoutes(ReporteHandler handler) {
        return RouterFunctions
                .route(GET("/api/reportes/ventas"), handler::obtenerReporteVentas)
                .andRoute(GET("/api/reportes/ventas/cliente"), handler::obtenerReporteVentasPorCliente)

                .andRoute(GET("/api/reportes/compras"), handler::obtenerReporteCompras);
    }
}

