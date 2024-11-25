package com.artifactory.crud.component;

import com.artifactory.crud.model.Compra;
import com.artifactory.crud.model.Venta;
import com.artifactory.crud.repository.VentaRepository;
import com.artifactory.crud.service.VentaServices;
import com.fasterxml.jackson.databind.node.ValueNode;
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
public class VentaComponentHandler {

    private final VentaServices ventaServices;


    public Mono<ServerResponse> getVentas(ServerRequest request){
        Flux<Venta> ventas = ventaServices.getVentas();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ventas, Venta.class);
    }

    public Mono<ServerResponse> getCompraById(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return ventaServices.getVentaById(Long.valueOf(id))
                .flatMap(compra -> ServerResponse.ok().bodyValue(compra)
                        .switchIfEmpty(ServerResponse.notFound().build())); }


    public Mono<ServerResponse> realizarVenta(ServerRequest request){
        return request.bodyToMono(Venta.class)
                .flatMap(ventaServices::realizarVenta)
                .flatMap(createVenta -> ServerResponse.created(URI.create("/createVenta/" + createVenta.getIdventa()))
                        .bodyValue(createVenta))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> actualizarVenta(ServerRequest request){
        return request.bodyToMono(Venta.class)
                .flatMap(ventaServices::realizarVenta)
                .flatMap(createVenta -> ServerResponse.created(URI.create("/updateventa/" + createVenta.getIdventa()))
                        .bodyValue(createVenta))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> deleteVentaByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return ventaServices.deleteVentaById(Long.valueOf(id))
                .flatMap(venta -> ServerResponse.ok().bodyValue(venta)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }


}
