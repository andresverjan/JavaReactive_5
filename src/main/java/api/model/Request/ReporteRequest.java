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
public class ReporteRequest {
    private LocalDateTime inicio;
    private LocalDateTime fin;

    // Getters y setters
}
