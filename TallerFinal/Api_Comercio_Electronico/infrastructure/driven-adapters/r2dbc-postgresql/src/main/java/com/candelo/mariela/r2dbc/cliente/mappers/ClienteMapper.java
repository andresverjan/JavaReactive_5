package com.candelo.mariela.r2dbc.cliente.mappers;

import com.candelo.mariela.model.cliente.Cliente;
import com.candelo.mariela.r2dbc.cliente.entities.ClienteEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteEntity clienteEntity) {
        return Cliente.builder()
                .documentNumber(clienteEntity.getDocumentNumber())
                .nombre(clienteEntity.getNombre())
                .correo(clienteEntity.getCorreo())
                .telefono(clienteEntity.getTelefono())
                .direccion(clienteEntity.getDireccion())
                .documento_type(clienteEntity.getDocumento_type())
                .build();
    }

    public static ClienteEntity toClienteEntity(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setDocumentNumber(cliente.getDocumentNumber());
        entity.setNombre(cliente.getNombre());
        entity.setCorreo(cliente.getCorreo());
        entity.setTelefono(cliente.getTelefono());
        entity.setDireccion(cliente.getDireccion());
        entity.setDocumento_type(cliente.getDocumento_type());
        return entity;
    }
}
