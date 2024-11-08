package curso.java.reactivo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("registro")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Registro {
    @Id
    private Long id;
    @Column("estudiante_id")
    private Long estudianteId;
    @Column("materia_id")
    private Long materiaId;
    private Double nota;
}
