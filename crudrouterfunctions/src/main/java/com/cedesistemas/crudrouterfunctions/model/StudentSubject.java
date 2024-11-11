package com.cedesistemas.crudrouterfunctions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "programacionreactiva", name = "\"STUDENTS_SUBJECTS\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSubject {
    @Id
    private Integer id;
    @Column("estudiante_id")
    private Integer estudiante_id;
    @Column("materia_id")
    private Integer materia_id;
}
