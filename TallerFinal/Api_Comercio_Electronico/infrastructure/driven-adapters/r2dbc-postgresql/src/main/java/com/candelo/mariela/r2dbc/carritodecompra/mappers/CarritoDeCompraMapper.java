package com.candelo.mariela.r2dbc.carritodecompra.mappers;

import com.candelo.mariela.model.carritodecompra.CarritoDeCompra;
import com.candelo.mariela.r2dbc.carritodecompra.entities.CarritoDeCompraEntity;

public class CarritoDeCompraMapper {

    public static CarritoDeCompra toModel(CarritoDeCompraEntity entity) {
        return CarritoDeCompra.builder()
                .id(entity.getId())
                .clienteId(entity.getIdUsuario())
                .total(entity.getTotal())
                .build();
    }

    public static CarritoDeCompraEntity toEntity(CarritoDeCompra model) {
        CarritoDeCompraEntity entity = new CarritoDeCompraEntity();
        entity.setId(model.getId());
        entity.setIdUsuario(model.getClienteId());
        entity.setTotal(model.getTotal());
        return entity;
    }
}