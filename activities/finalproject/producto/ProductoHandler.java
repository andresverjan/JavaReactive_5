package org.example.finalproject.producto;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProductoHandler {

    private final ProductoService productoService;

    public ProductoHandler(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Mono<ServerResponse> crearProducto(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::crearProducto)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> editarProducto(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Producto.class)
                .flatMap(producto -> productoService.editarProducto(id, producto))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> listarProductos(ServerRequest request) {
        return ServerResponse.ok().body(productoService.listarProductos(), Producto.class);
    }

    public Mono<ServerResponse> buscarPorId(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return productoService.buscarPorId(id)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarPorNombre(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        return ServerResponse.ok().body(productoService.buscarPorNombre(name), Producto.class);
    }

    public Mono<ServerResponse> modificarStock(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Map.class)
                .flatMap(body -> {
                    if (body.containsKey("stock")) {
                        Integer nuevoStock = (Integer) body.get("stock");
                        return productoService.modificarStock(id, nuevoStock)
                                .flatMap(ServerResponse.ok()::bodyValue);
                    } else {
                        return ServerResponse.badRequest().bodyValue(Map.of("error", "El campo 'stock' es obligatorio."));
                    }
                })
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> eliminarProducto(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return productoService.eliminarProducto(id)
                .then(ServerResponse.noContent().build());
    }
}
