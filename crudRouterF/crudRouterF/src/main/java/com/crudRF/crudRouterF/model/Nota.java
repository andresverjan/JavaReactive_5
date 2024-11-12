package com.crudRF.crudRouterF.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "nota")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nota {
    @Id
    private Long id;
    @Column("estudiante_id")
    private Long estudianteId;
    @Column("materia_id")
    private Long materiaId;
    @Column("valor")
    private Double valor;
}
