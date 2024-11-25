package org.example.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigProduct {

    @Bean
    public RouterFunction<ServerResponse> productRouter(ProductHandler handler) {
        return route()
                .POST("/products", handler::createProduct)
                .GET("/product/{id}", handler::getProductPorId)
                .GET("/products", handler::listProductos)
                .PUT("/product/{id}", handler::updateProduct)
                .DELETE("/product/{id}", handler::deleteProduct)
                .build();
    }
}
