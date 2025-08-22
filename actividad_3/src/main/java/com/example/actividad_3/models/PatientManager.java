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
        for(Patient patient : patientqueue) {
            System.out.println("Patient Name: " + patient.getName() + ", Age: " + patient.getAge() + ", Priority: " + patient.getPriority());
        }
    }

    public void addPatientsByDefault() {
        Patient patient1 = new Patient("john doe", 30, 2);
        addPatient(patient1);

        Patient patient2 = new Patient("jane smith", 25, 1);
        addPatient(patient2);

        Patient patient3 = new Patient("alice johnson", 40, 3);
        addPatient(patient3);

        Patient patient4 = new Patient("bob brown", 50, 2);
        addPatient(patient4);

        Patient patient5 = new Patient("charlie davis", 35, 1);
        addPatient(patient5);

        Patient patient6 = new Patient("emily wilson", 28, 4);
        addPatient(patient6);

        Patient patient7 = new Patient("david martinez", 45, 2);
        addPatient(patient7);

        Patient patient8 = new Patient("sophia lee", 32, 3);
        addPatient(patient8);

        Patient patient9 = new Patient("michael clark", 60, 5);
        addPatient(patient9);

        Patient patient10 = new Patient("olivia harris", 22, 1);
        addPatient(patient10);
    }

}
