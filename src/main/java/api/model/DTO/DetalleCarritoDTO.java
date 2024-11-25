package api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetalleCarritoDTO {
    private Long productId;
    private String nombreProducto;
    private Integer quantity;
    private Double totalPrice;  // El precio total para esa cantidad de productos
    private Integer stockProducto;
}
