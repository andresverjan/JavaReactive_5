package org.example.activitySix.model;

import java.util.List;

public class MateriasConNotas {
    private Long id;
    private String nombre;
    private List<Double> notas;

    public MateriasConNotas(Long id, String nombre, List<Double> notas) {
        this.id = id;
        this.nombre = nombre;
        this.notas = notas;
    }

    // Método para calcular el promedio de las notas
    public Double calcularPromedio() {
        return notas.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // Getters y Setters
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

    public List<Double> getNotas() {
        return notas;
    }

    public void setNotas(List<Double> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "MateriasConNotas{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", notas=" + notas +
                ", promedio=" + calcularPromedio() +
                '}';
    }
}
