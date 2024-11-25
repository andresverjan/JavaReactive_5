package com.artifactory.crud.component;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.model.Person;
import com.artifactory.crud.service.ClienteService;
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
public class ClienteComponentHandler {

    private ClienteService clienteService;

    public Mono<ServerResponse> getClientes(ServerRequest request){
        Flux<Cliente> cliente = clienteService.getCliente();
        System.out.println(cliente);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cliente, Cliente.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return clienteService.getPersonById(Long.valueOf(id))
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente)
                        .switchIfEmpty(ServerResponse.notFound().build())); }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(Cliente.class)
                .flatMap(clienteService::create)
                .flatMap(createCliente -> ServerResponse.created(URI.create("/cliente/" + createCliente.getIdcliente()))
                        .bodyValue(createCliente))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(Cliente.class)
                .flatMap(clienteService::update)
                .flatMap(updateCliente -> ServerResponse.ok().bodyValue(updateCliente))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteClienteByid(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return clienteService.deleteClienteById(Long.valueOf(id))
                .flatMap(cliente -> ServerResponse.ok().bodyValue(cliente)
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }
}
