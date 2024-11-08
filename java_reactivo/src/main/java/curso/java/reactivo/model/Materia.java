package curso.java.reactivo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@Table("materia")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Materia {
    private Long id;
    private String nombre;

}
