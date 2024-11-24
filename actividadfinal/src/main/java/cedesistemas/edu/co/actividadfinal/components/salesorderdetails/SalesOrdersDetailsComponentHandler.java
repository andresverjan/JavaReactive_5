package cedesistemas.edu.co.actividadfinal.components.salesorderdetails;

import cedesistemas.edu.co.actividadfinal.interfaces.SalesOrdersDetailsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.SalesOrdersDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SalesOrdersDetailsComponentHandler {

    private final SalesOrdersDetailsServiceInterface salesOrdersDetailsServiceInterface;

    public Mono<ServerResponse> addProductToCart(ServerRequest request) {
        return request.bodyToFlux(SalesOrdersDetails.class)
                .collectList()
                .flatMapMany(Flux::fromIterable)
                .flatMap(details -> salesOrdersDetailsServiceInterface.saveSalesOrderDetails(Flux.just(details)))
                .collectList()
                .flatMap(salesOrderDetails -> ServerResponse.ok().bodyValue(salesOrderDetails));
    }

    public Mono<ServerResponse> deleteProductFromCart(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return salesOrdersDetailsServiceInterface.deleteSalesOrderDetails(id)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> updateProductInCart(ServerRequest request) {
        return request.bodyToMono(SalesOrdersDetails.class)
                .flatMap(salesOrdersDetailsServiceInterface::updateSalesOrderDetails)
                .flatMap(salesOrderDetails -> ServerResponse.ok().bodyValue(salesOrderDetails));
    }
}
