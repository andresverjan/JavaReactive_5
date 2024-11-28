package com.artifactory.crud.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class ReporteVentas {

    private Long idventa;
    private int totalventa;
    private LocalDate ventafecha;
    private String nombrecliente;
    private String correocliente;
    private String direccioncliente;
    private String telefonocliente;
    private String nombreproducto;
    private String  descripcioproducto;
    private int precioproducto;
    private String categoria;
    private int cantidad;

    public ReporteVentas(Long idventa,
                         Integer totalventa,
                         LocalDate ventafecha,
                         String nombrecliente,
                         String correocliente,
                         String direccioncliente,
                         String telefonocliente,
                         String nombreproducto,
                         String descripcioproducto,
                         Integer precioproducto,
                         String categoria,
                         Integer cantidad) {
        this.idventa = idventa;
        this.totalventa = totalventa;
        this.ventafecha = ventafecha;
        this.nombrecliente = nombrecliente;
        this.correocliente = correocliente;
        this.direccioncliente = direccioncliente;
        this.telefonocliente = telefonocliente;
        this.nombreproducto = nombreproducto;
        this.descripcioproducto = descripcioproducto;
        this.precioproducto = precioproducto;
        this.categoria = categoria;
        this.cantidad = cantidad;

    }
}
