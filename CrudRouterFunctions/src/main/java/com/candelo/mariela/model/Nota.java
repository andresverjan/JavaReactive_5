package com.candelo.mariela.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "programacionreactiva", name = "\"NOTAS\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Nota {
    @Id
    private Integer id;
    @Column("estudiante_materia_id")
    private Integer estudiante_materia_id;
    @Column("nota")
    private double nota;
    @Column("descripcion")
    private String descripcion;
}