package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "PERSON")
public class Person {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("age")
    private Integer age;
    @Column("gander")
    private String gander;
    @Column("date_of_birth")
    private LocalDate dateOfBirth;
    @Column("blood_type")
    private String bloodType;
}
