package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "DetalleOrdenCompra")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleOrdenCompra {
    @Id
    private Long id;
    private Long ordenCompraId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
}
