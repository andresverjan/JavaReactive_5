package com.artifactory.crud.model;

import java.util.ArrayList;
import java.util.List;

public class TotalVentaResponse {

    private int totalproductos;

    private int impuestos;

    private int totalpagar;

    private Long idcarrito;

    private String fecha;



    public int getTotalproductos() {
        return totalproductos;
    }

    public void setTotalproductos(int totalproductos) {
        this.totalproductos = totalproductos;
    }

    public int getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(int impuestos) {
        this.impuestos = impuestos;
    }

    public int getTotalpagar() {
        return totalpagar;
    }

    public void setTotalpagar(int totalpagar) {
        this.totalpagar = totalpagar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Long idcarrito) {
        this.idcarrito = idcarrito;
    }
}
