package com.artifactory.crud.component;

import com.artifactory.crud.model.Compra;
import com.artifactory.crud.service.CompraService;
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
public class CompraComponentHandler {

    private CompraService compraService;



    public Mono<ServerResponse> getCompras(ServerRequest request){
        Flux<Compra> compras = compraService.getCompra();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(compras, Compra.class);
    }

    public Mono<ServerResponse> getCompraById(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return compraService.getCompraById(Long.valueOf(id))
                .flatMap(compra -> ServerResponse.ok().bodyValue(compra)
                        .switchIfEmpty(ServerResponse.notFound().build())); }


    public Mono<ServerResponse> realizarCompra(ServerRequest request){
        return request.bodyToMono(Compra.class)
                .flatMap(compraService::realizarCompra)
                .flatMap(createCompra -> ServerResponse.created(URI.create("/compras/" + createCompra.getIdproducto()))
                        .bodyValue(createCompra))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }
    public Mono<ServerResponse> actualizarCompra(ServerRequest request){
        return request.bodyToMono(Compra.class)
                .flatMap(compraService::actualizarCompra)
                .flatMap(createCompra -> ServerResponse.created(URI.create("/compras/" + createCompra.getIdproducto()))
                        .bodyValue(createCompra))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> deleteCompraByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return compraService.deleteCompraById(Long.valueOf(id))
                .flatMap(compra -> ServerResponse.ok().bodyValue(compra)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }


}
