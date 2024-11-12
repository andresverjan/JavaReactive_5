package com.lab.academicsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("SUBJECT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    private Long id;
    @Column("name")
    private String name;

}
