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
@Table(schema = "ecommerce_schema", name = "\"ordenes_compra\"")
public class PurchaseOrders {
    @Id
    private Integer id;
    @Column("proveedor_id")
    private Integer supplierId;
    @Column("fecha_orden")
    private String orderDate;
    @Column("total")
    private double total;
}
