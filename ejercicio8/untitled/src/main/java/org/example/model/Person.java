package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("PERSON")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("age")
    private Integer age;
    @Column("gender")
    private String gender;
    @Column("dateofbirth")
    private LocalDate birthdate;
    @Column("bloodtype")
    private String bloodtype;

}