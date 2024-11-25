package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class CarritoDetalleRequest {

    private Long idcarrito;

    private String fecha;

    private List<DetalleCarrito> detalleCarritos = new ArrayList<>();


    public Long getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Long idcarrito) {
        this.idcarrito = idcarrito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<DetalleCarrito> getDetalleCarritos() {
        return detalleCarritos;
    }

    public void setDetalleCarritos(List<DetalleCarrito> detalleCarritos) {
        this.detalleCarritos = detalleCarritos;
    }
}
