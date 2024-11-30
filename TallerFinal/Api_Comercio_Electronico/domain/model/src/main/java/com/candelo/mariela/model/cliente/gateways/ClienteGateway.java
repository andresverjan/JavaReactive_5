package com.candelo.mariela.model.cliente.gateways;

import com.candelo.mariela.model.cliente.Cliente;
import reactor.core.publisher.Mono;

public interface ClienteGateway {

    Mono<Cliente> saveCliente(Cliente cliente);

    Mono<Cliente> updateCliente(Cliente cliente);

    Mono<Void> deleteClienteByDocumentNumber(int documentNumber);

    Mono<Cliente> getClienteByDocumentNumber(int documentNumber);
}
