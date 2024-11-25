package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "producto")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Producto {
    @Id
    private Long idproducto;
    @Column("nombre")
    private String nombre;
    @Column("descripcion")
    private String descripcion;
    @Column("precio")
    private Long precio;
    @Column("stock")
    private int stock;
    @Column("categoria")
    private String categoria;

}
