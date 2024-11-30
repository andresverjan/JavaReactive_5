package com.candelo.mariela.api.proveedor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProveedorRouterRest {

   @Bean
    public RouterFunction<ServerResponse> ProveedorRouterFunction(ProveedorHandler handler) {
        return route()
                .POST("/proveedor", handler::saveProveedor)
                .GET("/proveedor/{documentNumber}", handler::getProveedorByDocumentNumber)
                .PUT("/proveedor", handler::updateProveedor)
                .DELETE("/proveedor/{documentNumber}", handler::deleteProveedorByDocumentNumber)
                .build();
    }
}
