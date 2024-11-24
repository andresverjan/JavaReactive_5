package cedesistemas.edu.co.actividadfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(schema = "ecommerce_schema", name = "\"carrito\"")
public class Cart {
    @Id
    private Integer id;
    @Column("usuario_id")
    private Integer userId;
    @Column("fecha_creacion")
    private String creationDate;
}
