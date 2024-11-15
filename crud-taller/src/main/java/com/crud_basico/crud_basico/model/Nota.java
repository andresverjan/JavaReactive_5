package com.crud_basico.crud_basico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("notas")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Nota {

    @Id
    private Long id;
    private Double valor;
    @Column("materia_id")
    private Long materiaId;
}
