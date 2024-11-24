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
@Table(schema = "ecommerce_schema", name = "\"productos\"")
public class Products {
    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("descripcion")
    private String descripcion;
    @Column("imageUrl")
    private String imageUrl;
    @Column("stock")
    private int stock;
    @Column("precio")
    private double precio;
}
