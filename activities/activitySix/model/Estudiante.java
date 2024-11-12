package org.example.activitySix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("estudiante")
public class Estudiante {
    @Id
    private Long id;
    private String nombre;
    private int edad;



    @Override
    public String toString() {
        return "Estudiante{" + "id=" + id + ", nombre='" + nombre + "', email='" + edad + "'}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
