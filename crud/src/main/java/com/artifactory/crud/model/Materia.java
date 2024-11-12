package com.artifactory.crud.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "materia")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Materia {
    @Id
    private Long id;
    @Column("nombre")
    private String nombre;
    @Column("nota")
    private Double nota;
    @Column("estudiante")
    private Double estudiante;

}
