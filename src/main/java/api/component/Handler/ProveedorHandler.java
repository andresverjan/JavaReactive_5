package api.component.Handler;

import api.model.Cliente;
import api.model.Proveedor;
import api.service.ClienteService;
import api.service.ProveedorService;
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
public class ProveedorHandler {
    private final ProveedorService proveedorService;

    public Mono<ServerResponse> getProveedores(ServerRequest request){
        Flux<Proveedor> proveedores = proveedorService.getProveedores();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(proveedores,Proveedor.class);
    }
    public Mono<ServerResponse> getProveedorById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return proveedorService.buscarPorId(Id)
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorService::crear)
                .flatMap(createdPerson ->
                        ServerResponse.created(URI.create("/proveedores" + createdPerson.getId()))
                                .bodyValue(createdPerson))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Proveedor.class)
                .flatMap(proveedorService::actualizar)
                .flatMap(updateCliente -> ServerResponse.ok().bodyValue(updateCliente))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteClienteById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return proveedorService.eliminar(Id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("ID que será eliminada" + Id))
                .doOnError(err -> System.out.println("Error al eliminar proveedor con Id: " + Id))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
