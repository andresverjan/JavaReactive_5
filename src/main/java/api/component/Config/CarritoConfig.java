package api.component.Config;

import api.component.Handler.CarritoHandler;
import api.component.Handler.ClienteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
public class CarritoConfig {
    @Bean
    public RouterFunction<ServerResponse> carritoRoutes(CarritoHandler carritoHandler) {
        return route()
                .GET("/carritos/{id}", carritoHandler::BuscarPorId)
                .GET("/carritos/{carritoId}/contenido", carritoHandler::listarContenidoCarrito)
                .POST("/carritos/AddProducto", carritoHandler::agregarProducto)
                .POST("/carritos/modificarCantidad", carritoHandler::modificarCantidadProducto)
                .DELETE("/carritos/DeleteProducto/{carritoId}/{productId}", carritoHandler::eliminarProducto)
                .POST("/carritos/vaciar/{carritoId}", carritoHandler::vaciarCarrito)
                .POST("/carritos/{carritoId}/comprar", carritoHandler::comprarCarrito)
                .build();
    }
}
