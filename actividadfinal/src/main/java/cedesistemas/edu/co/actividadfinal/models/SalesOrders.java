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
@Table(schema = "ecommerce_schema", name = "\"ordenes_venta\"")
public class SalesOrders {
    @Id
    private Integer id;
    @Column("usuario_id")
    private Integer userId;
    @Column("fecha_orden")
    private String orderDate;
    @Column("total")
    private double total;
}
