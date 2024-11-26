package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportTopFive {
    private Integer producto_id;
    private String producto_nombre;
    private Integer total_cantidad_vendida;
    private Double total_ingresos;
}
