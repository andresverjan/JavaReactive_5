package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "cliente")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Cliente {
    @Id
    private Long idcliente;
    @Column("nombre")
    private String nombre;
    @Column("correo")
    private String correo;
    @Column("direccion")
    private String direccion;
    @Column("telefono")
    private String telefono;

}
