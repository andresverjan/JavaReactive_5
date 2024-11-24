package cedesistemas.edu.co.actividadfinal.components;

import cedesistemas.edu.co.actividadfinal.components.products.ProductsComponentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductsComponentHandler handler) {

        return RouterFunctions
                .nest(RequestPredicates.path("/router-products"),
                        RouterFunctions
                                .route(RequestPredicates.GET("/get-all"), handler::getAllProducts)
                                .andRoute(RequestPredicates.GET("/get-by-id/{id}"), handler::getProductById)
                                .andRoute(RequestPredicates.GET("/get-by-name/{name}"), handler::getProductByName)
                                .andRoute(RequestPredicates.POST("/save"), handler::saveProduct)
                                .andRoute(RequestPredicates.PUT("/update"), handler::updateProduct)
                                .andRoute(RequestPredicates.DELETE("/delete/{id}"), handler::deleteProduct)
                );
    }
}
