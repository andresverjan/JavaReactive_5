package org.example.activitySix.model;

import java.util.List;

public class EstudianteConMateriasYNotas {
    private Long id;
    private String nombre;
    private int edad;
    private List<Materia> materias;
    @Override
    public String toString() {
        return "EstudianteConMateriasYNotas{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", materias=" + materias +
                '}';
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

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}
