package api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReporteCompraCategoriaDTO {
    private String categoria;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
}
