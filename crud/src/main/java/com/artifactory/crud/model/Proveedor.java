package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "proveedor")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Proveedor {
    @Id
    private Long idproveedor;
    @Column("nombre")
    private String nombre;
    @Column("correo")
    private String correo;
    @Column("direccion")
    private String direccion;
    @Column("telefono")
    private String telefono;
}
