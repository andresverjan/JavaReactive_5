package api.component.Handler;
import api.model.DetalleOrdenCompra;
import api.model.OrdenCompra;
import api.model.OrdenVenta;
import api.model.Request.OrdenCompraRequest;
import api.service.ClienteService;
import api.service.OrdenCompraService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@AllArgsConstructor
public class OrdenCompraHandler {
    private final OrdenCompraService ordenCompraService;

    public Mono<ServerResponse> getoOrdenes(ServerRequest request){
        Flux<OrdenCompra> ordenes = ordenCompraService.getOrdenes();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ordenes,OrdenVenta.class);
    }

    // Crear una nueva orden de compra
    public Mono<ServerResponse> crearOrdenCompra(ServerRequest request) {
        return request.bodyToMono(OrdenCompraRequest.class)
                .flatMap(payload -> {
                    if (payload.getProveedorId() == null) {
                        return Mono.error(new IllegalArgumentException("El parámetro proveedorId es requerido"));
                    }
                    if (payload.getDetalles() == null || payload.getDetalles().isEmpty()) {
                        return Mono.error(new IllegalArgumentException("Los detalles de la orden son requeridos"));
                    }

                    // Calcular el precio total
                    double totalPrice = payload.getDetalles().stream()
                            .mapToDouble(detalle -> detalle.getUnitPrice() * detalle.getQuantity())
                            .sum();

                    // Llamar al servicio para crear la orden de compra
                    return ordenCompraService.crearOrdenCompra(payload.getProveedorId(), payload.getDetalles(), totalPrice);
                })
                .flatMap(orden -> ServerResponse.ok().bodyValue(orden))
                .onErrorResume(e -> {
                    e.printStackTrace(); // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Cancelar una orden de compra
    public Mono<ServerResponse> cancelarOrdenCompra(ServerRequest request) {
        Long ordenCompraId = Long.valueOf(request.pathVariable("id"));
        return ordenCompraService.cancelarOrdenCompra(ordenCompraId)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    // Completar una orden de compra
    public Mono<ServerResponse> completarOrdenCompra(ServerRequest request) {
        Long ordenCompraId = Long.valueOf(request.pathVariable("id"));
        return ordenCompraService.completarOrdenCompra(ordenCompraId)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    // Listar contenido de la orden de compra
    public Mono<ServerResponse> listarContenidoOrdenCompra(ServerRequest request) {
        Long ordenCompraId = Long.valueOf(request.pathVariable("ordenCompraId"));

        return ordenCompraService.listarContenidoOrdenCompra(ordenCompraId)
                .flatMap(contenido -> ServerResponse.ok().bodyValue(contenido))
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }
}
