package com.candelo.mariela.r2dbc.cliente.adapters;

import com.candelo.mariela.model.cliente.Cliente;
import com.candelo.mariela.model.cliente.gateways.ClienteGateway;
import com.candelo.mariela.r2dbc.cliente.mappers.ClienteMapper;
import com.candelo.mariela.r2dbc.cliente.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    @Override
    public Mono<Cliente> saveCliente(Cliente cliente) {
        var clienteEntity = ClienteMapper.toClienteEntity(cliente);
        return clienteRepository.saveCliente(clienteEntity.getDocumentNumber(), clienteEntity.getNombre(),
                clienteEntity.getCorreo(), clienteEntity.getTelefono(), clienteEntity.getDireccion(),
                clienteEntity.getDocumento_type())
                .map(ClienteMapper::toCliente)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al guardar el cliente" + error.getMessage())));
    }

    @Override
    public Mono<Cliente> updateCliente(Cliente cliente) {
        var clienteEntity = ClienteMapper.toClienteEntity(cliente);
        return clienteRepository.save(clienteEntity)
                .map(ClienteMapper::toCliente)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al actualizar el cliente")));
    }

    @Override
    public Mono<Void> deleteClienteByDocumentNumber(int documentNumber) {
        return clienteRepository.deleteById(documentNumber)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al eliminar el cliente")));
    }

    @Override
    public Mono<Cliente> getClienteByDocumentNumber(int documentNumber) {
        return clienteRepository.findById(documentNumber)
                .map(ClienteMapper::toCliente)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al obtener el cliente")));
    }

}
