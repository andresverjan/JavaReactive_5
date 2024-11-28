package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

@ToString
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class ReporteCompra {

    private Long idcompra;
    private String fecha;
    private int cantidad;
    private String nombreProducto;
    private String descripcionProducto;
    private int precioProducto;
    private String categoriaProducto;
    private String nombreProveedor;
    private String correoProveedor;
    private String direccionProveedor;
    private String telefonoProveedor;


    public ReporteCompra(Long idcompra,
                         String fecha,
                         int cantidad,
                         String nombreProducto,
                         String descripcionProducto,
                         int precioProducto,
                         String categoriaProducto,
                         String nombreProveedor,
                         String correoProveedor,
                         String direccionProveedor,
                         String telefonoProveedor
                         ) {
        this.idcompra = idcompra;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.categoriaProducto = categoriaProducto;
        this.nombreProveedor = nombreProveedor;
        this.correoProveedor = correoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.telefonoProveedor = telefonoProveedor;


    }
}
