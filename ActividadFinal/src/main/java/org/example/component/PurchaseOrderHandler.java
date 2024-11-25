package org.example.component;

import org.example.model.PurchaseOrder;
import org.example.service.PurchaseOrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class PurchaseOrderHandler {

    private final PurchaseOrderService service;

    public PurchaseOrderHandler(PurchaseOrderService service) {
        this.service = service;
    }

    public Mono<ServerResponse> registerOrder(ServerRequest request) {
        return request.bodyToMono(PurchaseOrder.class)
                .flatMap(service::registerOrder)
                .flatMap(order -> ServerResponse.ok().bodyValue(order))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> listOrders(ServerRequest request) {
        LocalDate dateInitial = LocalDate.parse(request.queryParam("dateInitial").orElse("1970-01-01"));
        LocalDate dateEnd = LocalDate.parse(request.queryParam("dateEnd").orElse(LocalDate.now().toString()));

        return ServerResponse.ok().body(service.listOrder(dateInitial, dateEnd), PurchaseOrder.class);
    }

    public Mono<ServerResponse> listOrderByProveedor(ServerRequest request) {
        String proveedorId = request.pathVariable("proveedorId");
        LocalDate dateInitial = LocalDate.parse(request.queryParam("fechaInicio").orElse("1970-01-01"));
        LocalDate dateEnd = LocalDate.parse(request.queryParam("fechaFin").orElse(LocalDate.now().toString()));

        return ServerResponse.ok().body(service.listOrdersByProveedor(proveedorId, dateInitial, dateEnd), PurchaseOrder.class);
    }

}