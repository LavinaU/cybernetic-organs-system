package com.cybernetic;

import lombok.Data;

@Data

public class WaitingList {
    private WaitingListNode head;

    public WaitingList() {
        this.head = null;
    }

    public void addPatient(Patient patient, int priority) {
        WaitingListNode newNode = new WaitingListNode(patient, priority);
        if (head == null || head.getPriority() < priority) {
            // new node becomes head, onluy if list is empty or higher priority than the head
            newNode.setNext(head);
            head = newNode;
        } else {
            // finds the appropriate position
            WaitingListNode current = head;
            while (current.getNext() != null && current.getNext().getPriority() >= priority) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
    }

    public Patient removeHighestPriority() {
        if (head == null) {
            return null;
        }
        Patient patient = head.getPatient();
        head = head.getNext();
        return patient;
    }

    public void updatePriority(String patientId, int newPriority) {
        WaitingListNode current = head;
        while (current != null) {
            if (current.getPatient().getId().equals(patientId)) {
                // for remoivng the node & re-add it with the new priority
                current.setNext(current.getNext());
                addPatient(current.getPatient(), newPriority);
                return;
            }
            current = current.getNext();
        }
    }

    public void displayWaitingList() {
        if (head == null) {
            System.out.println("The waiting list is empty.");
            return;
        }
        WaitingListNode current = head;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.getPatient().getName() + " (Priority: " + current.getPriority() + ")");
            current = current.getNext();
            position++;
        }
    }

    public int getPosition(String patientId) {
        int position = 1;
        WaitingListNode current = head;
        while (current != null) {
            if (current.getPatient().getId().equals(patientId)) {
                return position;
            }
            current = current.getNext();
            position++;
        }
        return -1; // not found :(
    }

    public void removePatient(String patientId) {
        if (head == null) return;

        if (head.getPatient().getId().equals(patientId)) {
            head = head.getNext();
            return;
        }

        WaitingListNode current = head;
        while (current.getNext() != null && !current.getNext().getPatient().getId().equals(patientId)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
        }
    }
}
