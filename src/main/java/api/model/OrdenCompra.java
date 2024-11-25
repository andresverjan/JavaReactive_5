package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "OrdenCompra")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdenCompra {
    @Id
    private Long id;
    private Long providerId;
    private LocalDateTime date;
    private Double totalPrice;
    private String status;
}
