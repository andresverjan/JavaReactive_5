package com.cedesistemas.crudrouterfunctions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "programacionreactiva", name = "\"STUDENTS\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    private Integer id;
    @Column("nombre")
    private String nombre;
    @Column("edad")
    private int edad;
    @Column("fecha_nacimiento")
    private String fecha_nacimiento;
}
