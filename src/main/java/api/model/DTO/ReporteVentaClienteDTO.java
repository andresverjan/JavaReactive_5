package api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReporteVentaClienteDTO {
    private Long ordenVentaId;
    private Long clienteId;
    private String clienteNombre;
    private LocalDateTime fecha;
    private Long productId;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
}
