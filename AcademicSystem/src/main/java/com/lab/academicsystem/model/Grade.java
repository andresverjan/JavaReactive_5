package com.lab.academicsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("GRADE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    private Long id;

    @Column("value")
    private double value;
    @Column("id_student")
    private Long studentId;
    @Column("id_subject")
    private Long subjectId;
}
