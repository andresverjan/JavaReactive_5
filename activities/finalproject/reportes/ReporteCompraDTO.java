package org.example.finalproject.reportes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReporteCompraDTO {
    private Integer ordenId;
    private LocalDateTime fechaCompra;
    private Integer proveedorId;
    private String proveedorName;
    private Integer productoId;
    private String productoName;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal totalProducto;

    public Integer getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Integer ordenId) {
        this.ordenId = ordenId;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorName() {
        return proveedorName;
    }

    public void setProveedorName(String proveedorName) {
        this.proveedorName = proveedorName;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getProductoName() {
        return productoName;
    }

    public void setProductoName(String productoName) {
        this.productoName = productoName;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(BigDecimal totalProducto) {
        this.totalProducto = totalProducto;
    }

    // Getters y Setters
}