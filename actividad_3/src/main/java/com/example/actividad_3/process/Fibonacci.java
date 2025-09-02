package com.example.actividad_3.process;

public class Fibonacci {

    public void FibonacciSerie(int n){
        int a = 0, b = 1;
        System.out.println("Serie de Fibonacci: " + a + " " + b + " ");
        for (int i = 2; i < n; i++) {
            int c = a + b;
            System.out.print(c + " ");
            a = b;
            b = c;
        }
    }

}
