package api.model.Request;

import api.model.DetalleOrdenCompra;

import java.util.List;

public class OrdenCompraRequest {
    private Long proveedorId;
    private List<DetalleOrdenCompra> detalles;

    // Getters y Setters
    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }
}
