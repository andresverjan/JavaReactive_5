package com.artifactory.crud.component;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.model.Producto;
import com.artifactory.crud.service.ProductoService;
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
public class ProductoComponentHandler {

    private ProductoService productoService;

    public Mono<ServerResponse> getProductos(ServerRequest request){
        Flux<Producto> producto = productoService.getProducto();
        System.out.println(producto);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(producto, Cliente.class);
    }

    public Mono<ServerResponse> getProductoById(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return productoService.getProductoById(Long.valueOf(id))
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto)
                        .switchIfEmpty(ServerResponse.notFound().build())); }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::create)
                .flatMap(createProducto -> ServerResponse.created(URI.create("/producto/" + createProducto.getIdproducto()))
                        .bodyValue(createProducto))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::update)
                .flatMap(updateProducto -> ServerResponse.ok().bodyValue(updateProducto))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteProductoByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return productoService.deleteClienteById(Long.valueOf(id))
                .flatMap(producto -> ServerResponse.ok().bodyValue(producto)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }
}
