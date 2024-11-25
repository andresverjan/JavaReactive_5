package org.example.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigCart {

    @Bean
    public RouterFunction<ServerResponse> routes(CartHandler cartHandler) {
        return route()
                .POST("/carts/{clientId}/products/{productId}/{quantity}", cartHandler::addProduct)
                .DELETE("/carts/{clientId}/products/{productId}", cartHandler::deleteProduct)
                .DELETE("/carts/{clientId}", cartHandler::emptyCart)
                .GET("/carts/{clientId}", cartHandler::getCart)
                .GET("/carts/{clientId}/total", cartHandler::calculateTotal)
                .build();
    }
}
