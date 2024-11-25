package api.model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReporteClienteRequest {
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Long clienteId;

    // Getters y setters
}
