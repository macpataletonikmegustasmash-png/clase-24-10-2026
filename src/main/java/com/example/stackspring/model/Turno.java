package com.example.stackspring.model;

public class Turno {

    private int numero;
    private String nombre;

    public Turno(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
}
