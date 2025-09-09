package com.example.actividad_3.ui;
import java.util.ArrayList;
import java.util.List;

import com.example.actividad_3.models.Empleado;
import com.example.actividad_3.process.ArbolBinario;

/**
 * Programa principal (Main) para demostrar el uso del BST.
 */
public class Menu {

    public static void main(String[] args) {
        System.out.println("=== DEMO 1: Árbol Binario con enteros ===");
        demoEnteros();

        System.out.println("\n=== DEMO 2: Gestión de Empleados (BST vs. búsqueda secuencial) ===");
        demoEmpleados();
    }

    /* ===== DEMO ENTEROS ===== */
    private static void demoEnteros() {
        ArbolBinario<Integer,Integer> arbol = new ArbolBinario<>();
        int[] datos = {50,30,70,20,40,60,80};
        for (int x: datos) arbol.insertar(x, x);

        System.out.println("In-orden:   " + arbol.inOrdenClaves());
        System.out.println("Pre-orden:  " + arbol.preOrdenClaves());
        System.out.println("Post-orden: " + arbol.postOrdenClaves());

        buscar(arbol, 40);
        buscar(arbol, 25);

        arbol.eliminar(20);
        System.out.println("Tras eliminar 20 -> " + arbol.inOrdenClaves());

        arbol.eliminar(30);
        System.out.println("Tras eliminar 30 -> " + arbol.inOrdenClaves());

        arbol.eliminar(50);
        System.out.println("Tras eliminar 50 -> " + arbol.inOrdenClaves());
    }

    private static void buscar(ArbolBinario<Integer,Integer> arbol, int clave) {
        var res = arbol.buscar(clave);
        System.out.printf("Buscar %d -> %s (comparaciones=%d)%n",
                clave,
                res.isPresent() ? "ENCONTRADO" : "NO ENCONTRADO",
                arbol.getComparacionesUltimaBusqueda());
    }

    /* ===== DEMO EMPLEADOS ===== */
    private static void demoEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        ArbolBinario<Integer,Empleado> indice = new ArbolBinario<>();

        Empleado[] emps = {
            new Empleado(105,"Ana Pérez","Cirugía"),
            new Empleado(203,"Luis Gómez","Urgencias"),
            new Empleado(150,"María Ruiz","Medicina Interna"),
            new Empleado(310,"Carlos Díaz","Pediatría"),
            new Empleado(120,"Sofía López","Anestesia"),
            new Empleado(250,"Jorge Torres","Trauma"),
            new Empleado(180,"Elena Rivas","Ginecología"),
            new Empleado(90,"Raúl Soto","Imagenología"),
            new Empleado(400,"Lucía Cano","Oftalmología"),
            new Empleado(75,"Diana Cruz","Urología")
        };

        // insertar en lista y árbol
        for (Empleado e: emps) { lista.add(e); indice.insertar(e.id, e); }

        System.out.println("IDs (in-orden): " + indice.inOrdenClaves());

        int[] idsPrueba = {180,400,75,999};
        for (int id: idsPrueba) {
            var secuencial = buscarSecuencial(lista,id);
            var bst = indice.buscar(id);
            System.out.printf("ID %d -> Secuencial: %s (pasos=%d) | BST: %s (comparaciones=%d)%n",
                id,
                secuencial.encontrado ? secuencial.valor : "NO ENCONTRADO",
                secuencial.pasos,
                bst.isPresent() ? bst.get() : "NO ENCONTRADO",
                indice.getComparacionesUltimaBusqueda());
        }

        int n = lista.size();
        System.out.printf("Peor caso ~ Secuencial=%d pasos | BST≈%d pasos%n",
                n, (int)Math.ceil(Math.log(n+1)/Math.log(2)));
    }

    /* ==== Búsqueda Secuencial ==== */
    private static class Res<T> {
        boolean encontrado; T valor; int pasos;
        Res(boolean e,T v,int p){ encontrado=e; valor=v; pasos=p; }
    }
    private static Res<Empleado> buscarSecuencial(List<Empleado> lista, int id) {
        int pasos=0;
        for (Empleado e: lista){ pasos++; if(e.id==id) return new Res<>(true,e,pasos);}
        return new Res<>(false,null,pasos);
    }
}

