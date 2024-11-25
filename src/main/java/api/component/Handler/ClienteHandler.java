package api.component.Handler;

import api.model.Cliente;
import api.service.ClienteService;
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
public class ClienteHandler {
    private final ClienteService clienteService;

    public Mono<ServerResponse> getClientes(ServerRequest request){
        Flux<Cliente> clientes = clienteService.getClientes();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientes,Cliente.class);
    }
    public Mono<ServerResponse> getClienteById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return clienteService.buscarPorId(Id)
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Cliente.class)
                .flatMap(clienteService::crear)
                .flatMap(createdPerson ->
                        ServerResponse.created(URI.create("/clientes" + createdPerson.getId()))
                                .bodyValue(createdPerson))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Cliente.class)
                .flatMap(clienteService::actualizar)
                .flatMap(updateCliente -> ServerResponse.ok().bodyValue(updateCliente))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteClienteById(ServerRequest request){
        Long Id = Long.valueOf(request.pathVariable("id"));
        return clienteService.eliminar(Id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("ID que será eliminada" + Id))
                .doOnError(err -> System.out.println("Error al eliminar cliente con Id: " + Id))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
