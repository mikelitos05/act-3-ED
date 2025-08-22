package com.example.actividad_3.ui;

import java.util.Scanner;

import com.example.actividad_3.models.PatientManager;
import com.example.actividad_3.process.Patient;

public class Ui {

    public static void showMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Patient");
        System.out.println("2. Get Patient by Priority");
        System.out.println("3. Exit");
    }

    public static int validatePriority() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduzca la prioridad de el paciente (1-5): ");

        int priority = scanner.nextInt();
        scanner.nextLine();

        while (priority < 1 || priority > 5) {
            System.out.println("Prioridad invalida. Introduzca un numero entre 1 y 5: ");
            priority = scanner.nextInt();
            scanner.nextLine();
        }

        return priority;
    }

    public static void runApp() {
        Scanner scanner = new Scanner(System.in);
        PatientManager patientManager = new PatientManager();

        showMenu();

        int option = scanner.nextInt();
        scanner.nextLine();
        while (option != 3) {
            switch (option) {
                case 1:
                    System.out.println("Introduzca el nombre de el paciente: ");
                    String name = scanner.nextLine();
                    System.out.println("Introduzca la edad de el paciente: ");
                    int age = scanner.nextInt();

                    int priority = validatePriority();

                    Patient patient = new Patient(name, age, priority);
                    patientManager.addPatient(patient);

                    break;
                case 2:
                    patientManager.getPatientByPriority();
                    break;
                case 97:
                    patientManager.addPatientsByDefault();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            showMenu();
            option = scanner.nextInt();
            scanner.nextLine();
        }
    }
}
