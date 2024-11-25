package org.example.finalproject.ordenes;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("detalles_ordenes")
public class DetalleOrden {
    @Id
    private Integer id;
    private Integer ordenId;
    private Integer productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private LocalDateTime fechaDetalle;
    private LocalDateTime fechaActualizacion;

    public DetalleOrden(Integer ordenId, Integer productoId, Integer cantidad, BigDecimal precioUnitario,
                        LocalDateTime fechaDetalle, LocalDateTime fechaActualizacion) {
        this.ordenId = ordenId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fechaDetalle = fechaDetalle;
        this.fechaActualizacion = fechaActualizacion;
    }

    public DetalleOrden() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Integer ordenId) {
        this.ordenId = ordenId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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

    public LocalDateTime getFechaDetalle() {
        return fechaDetalle;
    }

    public void setFechaDetalle(LocalDateTime fechaDetalle) {
        this.fechaDetalle = fechaDetalle;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // Getters and Setters
}