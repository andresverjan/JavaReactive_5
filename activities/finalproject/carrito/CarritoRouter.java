package org.example.finalproject.carrito;

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
public class CarritoRouter {

    @Bean
    public RouterFunction<ServerResponse> carritoRoutes(CarritoHandler handler) {
        return route(POST("/api/carrito/{usuarioId}/verificar"), handler::verificarOCrearCarrito)
                .andRoute(GET("/api/carrito/listar"), handler::listarCarritos)
                .andRoute(POST("/api/carrito/{carritoId}/agregar"), handler::agregarProducto)
                .andRoute(GET("/api/carrito/{carritoId}/contenido"), handler::obtenerContenido)
                .andRoute(PUT("/api/carrito/{detalleId}/actualizar"), handler::actualizarCantidad)
                .andRoute(DELETE("/api/carrito/{detalleId}/eliminar"), handler::eliminarProducto)
                .andRoute(DELETE("/api/carrito/{carritoId}/vaciar"), handler::vaciarCarrito)
                .andRoute(GET("/api/carrito/{carritoId}/calcular-total"), handler::calcularTotal);
    }
}
