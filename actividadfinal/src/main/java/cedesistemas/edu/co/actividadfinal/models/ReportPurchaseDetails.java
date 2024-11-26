package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportPurchaseDetails {
    private Integer orden_compra_id;
    private LocalDate fecha_compra;
    private Long proveedor_id;
    private String proveedor_nombre;
    private Long producto_id;
    private String producto_nombre;
    private Integer cantidad;
    private BigDecimal precio_unitario;
    private BigDecimal total;
}
