package com.artifactory.crud.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class ReporteCompras {

    private Compra compra;
    private Proveedor proveedor;
    private Producto producto;

    public ReporteCompras(Compra compra, Proveedor proveedor, Producto producto){
        this.compra = compra;
        this.proveedor = proveedor;
        this.producto = producto;
    }
}
