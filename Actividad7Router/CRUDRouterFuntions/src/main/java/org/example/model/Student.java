package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(schema = "administraciones", name = "\"STUDENT\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("age")
    private Integer age;
    private List<Subject> subjects;

    public Student(long id, String johnDoe, int age, List<Subject> subjects) {
    }
}
