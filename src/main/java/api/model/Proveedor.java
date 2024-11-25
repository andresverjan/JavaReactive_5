package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "Proveedor")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Proveedor {
    @Id
    private Long id;
    private String name;
    private String email;
    private String phone;
}
