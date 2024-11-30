package com.candelo.mariela.usecase.cliente;

import com.candelo.mariela.model.cliente.Cliente;
import com.candelo.mariela.model.cliente.gateways.ClienteGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteGateway clienteGateway;

    public Mono<Cliente> saveCliente(Cliente cliente) {
        return clienteGateway.saveCliente(cliente);
    }

    public Mono<Cliente> updateCliente(Cliente cliente) {
        return clienteGateway.updateCliente(cliente);
    }

    public Mono<Void> deleteClienteByDocumentNumber(int documentNumber) {
        return clienteGateway.deleteClienteByDocumentNumber(documentNumber);
    }

    public Mono<Cliente> getClienteByDocumentNumber(int documentNumber) {
        return clienteGateway.getClienteByDocumentNumber(documentNumber);
    }


}
