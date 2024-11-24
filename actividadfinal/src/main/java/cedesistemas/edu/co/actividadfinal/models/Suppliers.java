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
@Table(schema = "ecommerce_schema", name = "\"proveedores\"")
public class Suppliers {
    @Id
    private Integer id;
    @Column("nombre")
    private String name;
    @Column("direccion")
    private String address;
    @Column("telefono")
    private String phone;
    @Column("email")
    private String email;
}
