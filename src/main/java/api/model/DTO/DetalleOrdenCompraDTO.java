package api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetalleOrdenCompraDTO {
    private Long proveedorId;
    private String proveedorName;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Integer stock;
}
