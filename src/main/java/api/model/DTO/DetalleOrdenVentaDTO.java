package api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetalleOrdenVentaDTO {

    private Long clienteId;
    private String clienteName;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
}
