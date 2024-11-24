package cedesistemas.edu.co.actividadfinal.components.suppliers;

import cedesistemas.edu.co.actividadfinal.interfaces.SuppliersServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.Suppliers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SuppliersComponentHandler {

    private final SuppliersServiceInterface suppliersServiceInterface;

    public Mono<ServerResponse> saveSupplier(ServerRequest request) {
        return request.bodyToMono(Suppliers.class)
                .flatMap(suppliersServiceInterface::saveSupplier)
                .flatMap(suppliers -> ServerResponse.ok().bodyValue(suppliers));
    }

    public Mono<ServerResponse> getAllSuppliers(ServerRequest request) {
        return ServerResponse.ok().body(suppliersServiceInterface.getAllSuppliers(), Suppliers.class);
    }

    public Mono<ServerResponse> getSupplierById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(suppliersServiceInterface.getSupplierById(id), Suppliers.class);
    }

    public Mono<ServerResponse> updateSupplier(ServerRequest request) {
        return request.bodyToMono(Suppliers.class)
                .flatMap(suppliersServiceInterface::updateSupplier)
                .flatMap(suppliers -> ServerResponse.ok().bodyValue(suppliers));
    }

    public Mono<ServerResponse> deleteSupplier(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return suppliersServiceInterface.deleteSupplier(id)
                .then(ServerResponse.ok().build());
    }
}
