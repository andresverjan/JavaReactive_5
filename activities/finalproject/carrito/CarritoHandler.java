package org.example.finalproject.carrito;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CarritoHandler {

    private final CarritoService carritoService;

    public CarritoHandler(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    public Mono<ServerResponse> verificarOCrearCarrito(ServerRequest request) {
        Integer usuarioId = Integer.valueOf(request.pathVariable("usuarioId"));
        return carritoService.verificarOCrearCarrito(usuarioId)
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> agregarProducto(ServerRequest request) {
        Integer carritoId = Integer.valueOf(request.pathVariable("carritoId"));
        return request.bodyToMono(DetalleCarrito.class)
                .flatMap(detalle -> carritoService.agregarProducto(carritoId, detalle))
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }
    public Mono<ServerResponse> obtenerContenido(ServerRequest request) {
        Integer carritoId = Integer.valueOf(request.pathVariable("carritoId"));
        return ServerResponse.ok().body(carritoService.obtenerContenido(carritoId), DetalleCarrito.class);
    }

    public Mono<ServerResponse> listarCarritos(ServerRequest request) {
        return ServerResponse.ok()
                .body(carritoService.listarCarritos(), Carrito.class); // Devuelve todos los carritos
    }

    public Mono<ServerResponse> actualizarCantidad(ServerRequest request) {
        Integer detalleId = Integer.valueOf(request.pathVariable("detalleId"));
        return request.bodyToMono(Map.class) // Leer el JSON como un mapa
                .flatMap(body -> {
                    Integer cantidad = (Integer) body.get("cantidad"); // Obtener el valor del campo cantidad
                    return carritoService.actualizarCantidad(detalleId, cantidad);
                })
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }
    public Mono<ServerResponse> eliminarProducto(ServerRequest request) {
        Integer detalleId = Integer.valueOf(request.pathVariable("detalleId"));
        return carritoService.eliminarProducto(detalleId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> vaciarCarrito(ServerRequest request) {
        Integer carritoId = Integer.valueOf(request.pathVariable("carritoId"));
        return carritoService.vaciarCarrito(carritoId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> calcularTotal(ServerRequest request) {
        Integer carritoId = Integer.valueOf(request.pathVariable("carritoId"));
        return carritoService.calcularTotal(carritoId)
                .flatMap(total -> ServerResponse.ok().bodyValue(Map.of("total", total)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }
}