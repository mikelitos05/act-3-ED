package com.example.actividad_3.ui;

import java.util.Scanner;

import com.example.actividad_3.process.Fibonacci;

public class Ui {

    public static void showMenu() {
        System.out.println("========================");
        System.out.println("1. Serie de fibonacci");
        System.out.println("2. Suma de subconjuntos");
        System.out.println("3. Sudoku");
        System.out.println("4. Salir");
        System.out.println("========================");
    }

    public static void runApp() {

        Scanner scanner = new Scanner(System.in);
        showMenu();

        Fibonacci fibonacci = new Fibonacci();

        int opc = scanner.nextInt();
        scanner.nextLine();

        while (opc != 4) {

            switch (opc) {
                case 1:
                    System.out.println("Introduce el numero de terminos de la serie de Fibonacci: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    fibonacci.FibonacciSerie(numero);
                    break;
                case 2:
                    System.out.println("Introduce el conjunto de numeros separados por espacios: ");
                    
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            showMenu();

            opc = scanner.nextInt();
            scanner.nextLine();

        }
    }

}
