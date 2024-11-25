package org.example.finalproject.producto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductoRouter {

    @Bean
    public RouterFunction<ServerResponse> productoRoutes(ProductoHandler handler) {
        return route(POST("/api/productos"), handler::crearProducto)
                .andRoute(PUT("/api/productos/{id}"), handler::editarProducto)
                .andRoute(GET("/api/productos"), handler::listarProductos)
                .andRoute(GET("/api/productos/buscar/{id}"), handler::buscarPorId)
                .andRoute(GET("/api/productos/buscar"), handler::buscarPorNombre)
                .andRoute(PUT("/api/productos/{id}/stock"), handler::modificarStock)
                .andRoute(DELETE("/api/productos/{id}"), handler::eliminarProducto);
    }
}
