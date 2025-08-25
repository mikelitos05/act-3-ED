package com.example.actividad_3.models;

import java.util.PriorityQueue;

import com.example.actividad_3.process.Patient;

public class PatientManager {

    PriorityQueue<Patient> patientqueue;

    public PatientManager() {
        this.patientqueue = new PriorityQueue<>();
    }

    public void addPatient(Patient patient) {
        patientqueue.add(patient);
    }

    public void getPatientByPriority() {
        PriorityQueue<Patient> copy = new PriorityQueue<>(patientqueue);
        while (!copy.isEmpty()) {
            Patient patient = copy.poll();
            System.out.println("Patient Name: " + patient.getName() + ", Age: " + patient.getAge() + ", Priority: " + patient.getPriority());
        }

    }

    public void addPatientsByDefault() {
        Patient patient1 = new Patient("John Doe", 30, 2);
        addPatient(patient1);

        Patient patient2 = new Patient("Jane Smith", 25, 1);
        addPatient(patient2);

        Patient patient3 = new Patient("Alice Johnson", 40, 3);
        addPatient(patient3);

        Patient patient4 = new Patient("Bob Brown", 50, 2);
        addPatient(patient4);

        Patient patient5 = new Patient("Charlie Davis", 35, 1);
        addPatient(patient5);

        Patient patient6 = new Patient("Emily Wilson", 28, 4);
        addPatient(patient6);

        Patient patient7 = new Patient("David Martinez", 45, 2);
        addPatient(patient7);

        Patient patient8 = new Patient("Sophia Lee", 32, 3);
        addPatient(patient8);

        Patient patient9 = new Patient("Michael Clark", 60, 5);
        addPatient(patient9);

        Patient patient10 = new Patient("Olivia Harris", 22, 1);
        addPatient(patient10);
    }

}
