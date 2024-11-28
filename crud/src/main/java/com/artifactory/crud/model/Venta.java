package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table( name = "ventas")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Venta {

    @Id
    private Long idventa;
    @Column("idcliente")
    private Long idcliente;
    @Column("idcarrito")
    private Long idcarrito;
    @Column("totalventa")
    private Double totalventa;
    @Column("fecha")
    private LocalDate fecha;

}
