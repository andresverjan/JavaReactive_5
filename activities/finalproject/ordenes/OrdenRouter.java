package org.example.finalproject.ordenes;

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
public class OrdenRouter {

    @Bean
    public RouterFunction<ServerResponse> ordenRoutes(OrdenHandler handler) {
        return route(GET("/api/ordenes/{ordenId}/detalles"), handler::listarDetallesPorOrdenId)
                .andRoute(POST("/api/ordenes/venta/{carritoId}"), handler::crearOrdenVentaDesdeCarrito)
                .andRoute(PUT("/api/ordenes/venta/editar/{ordenId}/{productoId}"), handler::editarCantidadProducto)
                .andRoute(PUT("/api/ordenes/venta/{ordenId}/confirmar"), handler::confirmarOrdenVenta)
                .andRoute(GET("/api/ordenes/{tipoOrden}"), handler::listarOrdenesPorTipo)
                .andRoute(POST("/api/ordenes/compras/{carritoId}"), handler::registrarCompraDesdeCarrito)
                .andRoute(DELETE("/api/ordenes/{ordenId}/eliminar"), handler::eliminarOrden)
                .andRoute(PUT("/api/ordenes/{ordenId}/cancelar"), handler::cancelarOrden)
                .andRoute(POST("/api/ordenes/compras/{ordenId}/confirmar"), handler::confirmarOrdenCompra)
                .andRoute(GET("/api/ordenes/detalles/producto/{productoId}"), handler::listarDetallesPorProducto)


                ;
    }
}
