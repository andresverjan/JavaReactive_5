package api.model.Request;

import api.model.DetalleOrdenCompra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModificarCarritoRequest {
    private Long carritoId;
    private Long productId;
    private Integer cantidad;


}
