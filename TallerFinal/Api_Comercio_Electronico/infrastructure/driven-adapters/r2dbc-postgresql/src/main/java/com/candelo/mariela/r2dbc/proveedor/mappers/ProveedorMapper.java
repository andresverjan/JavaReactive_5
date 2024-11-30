package com.candelo.mariela.r2dbc.proveedor.mappers;

import com.candelo.mariela.model.proveedor.Proveedor;
import com.candelo.mariela.r2dbc.proveedor.entities.ProveedorEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProveedorMapper {

    public static Proveedor toProveedor(ProveedorEntity entity) {
        return Proveedor.builder()
                .nombre(entity.getNombre())
                .correo(entity.getCorreo())
                .telefono(entity.getTelefono())
                .direccion(entity.getDireccion())
                .documento_type(entity.getDocumento_type())
                .documentNumber(entity.getDocumentNumber())
                .build();
    }

    public static ProveedorEntity toProveedorEntity(Proveedor proveedor) {
        ProveedorEntity entity = new  ProveedorEntity();
        entity.setNombre(proveedor.getNombre());
        entity.setCorreo(proveedor.getCorreo());
        entity.setTelefono(proveedor.getTelefono());
        entity.setDireccion(proveedor.getDireccion());
        entity.setDocumento_type(proveedor.getDocumento_type());
        entity.setDocumentNumber(proveedor.getDocumentNumber());
        return entity;

    }
}
