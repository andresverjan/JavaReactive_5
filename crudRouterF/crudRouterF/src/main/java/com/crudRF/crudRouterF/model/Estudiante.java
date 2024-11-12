package com.crudRF.crudRouterF.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;


@Table(name = "estudiantes")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Estudiante {
    @Id
    private Long Id;
    private String nombre;
    private Integer edad;
}
