package com.artifactory.crud.component;

import com.artifactory.crud.model.*;
import com.artifactory.crud.service.CarritoService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarritoDetalleComponentHandler {

    private final CarritoService carritoService;

    public CarritoDetalleComponentHandler(CarritoService carritoService) {
        this.carritoService = carritoService;
    }


    public Mono<ServerResponse> crearCarritoNew(ServerRequest request){
        return request.bodyToMono(CarritoDetalleRequest.class)
                .flatMap(createCarrito -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setFecha_creacion(createCarrito.getFecha());

                    List<DetalleCarrito> listDetalleCarrito = createCarrito.getDetalleCarritos();


                    return carritoService.crearCarritoNew(nuevoCarrito,listDetalleCarrito);

                }).flatMap(crear -> ServerResponse.ok().bodyValue(crear))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> crearCarrito(ServerRequest request){
        return request.bodyToMono(CarritoDetalleRequest.class)
                .flatMap(carritoService::CrearCarrito)
                .flatMap(createCarrito -> ServerResponse.created(URI.create("/carrito/" + createCarrito.getIdcarrito()))
                        .bodyValue(createCarrito))
                .switchIfEmpty(ServerResponse.badRequest().build());

    }

    public Mono<ServerResponse> getCarritos(ServerRequest request){
        Flux<CarritoDetalleRequest> carrito = carritoService.getCarrito();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(carrito, CarritoDetalleRequest.class);
    }
    public Mono<ServerResponse> deleteCarritoByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return carritoService.deleteCarritoById(Long.valueOf(id))
                .flatMap(compra -> ServerResponse.ok().bodyValue(compra)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> deleteProductoByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return carritoService.deleteProductoDetalleByid(Long.valueOf(id))
                .flatMap(compra -> ServerResponse.ok().bodyValue(compra)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }
    public Mono<ServerResponse> calcularTotalByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return carritoService.getTotalCompraById(Long.valueOf(id))
                .flatMap(carrito -> ServerResponse.ok().bodyValue(carrito)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> actualizarProducto(ServerRequest request){
        return request.bodyToMono(DetalleCarrito.class)
                .flatMap(carritoService::actualizarCompra)
                .flatMap(createCompra -> ServerResponse.created(URI.create("/actualizarProducto/" + createCompra.getIdproducto()))
                        .bodyValue(createCompra))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }
}
