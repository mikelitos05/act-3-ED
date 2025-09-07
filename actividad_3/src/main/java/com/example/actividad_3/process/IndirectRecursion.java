package com.example.actividad_3.process;

public class IndirectRecursion {

    // Recursividad indirecta: isEven llama a isOdd y viceversa
    public boolean isEven(int n) {
        if (n < 0) return isEven(-n); // normalizamos signo
        if (n == 0) return true;      // base
        return isOdd(n - 1);          // llamada indirecta
    }

    public boolean isOdd(int n) {
        if (n < 0) return isOdd(-n);
        if (n == 0) return false;
        return isEven(n - 1);         // llamada indirecta
    }
}

    

