package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "DetalleCarrito")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleCarrito {
    @Id
    private Long id;
    @Column("carrito_id")
    private Long CarritoId;
    @Column("product_id")
    private Long ProductId;
    private Integer quantity;
    private Double price;

}
