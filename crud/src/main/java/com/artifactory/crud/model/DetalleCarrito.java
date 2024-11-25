package com.artifactory.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "detallecarrito")
@ToString
@AllArgsConstructor
@NoArgsConstructor // Genera un constructor por defecto
@Data
public class DetalleCarrito {

    @Id
    private Long iddetallecarrito;
    @Column("idcarrito")
    private Long idcarrito;
    @Column("idproducto")
    private Long idproducto;
    @Column("cantidad")
    private int cantidad;

    public Long getIddetallecarrito() {
        return iddetallecarrito;
    }

    public void setIddetallecarrito(Long iddetallecarrito) {
        this.iddetallecarrito = iddetallecarrito;
    }

    public Long getIdcarrito() {
        return idcarrito;
    }

    public void setIdcarrito(Long idcarrito) {
        this.idcarrito = idcarrito;
    }

    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
