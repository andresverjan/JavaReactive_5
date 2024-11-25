package api.component.Handler;

import api.model.DetalleCarrito;
import api.model.Request.ModificarCarritoRequest;
import api.model.Request.OrdenCompraRequest;
import api.service.CarritoService;
import api.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class CarritoHandler {
    private final CarritoService carritoService;

    // Buscar carrito por ID
    public Mono<ServerResponse> BuscarPorId(ServerRequest request) {
        Long Id = Long.valueOf(request.pathVariable("id"));
        return  carritoService.BuscarPorId(Id)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
    public Mono<ServerResponse> listarContenidoCarrito(ServerRequest request) {
        Long carritoId = Long.valueOf(request.pathVariable("carritoId"));

        return carritoService.listarContenidoCarrito(carritoId)
                .flatMap(contenido -> ServerResponse.ok().bodyValue(contenido))
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Agregar producto al carrito
    public Mono<ServerResponse> agregarProducto(ServerRequest request) {
        return request.bodyToMono(DetalleCarrito.class)
                .flatMap(payload -> carritoService.agregarProducto(
                        payload.getCarritoId(),
                        payload.getProductId(),
                        payload.getQuantity()
                ))
                .flatMap(carrito -> ServerResponse.ok().bodyValue(carrito))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    // Eliminar producto del carrito
    public Mono<ServerResponse> eliminarProducto(ServerRequest request) {
        Long carritoId = Long.valueOf(request.pathVariable("carritoId"));
        Long productId = Long.valueOf(request.pathVariable("productId"));

        return carritoService.eliminarProducto(carritoId, productId)
                .flatMap(carrito -> ServerResponse.ok().bodyValue(carrito))
                .onErrorResume(e -> {
                    // Manejar errores y devolver mensaje adecuado
                    e.printStackTrace();
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Modificar la cantidad de un producto en el carrito
    public Mono<ServerResponse> modificarCantidadProducto(ServerRequest request) {
        return request.bodyToMono(ModificarCarritoRequest.class)
                .flatMap(payload -> {
                    // Validar que los parámetros no sean nulos
                    if (payload.getCarritoId() == null) {
                        return Mono.error(new IllegalArgumentException("El parámetro carritoId es requerido"));
                    }
                    if (payload.getProductId() == null) {
                        return Mono.error(new IllegalArgumentException("El parámetro productId es requerido"));
                    }
                    if (payload.getCantidad() == null || payload.getCantidad() <= 0) {
                        return Mono.error(new IllegalArgumentException("La cantidad debe ser mayor que 0"));
                    }

                    return carritoService.modificarCantidadProducto(payload.getCarritoId(), payload.getProductId(), payload.getCantidad());
                })
                .flatMap(carrito -> ServerResponse.ok().bodyValue(carrito))  // Retornamos el carrito actualizado
                .onErrorResume(e -> {
                    // Manejo de errores
                    e.printStackTrace();
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    public Mono<ServerResponse> vaciarCarrito(ServerRequest request) {
        Long carritoId = Long.valueOf(request.pathVariable("carritoId"));

        return carritoService.vaciarCarrito(carritoId)
                .flatMap(carrito -> ServerResponse.ok().bodyValue(carrito))
                .onErrorResume(e -> {
                    e.printStackTrace(); // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

    // Comprar carrito
    public Mono<ServerResponse> comprarCarrito(ServerRequest request) {
        Long carritoId = Long.valueOf(request.pathVariable("carritoId"));

        return carritoService.comprarCarrito(carritoId)
                .then(ServerResponse.ok().build())
                .onErrorResume(e -> {
                    e.printStackTrace();  // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }
}
