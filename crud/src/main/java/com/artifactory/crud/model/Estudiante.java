package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
@Table( name = "estudiante")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Estudiante {
    @Id
    private Long id;
    @Column("nombre")
    private String nombre;
    @Column("edad")
    private int edad;
    @Column("materias")
    private List<Materia> materias;
}
