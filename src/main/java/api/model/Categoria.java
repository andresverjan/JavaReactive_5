package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "Categoria")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Categoria {
    @Id
    private Long id;
    private String name;
}
