package com.crud_basico.crud_basico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("materias")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Materia {
    @Id
    private Long id;
    private String nombre;
    private List<Nota> notas;
    @Column("estudiante_id")
    private Long estudianteId;
}
