package com.example.actividad_3.process;

public class Fibonacci {

    public void FibonacciSerie(int n) {
        int a = 0, b = 1;
        int c = 1;
        System.out.println("0");
        for (int i = 0; i < n; i++) {
            System.out.println(c + " ");
            c = a + b;

            a = b;
            b = c;
        }
    }

}
