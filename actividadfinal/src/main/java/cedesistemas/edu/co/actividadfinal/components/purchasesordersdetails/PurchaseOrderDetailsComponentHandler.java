package cedesistemas.edu.co.actividadfinal.components.purchasesordersdetails;

import cedesistemas.edu.co.actividadfinal.interfaces.PurchaseOrderDetailsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.PurchaseOrdersDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PurchaseOrderDetailsComponentHandler {

    private final PurchaseOrderDetailsServiceInterface purchaseOrderDetailsServiceInterface;

    public Mono<ServerResponse> savePurchaseOrderDetails(ServerRequest request) {
        return request.bodyToMono(PurchaseOrdersDetails.class)
                .flatMap(purchaseOrderDetailsServiceInterface::savePurchaseOrderDetails)
                .flatMap(purchaseOrderDetails -> ServerResponse.ok().bodyValue(purchaseOrderDetails));
    }

    public Mono<ServerResponse> getPurchaseOrderDetails(ServerRequest request) {
        return ServerResponse.ok().body(purchaseOrderDetailsServiceInterface.getPurchaseOrderDetails(), PurchaseOrdersDetails.class);
    }

    public Mono<ServerResponse> getPurchaseOrderDetailsById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(purchaseOrderDetailsServiceInterface.getPurchaseOrderDetailsById(id), PurchaseOrdersDetails.class);
    }

    public Mono<ServerResponse> updatePurchaseOrderDetails(ServerRequest request) {
        return request.bodyToMono(PurchaseOrdersDetails.class)
                .flatMap(purchaseOrderDetailsServiceInterface::updatePurchaseOrderDetails)
                .flatMap(purchaseOrderDetails -> ServerResponse.ok().bodyValue(purchaseOrderDetails));
    }

    public Mono<ServerResponse> deletePurchaseOrderDetails(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return purchaseOrderDetailsServiceInterface.deletePurchaseOrderDetails(id)
                .then(ServerResponse.ok().build());
    }
}
