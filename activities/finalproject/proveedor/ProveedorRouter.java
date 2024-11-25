package org.example.finalproject.proveedor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProveedorRouter {

    @Bean
    public RouterFunction<ServerResponse> proveedorRoutes(ProveedorHandler handler) {
        return route(POST("/api/proveedores"), handler::crearProveedor)
                .andRoute(GET("/api/proveedores"), handler::listarProveedores)
                .andRoute(PUT("/api/proveedores/{id}"), handler::actualizarProveedor)
                .andRoute(DELETE("/api/proveedores/{id}"), handler::eliminarProveedor)
                .andRoute(POST("/api/proveedores/productos"), handler::agregarProductoAProveedor)
                .andRoute(GET("/api/proveedores/productos"), handler::listarTodosLosProductosDeProveedores)
                .andRoute(GET("/api/proveedores/{proveedorId}/productos"), handler::listarProductosDeProveedor);
    }
}