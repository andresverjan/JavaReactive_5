package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "administraciones", name = "\"SUBJECT\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subject {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("note")
    private Double note;

    public Subject(long id, String johnDoe, int age) {
    }
}
