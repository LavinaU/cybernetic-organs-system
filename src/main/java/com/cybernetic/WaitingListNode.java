package com.cybernetic;

public class WaitingListNode {
    private Patient patient; // the patient in the waiting list
    private int priority; // the priority of the patient (higher number means higher priority)
    private WaitingListNode next; // Reference to the next node in the list

    public WaitingListNode(Patient patient, int priority) {
        this.patient = patient;
        this.priority = priority;
        this.next = null;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getPriority() {
        return priority;
    }

    public WaitingListNode getNext() {
        return next;
    }

    public void setNext(WaitingListNode next) {
        this.next = next;
    }
}
