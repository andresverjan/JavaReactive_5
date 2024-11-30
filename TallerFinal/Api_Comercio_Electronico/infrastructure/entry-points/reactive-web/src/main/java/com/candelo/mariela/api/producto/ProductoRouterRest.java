package com.candelo.mariela.api.producto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductoRouterRest {
    @Bean
    public RouterFunction<ServerResponse> ProductRouterFunction(ProductoHandler handler) {
        return route()
                .GET("/productos", handler::getAllProductos)
                .GET("/producto/{id}", handler::getProductoById)
                .POST("/producto", handler::createProducto)
                .PUT("/producto/{id}", handler::updateProducto)
                .PUT("/producto/{id}/stock/{stock}", handler::updateStockProducto)
                .DELETE("/producto/{id}", handler::deleteProducto)
                .GET("/health", request -> ServerResponse.ok().build())
                .build();
    }
}
