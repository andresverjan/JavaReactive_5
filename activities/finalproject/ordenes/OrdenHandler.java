package org.example.finalproject.ordenes;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class OrdenHandler {

    private final OrdenService ordenService;

    public OrdenHandler(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Crear una orden de venta desde el carrito
    public Mono<ServerResponse> crearOrdenVentaDesdeCarrito(ServerRequest request) {
        Integer carritoId = Integer.valueOf(request.pathVariable("carritoId"));
        return ordenService.crearOrdenVentaDesdeCarrito(carritoId)
                .flatMap(orden -> ServerResponse.ok().bodyValue(orden))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    // Editar la cantidad de un producto en una orden de venta
    public Mono<ServerResponse> editarCantidadProducto(ServerRequest request) {
        Integer ordenId = Integer.valueOf(request.pathVariable("ordenId"));
        Integer productoId = Integer.valueOf(request.pathVariable("productoId"));
        Integer nuevaCantidad = Integer.valueOf(request.queryParam("cantidad")
                .orElseThrow(() -> new IllegalArgumentException("La cantidad es requerida.")));

        return ordenService.editarCantidadProducto(ordenId, productoId, nuevaCantidad)
                .flatMap(detalle -> ServerResponse.ok().bodyValue(detalle))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    // Confirmar una orden de venta
    public Mono<ServerResponse> confirmarOrdenVenta(ServerRequest request) {
        Integer ordenId = Integer.valueOf(request.pathVariable("ordenId"));
        return ordenService.confirmarOrdenVenta(ordenId)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> registrarCompraDesdeCarrito(ServerRequest request) {
        Integer carritoId = Integer.valueOf(request.pathVariable("carritoId"));
        return ordenService.registrarCompraDesdeCarrito(carritoId)
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> confirmarOrdenCompra(ServerRequest request) {
        Integer ordenId = Integer.valueOf(request.pathVariable("ordenId"));

        return ordenService.confirmarOrdenCompra(ordenId)
                .flatMap(respuesta -> ServerResponse.ok().bodyValue(respuesta))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }


    public Mono<ServerResponse> listarDetallesPorOrdenId(ServerRequest request) {
        Integer ordenId = Integer.valueOf(request.pathVariable("ordenId"));
        return ordenService.listarDetallesPorOrdenId(ordenId)
                .collectList()
                .flatMap(detalles -> ServerResponse.ok().bodyValue(detalles))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> cancelarOrden(ServerRequest request) {
        Integer ordenId = Integer.valueOf(request.pathVariable("ordenId"));
        return ordenService.cancelarOrden(ordenId)
                .flatMap(orden -> ServerResponse.ok().bodyValue(orden))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> listarOrdenesPorTipo(ServerRequest request) {
        String tipoOrden = request.pathVariable("tipoOrden").toUpperCase();
        return ServerResponse.ok().body(ordenService.listarOrdenesPorTipo(tipoOrden), Orden.class);
    }

    public Mono<ServerResponse> listarDetallesPorProducto(ServerRequest request) {
        Integer productoId = Integer.valueOf(request.pathVariable("productoId"));
        return ServerResponse.ok()
                .body(ordenService.listarDetallesPorProducto(productoId), DetalleOrden.class);
    }

    public Mono<ServerResponse> eliminarOrden(ServerRequest request) {
        Integer ordenId = Integer.valueOf(request.pathVariable("ordenId"));
        return ordenService.eliminarOrden(ordenId)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

}
