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
@Table(schema = "ecommerce_schema", name = "\"ordenes_compra_detalles\"")
public class PurchaseOrdersDetails {
    @Id
    private Integer id;
    @Column("orden_id")
    private Integer orderId;
    @Column("producto_id")
    private Integer productId;
    @Column("cantidad")
    private int quantity;
    @Column("precio_unitario")
    private double unitPrice;
}
