package org.example.finalproject.proveedor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProveedorHandler {

    private final ProveedorService proveedorService;

    public ProveedorHandler(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    public Mono<ServerResponse> crearProveedor(ServerRequest request) {
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorService::crearProveedor)
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> listarProveedores(ServerRequest request) {
        return ServerResponse.ok().body(proveedorService.listarProveedores(), Proveedor.class);
    }

    public Mono<ServerResponse> actualizarProveedor(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedor -> proveedorService.actualizarProveedor(id, proveedor))
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> agregarProductoAProveedor(ServerRequest request) {
        return request.bodyToMono(ProveedorProducto.class)
                .flatMap(proveedorService::agregarProductoAProveedor)
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> listarProductosDeProveedor(ServerRequest request) {
        Integer proveedorId = Integer.valueOf(request.pathVariable("proveedorId"));
        return ServerResponse.ok().body(proveedorService.listarProductosDeProveedor(proveedorId), ProveedorProducto.class);
    }

    public Mono<ServerResponse> eliminarProveedor(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return proveedorService.eliminarProveedor(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }

    public Mono<ServerResponse> listarTodosLosProductosDeProveedores(ServerRequest request) {
        return proveedorService.listarTodosLosProductosDeProveedores()
                .collectList()
                .flatMap(productos -> ServerResponse.ok().bodyValue(productos))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(Map.of("error", e.getMessage())));
    }
}