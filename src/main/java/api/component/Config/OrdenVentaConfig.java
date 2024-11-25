package api.component.Config;

import api.component.Handler.OrdenCompraHandler;
import api.component.Handler.OrdenVentaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class OrdenVentaConfig {
    @Bean
    public RouterFunction<ServerResponse> OrdenVentaRoutes(OrdenVentaHandler ordenVentaHandler) {
        return route()
                .GET("/ordenes-venta", ordenVentaHandler::getOrdenesVenta)
                .GET("/ordenes-venta/{ordenVentaId}/contenido", ordenVentaHandler::listarContenidoOrdenVenta)
                .build();
    }
}
