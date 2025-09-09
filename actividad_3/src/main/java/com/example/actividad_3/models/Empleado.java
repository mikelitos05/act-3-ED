package com.example.actividad_3.models;
import java.util.Objects;

public class Empleado {
    public final int id;
    public final String nombre;
    public final String departamento;

    public Empleado(int id, String nombre, String departamento) {
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre);
        this.departamento = Objects.requireNonNull(departamento);
    }

    @Override
    public String toString() {
        return String.format("Empleado{id=%d, nombre='%s', dept='%s'}", id, nombre, departamento);
    }
}
