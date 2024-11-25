package api.component.Config;

import api.component.Handler.ClienteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
public class ClienteConfig {
    @Bean
    public RouterFunction<ServerResponse> Clienteroutes (ClienteHandler clienteHandler){
        return route()
                .GET("/clientes", clienteHandler::getClientes)
                .GET("/clientes/{id}", clienteHandler::getClienteById)
                .POST("/clientes", clienteHandler::create)
                .PUT("/clientes", clienteHandler::update)
                .DELETE("/clientes/{id}", clienteHandler::deleteClienteById)
                .build();
    }
}
