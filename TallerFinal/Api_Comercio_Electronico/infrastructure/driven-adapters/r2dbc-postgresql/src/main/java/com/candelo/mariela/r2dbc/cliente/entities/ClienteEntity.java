package com.candelo.mariela.r2dbc.cliente.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table("cliente")
public class ClienteEntity {

    @Id
    @Column("id")
    private int documentNumber;

    @Column("nombre")
    private String nombre;

    @Column("correo")
    private String correo;

    @Column("telefono")
    private String telefono;

    @Column("direccion")
    private String direccion;

    @Column("documento_type")
    private String documento_type;


}
