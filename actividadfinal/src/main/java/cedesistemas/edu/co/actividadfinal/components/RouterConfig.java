package cedesistemas.edu.co.actividadfinal.components;

import cedesistemas.edu.co.actividadfinal.components.cart.CartComponentHandler;
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

    @Bean
    public RouterFunction<ServerResponse> routesCart(CartComponentHandler handler) {

        return RouterFunctions
                .nest(RequestPredicates.path("/router-cart"),
                        RouterFunctions
                                .route(RequestPredicates.POST("/add-product"), handler::addProductToCart)
                                .andRoute(RequestPredicates.DELETE("/delete-product/{id}"), handler::deleteProductFromCart)
                                .andRoute(RequestPredicates.PUT("/update-product"), handler::updateProductInCart)
                                .andRoute(RequestPredicates.GET("/get-all-products/{carritoId}"), handler::getAllProductsInCart)
                                .andRoute(RequestPredicates.DELETE("/delete-all-products/{carritoId}"), handler::deleteAllProductsInCart)
                                .andRoute(RequestPredicates.GET("/get-product-by-id/{id}"), handler::getProductInCartById)
                );
    }
}
