package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "carrito")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class Carrito {

    @Id
    private Long idcarrito;
    @Column("fecha_creacion")
    private String fecha_creacion;


    public Long getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Long idcarrito) {
        this.idcarrito = idcarrito;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
