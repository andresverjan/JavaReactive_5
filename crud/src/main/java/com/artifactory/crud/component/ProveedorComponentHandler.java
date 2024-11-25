package com.artifactory.crud.component;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.model.Producto;
import com.artifactory.crud.model.Proveedor;
import com.artifactory.crud.service.ProductoService;
import com.artifactory.crud.service.ProveedorService;
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
public class ProveedorComponentHandler {

    private ProveedorService proveedorService;

    public Mono<ServerResponse> getProveedor(ServerRequest request){
        Flux<Proveedor> proveedor = proveedorService.getProveedor();
        System.out.println(proveedor);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(proveedor, Cliente.class);
    }

    public Mono<ServerResponse> getProveedoryId(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return proveedorService.getProveedorById(Long.valueOf(id))
                .flatMap(proveedor -> ServerResponse.ok().bodyValue(proveedor)
                        .switchIfEmpty(ServerResponse.notFound().build())); }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorService::create)
                .flatMap(createProveedor -> ServerResponse.created(URI.create("/producto/" + createProveedor.getIdproveedor()))
                        .bodyValue(createProveedor))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorService::update)
                .flatMap(updateProveedor -> ServerResponse.ok().bodyValue(updateProveedor))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteProveedorByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return proveedorService.deleteProveedorById(Long.valueOf(id))
                .flatMap(proveedor -> ServerResponse.ok().bodyValue(proveedor)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }
}
