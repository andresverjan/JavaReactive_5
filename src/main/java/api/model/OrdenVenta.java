package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "OrdenVenta")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdenVenta {
    @Id
    private Long id;
    private Long clienteId;
    private LocalDateTime date;
    private Double totalPrice;
    private String status;
}
