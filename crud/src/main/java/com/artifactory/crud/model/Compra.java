package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "compra")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Compra {

    @Id
    private Long idcompra;
    @Column("idproducto")
    private Long idproducto;
    @Column("idproveedor")
    private Long idproveedor;
    @Column("fecha")
    private String fecha;
    @Column("cantidad")
    private int cantidad;
    @Column("estado")
    private boolean estado;

}
