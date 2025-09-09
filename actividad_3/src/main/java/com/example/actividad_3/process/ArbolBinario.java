package com.example.actividad_3.process;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.actividad_3.models.Nodo;

public class ArbolBinario<K extends Comparable<K>, V> {

    private Nodo<K,V> raiz;
    private int comparacionesUltimaBusqueda = 0;

    public int getComparacionesUltimaBusqueda() {
        return comparacionesUltimaBusqueda;
    }

    /* ===== Inserción ===== */
    public void insertar(K clave, V valor) {
        raiz = insertarRec(raiz, clave, valor);
    }

    private Nodo<K,V> insertarRec(Nodo<K,V> nodo, K clave, V valor) {
        if (nodo == null) return new Nodo<>(clave, valor);
        int cmp = clave.compareTo(nodo.clave);
        if (cmp < 0) {
            nodo.izq = insertarRec(nodo.izq, clave, valor);
        } else if (cmp > 0) {
            nodo.der = insertarRec(nodo.der, clave, valor);
        } else {
            // clave existente -> actualizar valor
            nodo.valor = valor;
        }
        return nodo;
    }

    /* ===== Búsqueda ===== */
    public Optional<V> buscar(K clave) {
        comparacionesUltimaBusqueda = 0;
        Nodo<K,V> actual = raiz;
        while (actual != null) {
            comparacionesUltimaBusqueda++;
            int cmp = clave.compareTo(actual.clave);
            if (cmp == 0) return Optional.ofNullable(actual.valor);
            actual = (cmp < 0) ? actual.izq : actual.der;
        }
        return Optional.empty();
    }

    /* ===== Eliminación ===== */
    public void eliminar(K clave) {
        raiz = eliminarRec(raiz, clave);
    }

    private Nodo<K,V> eliminarRec(Nodo<K,V> nodo, K clave) {
        if (nodo == null) return null;
        int cmp = clave.compareTo(nodo.clave);
        if (cmp < 0) {
            nodo.izq = eliminarRec(nodo.izq, clave);
        } else if (cmp > 0) {
            nodo.der = eliminarRec(nodo.der, clave);
        } else {
            // encontrado
            if (nodo.izq == null && nodo.der == null) return null;   // hoja
            if (nodo.izq == null) return nodo.der;                    // un hijo (der)
            if (nodo.der == null) return nodo.izq;                    // un hijo (izq)
            // dos hijos: sucesor (mín del subárbol derecho)
            Nodo<K,V> sucesor = min(nodo.der);
            nodo.clave = sucesor.clave;
            nodo.valor = sucesor.valor;
            nodo.der = eliminarRec(nodo.der, sucesor.clave);
        }
        return nodo;
    }

    private Nodo<K,V> min(Nodo<K,V> nodo) {
        while (nodo.izq != null) nodo = nodo.izq;
        return nodo;
    }

    /* ===== Recorridos ===== */

    public List<K> inOrdenClaves() {
        List<K> res = new ArrayList<>();
        inOrdenClavesRec(raiz, res);
        return res;
    }

    private void inOrdenClavesRec(Nodo<K,V> nodo, List<K> res) {
        if (nodo == null) return;
        inOrdenClavesRec(nodo.izq, res);
        res.add(nodo.clave);
        inOrdenClavesRec(nodo.der, res);
    }

    public List<K> preOrdenClaves() {
        List<K> res = new ArrayList<>();
        preOrdenClavesRec(raiz, res);
        return res;
    }

    private void preOrdenClavesRec(Nodo<K,V> nodo, List<K> res) {
        if (nodo == null) return;
        res.add(nodo.clave);
        preOrdenClavesRec(nodo.izq, res);
        preOrdenClavesRec(nodo.der, res);
    }

    public List<K> postOrdenClaves() {
        List<K> res = new ArrayList<>();
        postOrdenClavesRec(raiz, res);
        return res;
    }

    private void postOrdenClavesRec(Nodo<K,V> nodo, List<K> res) {
        if (nodo == null) return;
        postOrdenClavesRec(nodo.izq, res);
        postOrdenClavesRec(nodo.der, res);
        res.add(nodo.clave);
    }
}
