package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "Carrito")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Carrito {
    @Id
    private Long id;
    private Long clienteId;
    private Double total = 0.0;

    public Carrito(Long clienteId) {
        this.clienteId = clienteId;
    }
}
