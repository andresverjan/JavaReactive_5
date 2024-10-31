package curso.java.reactivo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("person")
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
    @Column("date_of_birth")
    private LocalDate dateOfBirth;
    @Column("blood_of_type")
    private String bloodType;

    public Person(long id, String johnDoe, int age) {
    }
}