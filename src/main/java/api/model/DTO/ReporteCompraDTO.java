package api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReporteCompraDTO {
    private Long ordenCompraId;
    private Long proveedorId;
    private String proveedorNombre;
    private LocalDateTime fecha;
    private Long productId;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;


}
