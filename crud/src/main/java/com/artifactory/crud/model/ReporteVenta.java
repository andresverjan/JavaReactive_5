package com.artifactory.crud.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@ToString
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class ReporteVenta {

    private Venta venta;
    private Cliente cliente;
    private Carrito carrito;
    private List<Producto> listProducto;

    public ReporteVenta(Venta venta, Cliente cliente, Carrito carrito, List<Producto> listProducto) {
        this.venta = venta;
        this.cliente = cliente;
        this.carrito = carrito;
        this.listProducto = listProducto;
    }


}
