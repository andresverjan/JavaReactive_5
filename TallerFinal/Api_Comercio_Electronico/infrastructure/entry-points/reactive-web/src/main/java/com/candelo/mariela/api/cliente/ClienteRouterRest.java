package com.candelo.mariela.api.cliente;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ClienteRouterRest{

    @Bean
    public RouterFunction<ServerResponse> ClientRouterFunction(ClienteHandler handler) {
        return route()
                //.GET("/clientes", handler::getAllClientes)
                .GET("/cliente/{documentNumber}", handler::getClienteByDocumentNumber)
                .POST("/cliente", handler::createCliente)
                .PUT("/cliente/{documentNumber}", handler::updateCliente)
                .DELETE("/cliente/{documentNumber}", handler::deleteCliente)
                .GET("/healthy", request -> ServerResponse.ok().build())
                .build();
    }
}
