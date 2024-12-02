package com.cybernetic;

import java.util.Stack;

public class PatientHistory {

    private Stack<String> medicalHistory;

    public PatientHistory() {
        medicalHistory = new Stack<>();
    }

    // i added a new medical event to the patient's history. @param event The medical event to be added.
    public void addMedicalEvent(String event) {

        medicalHistory.push(event);
    }

    // view the most recent medical event without removing it from the stack. @return The most recent medical event.
    public String viewLatestEvent() {

        if (medicalHistory.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return medicalHistory.peek();
    }

    // Remove and return the most recent medical event from the stack. @return The most recent medical event.
    public String removeMostRecentEvent() {

        if (medicalHistory.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return medicalHistory.pop();
    }

    // check if the patient's medical history is empty. @return True if the medical history is empty, false otherwise.
    public boolean isEmpty() {
        return medicalHistory.isEmpty();
    }
}
