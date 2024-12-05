package com.candelo.mariela.r2dbc.item.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("item")
public class ItemEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("carrito_id")
    private UUID carritoId;

    @Column("product_id")
    private UUID productId;

    @Column("name")
    private String name;

    @Column("cantidad")
    private int cantidad;

    @Column("precio_unitario")
    private double precioUnitario;

    @Column("total")
    private double total;
}
