package com.candelo.mariela.r2dbc.item.mappers;

import com.candelo.mariela.model.item.Item;
import com.candelo.mariela.r2dbc.item.entities.ItemEntity;

public class ItemMapper {

    public static Item toModel(ItemEntity entity) {
        return Item.builder()
                .id(entity.getId())
                .carritoId(entity.getCarritoId())
                .productId(entity.getProductId())
                .name(entity.getName())
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .total(entity.getTotal())
                .build();
    }

    public static ItemEntity toEntity(Item model) {
        ItemEntity entity = new ItemEntity();
        entity.setId(model.getId());
        entity.setCarritoId(model.getCarritoId());
        entity.setProductId(model.getProductId());
        entity.setName(model.getName());
        entity.setCantidad(model.getCantidad());
        entity.setPrecioUnitario(model.getPrecioUnitario());
        entity.setTotal(model.getTotal());
        return entity;
    }
}
