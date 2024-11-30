package com.candelo.mariela.r2dbc.producto.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Table("producto")
public class ProductoEntity {

        @Id
        @Column("id")
        private UUID id;

        @Column("nombre")
        private String nombre;

        @Column("descripcion")
        private String descripcion;

        @Column("precio_unitario")
        private int precioUnitario;

        @Column("stock")
        private int stock;

        @Column("imagen_url")
        private String imagenUrl;

        @Column("categoria")
        private String categoria;

        @Column("created_at")
        private LocalDateTime createdAt;

        @Column("updated_at")
        private LocalDateTime updatedAt;

}