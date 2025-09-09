package com.example.actividad_3.models;


public class Nodo<K extends Comparable<K>, V> {
    public K clave;
    public V valor;
    public Nodo<K,V> izq, der;

    public Nodo(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }
}

