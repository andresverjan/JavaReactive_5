package api.component.Config;


import api.component.Handler.ProveedorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProveedorConfig {
    @Bean
    public RouterFunction<ServerResponse> Proveedorroutes (ProveedorHandler proveedorHandler){
        return route()
                .GET("/proveedores", proveedorHandler::getProveedores)
                .GET("/proveedores/{id}", proveedorHandler::getProveedorById)
                .POST("/proveedores", proveedorHandler::create)
                .PUT("/proveedores", proveedorHandler::update)
                .DELETE("/proveedores/{id}", proveedorHandler::deleteClienteById)
                .build();
    }
}
