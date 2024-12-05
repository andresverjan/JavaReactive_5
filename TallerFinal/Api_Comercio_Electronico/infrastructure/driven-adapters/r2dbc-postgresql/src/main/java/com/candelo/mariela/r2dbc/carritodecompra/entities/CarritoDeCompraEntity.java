package com.candelo.mariela.r2dbc.carritodecompra.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("carrito_de_compra")
public class CarritoDeCompraEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("id_usuario")
    private int idUsuario;

    @Column("total")
    private Double total;


}
