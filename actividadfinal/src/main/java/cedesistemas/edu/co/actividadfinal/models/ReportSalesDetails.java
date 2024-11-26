package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportSalesDetails {
    private Integer orden_venta_id;
    private LocalDate fecha_venta;
    private Integer cliente_id;
    private Integer producto_id;
    private String producto_nombre;
    private Integer cantidad;
    private Double precio_unitario;
    private Double total;
}
