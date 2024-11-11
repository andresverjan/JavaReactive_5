package com.cedesistemas.crudrouterfunctions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSubjectGrade {

    private String estudiante;
    private String materia;
    private double nota;
    private String descripcion_nota;
}
