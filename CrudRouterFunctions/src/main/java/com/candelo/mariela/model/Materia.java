package com.candelo.mariela.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(schema = "programacionreactiva", name = "\"MATERIAS\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Materia {

  @Id
    private Integer id;
    @Column("nombre")
    private String nombre;
    @Column("descripcion")
    private String descripcion;
  
}
