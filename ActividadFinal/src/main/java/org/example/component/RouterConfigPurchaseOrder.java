package org.example.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigPurchaseOrder {

    @Bean
    public RouterFunction<ServerResponse> routes(PurchaseOrderHandler handler) {
        return route()
                .POST("/purchase-order", handler::registerOrder)
                .GET("/purchase-order", handler::listOrders)
                .GET("/purchase-order/proveedor/{proveedorId}", handler::listOrderByProveedor)
                .build();
    }
}
