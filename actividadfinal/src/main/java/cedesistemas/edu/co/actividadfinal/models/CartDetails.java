package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(schema = "ecommerce_schema", name = "\"carrito_detalles\"")
public class CartDetails {
    @Id
    private Integer id;
    @Column("carrito_id")
    private Integer cartId;
    @Column("producto_id")
    private Integer productId;
    @Column("cantidad")
    private int quantity;
}
