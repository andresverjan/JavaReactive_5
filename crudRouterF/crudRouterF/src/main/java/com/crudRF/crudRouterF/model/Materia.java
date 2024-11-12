package com.crudRF.crudRouterF.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "materias")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Materia {
    @Id
    private Long id;
    @Column("nombre")
    private String nombre;

}
