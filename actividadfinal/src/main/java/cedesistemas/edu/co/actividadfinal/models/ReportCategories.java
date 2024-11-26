package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportCategories {
    private Integer orden_compra_id;
    private LocalDate fecha_compra;
    private Integer proveedor_id;
    private String proveedor_nombre;
    private Integer producto_id;
    private String producto_nombre;
    private String producto_categoria;
    private Integer cantidad;
    private Double precio_unitario;
    private Double total;
}
