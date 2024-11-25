package api.component.Handler;

import api.model.Producto;
import api.service.ProductoService;
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
public class ProductoHandler {
    private final ProductoService productoService;

    public Mono<ServerResponse> getProductos(ServerRequest request) {
        Flux<Producto> productos = productoService.getProductos();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productos, Producto.class);
    }

    public Mono<ServerResponse> getProductoById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productoService.buscarPorId(id)
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::crear)
                .flatMap(createdProducto ->
                        ServerResponse.created(URI.create("/productos/" + createdProducto.getId()))
                                .bodyValue(createdProducto))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::actualizar)
                .flatMap(updatedProducto -> ServerResponse.ok().bodyValue(updatedProducto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productoService.eliminar(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
