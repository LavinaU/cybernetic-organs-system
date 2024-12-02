package com.cybernetic;


import java.util.LinkedList;
import java.util.Queue;

public class PatientWaitingList {
    private Queue<Patient> waitingList;

    public PatientWaitingList() {
        waitingList = new LinkedList<>();
    }


    // add a new patient to the end of the waiting list. @param patient The patient to be added to the waiting list.
    public void addPatient(Patient patient) {
        // throw new UnsupportedOperationException("Method not implemented");
        waitingList.offer(patient);
    }

    // remove and return the next patient from the front of the waiting list. @return The next patient in the waiting list.
    public Patient removeNextPatient() {
        // throw new UnsupportedOperationException("Method not implemented");
        if (waitingList.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return waitingList.poll();
    }

    // check if the patient waiting list is empty. @return True if the waiting list is empty, false otherwise.
    public boolean isEmpty() {
        // throw new UnsupportedOperationException("Method not implemented");
        return waitingList.isEmpty();
    }

    // print the current state of the patient waiting list.
    public void printWaitingList() {
        // throw new UnsupportedOperationException("Method not implemented");

        System.out.print("Current Waiting Queue:\n");
        int index = 1;
        for (Patient patient : waitingList) {
            if(index!= waitingList.size()) {
                System.out.print(index + ".[ " + patient.getName() + " ] <--- ");
            } else {
                System.out.println(index + ".[ " + patient.getName() + " ]");
            }
            index++;
        }
    }
}
