package org.example.activitySix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("materia")
public class Materia {
    @Id
    private Long id;
    private String nombre;
    private Double estudianteId; // Asociación con el estudiante
    private Double nota1;
    private Double nota2;
    private Double nota3;


    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", nombre='" + nombre + "', estudianteId=" + estudianteId + "}";
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

    public Double getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Double estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }
}