package com.candelo.mariela.r2dbc.producto.mappers;

import com.candelo.mariela.model.producto.Producto;
import com.candelo.mariela.r2dbc.producto.entities.ProductoEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductoMapper {

    public static Producto toProducto(ProductoEntity productoEntity) {
        return Producto.builder()
                .id(productoEntity.getId())
                .nombre(productoEntity.getNombre())
                .descripcion(productoEntity.getDescripcion())
                .precioUnitario(productoEntity.getPrecioUnitario())
                .stock(productoEntity.getStock())
                .imagenUrl(productoEntity.getImagenUrl())
                .categoria(productoEntity.getCategoria())
                .createdAt(productoEntity.getCreatedAt())
                .updatedAt(productoEntity.getUpdatedAt())
                .build();
    }

    public static ProductoEntity toProductoEntity(Producto producto) {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(producto.getId());
        entity.setNombre(producto.getNombre());
        entity.setDescripcion(producto.getDescripcion());
        entity.setPrecioUnitario(producto.getPrecioUnitario());
        entity.setStock(producto.getStock());
        entity.setImagenUrl(producto.getImagenUrl());
        entity.setCategoria(producto.getCategoria());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

}
