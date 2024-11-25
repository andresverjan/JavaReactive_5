package com.artifactory.crud.service;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.repository.ClienteRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    public Flux<Cliente> getCliente(){
        return clienteRepository.findAll()
                .doOnNext(cliente -> System.out.println(" Data " + cliente) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

    public Mono<Cliente> getPersonById(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return clienteRepository.findById(id)
                .doOnNext(person -> System.out.println(" Data getPersonByid " + person) );
    }

    public Mono<Void> deleteClienteById(Long id){

        return clienteRepository.deleteById(id)
                .doOnNext(p-> System.out.println("Borrado :: " + id))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Cliente> create(Cliente cliente){

        return clienteRepository.save(cliente)
                .doOnNext(p-> System.out.println("creado :: " + p))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Cliente> update(Cliente cliente){

        return clienteRepository.findById(cliente.getIdcliente())
                .flatMap(existing -> {
                    existing.setNombre(cliente.getNombre());
                    existing.setCorreo(cliente.getCorreo());
                    existing.setDireccion(cliente.getDireccion());
                    existing.setTelefono(cliente.getTelefono());
                    return clienteRepository.save(existing)
                            .doOnNext(updatedPerson -> System.out.println("Person updated: " + updatedPerson));
                })
                .switchIfEmpty(Mono.error(new Exception("Person not found with ID: " + cliente.getIdcliente())));
    }

    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                        Mono.from(connection.createStatement("select * from person ").execute())
                                .doOnNext(result -> System.out.println("Connection successful!"))
                                .doFinally(signalType -> connection.close())        )
                .then();
    }
}
