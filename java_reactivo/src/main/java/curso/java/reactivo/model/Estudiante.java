package curso.java.reactivo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
@Table("estudiante")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Estudiante {
    @Id
    private Long id;
    private String nombre;
    private int edad;
}
