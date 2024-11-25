package api.component.Config;

import api.component.Handler.CarritoHandler;
import api.component.Handler.OrdenCompraHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class OrdenCompraConfig {
    @Bean
    public RouterFunction<ServerResponse> OrdenCompraRoutes(OrdenCompraHandler ordenCompraHandler) {
        return route()
                .GET("/ordenes-compra", ordenCompraHandler::getoOrdenes)
                .GET("/ordenes-compra/{ordenCompraId}/contenido", ordenCompraHandler::listarContenidoOrdenCompra)
                .POST("/ordenes-compra", ordenCompraHandler::crearOrdenCompra)
                .POST("/ordenes-compra/{id}/cancelar", ordenCompraHandler::cancelarOrdenCompra)
                .POST("/ordenes-compra/{id}/completar", ordenCompraHandler::completarOrdenCompra)
                .build();
    }
}
