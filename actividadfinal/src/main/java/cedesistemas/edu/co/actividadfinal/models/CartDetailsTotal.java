package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDetailsTotal {
    private Integer carrito_id;
    private Integer producto_id;
    private String nombre_producto;
    private String descripcion;
    private String imagenUrl;
    private int cantidad;
    private double precio;
    private double total;
    private double total_con_impuestos;
    private double total_general;
}
