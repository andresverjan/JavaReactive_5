package api.component.Config;

import api.component.Handler.CarritoHandler;
import api.component.Handler.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductoConfig {
    @Bean
    public RouterFunction<ServerResponse> ProductoRoutes(ProductoHandler productoHandler) {
        return route()
                .GET("/productos", productoHandler::getProductos)
                .GET("/productos/{id}", productoHandler::getProductoById)
                .POST("/productos", productoHandler::create)
                .PUT("/productos", productoHandler::update)
                .DELETE("/productos/{id}", productoHandler::deleteById)
                .build();
    }
}
