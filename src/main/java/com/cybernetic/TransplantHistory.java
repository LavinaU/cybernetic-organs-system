package com.cybernetic;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class TransplantHistory {

    private TransplantRecord head;

    /**
     *
     */
    public TransplantHistory() {
        printTransplantRecord();
    }

    /**
     * Forward traversal
     */
    public void printTransplantRecord() {
        TransplantRecord current = head;
        while (current != null) {
            // System.out.print(current.operationId + " ");
            System.out.printf("Successfully validated: %s - %s (OrganId: %d, timestamp Type: %s, surgeon: %s, outcome: %s)\n",
                    current.operationId, current.patientId, current.organId, current.timestamp, current.surgeon, current.outcome);
            current = current.next;
        }
        System.out.println();
    }

    /**
     * Insert at the beginning
     * @param newNode
     */
    public void addTransplantRecordAtBeginning(TransplantRecord newNode) {
        newNode.next = head;
        head = newNode;
    }

    /**
     * Insert at the end
     * @param operationId
     * @param patientId
     * @param organId
     * @param timestamp
     * @param surgeon
     * @param outcome
     */
    public void insertAtEnd(String operationId, String patientId, String organId, LocalDateTime timestamp, String surgeon, String outcome) {
        TransplantRecord newNode = new TransplantRecord(operationId, patientId, organId, timestamp, surgeon, outcome);
        if (head == null) {
            head = newNode;
            return;
        }
        TransplantRecord current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    /**
     * Delete a TransplantRecord with given operationId
     * @param operationId
     */
    public void deleteTransplantRecord(String operationId) {
        if (head == null) {
            return;
        }
        if (head.operationId == operationId) {
            head = head.next;
            return;
        }
        TransplantRecord current = head;
        TransplantRecord previous = null;
        while (current != null && current.operationId != operationId) {
            previous = current;
            current = current.next;
        }
        if (current == null) {
            return;
        }
        previous.next = current.next;
    }

    /**
     * Find a TransplantRecord with a given PatientId
     * @param patientId
     * @return
     */
    public TransplantRecord findTransplantByPatient(String patientId) {
        TransplantRecord current = head;
        while (current != null) {
            if (current.patientId == patientId) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     *
     * @param i
     * @return
     */
    public List<TransplantRecord> getRecentTransplants(int i) {

        List<TransplantRecord> result = new LinkedList<TransplantRecord>();

        TransplantRecord current = head;
        while (current != null && i !=0) {
            result.add(current);
            current = current.next;
            i--;
        }

        return result;
    }
}

// TransplantRecord class
class TransplantRecord {

    String operationId;
    String patientId;
    String organId;
    LocalDateTime timestamp;
    String surgeon;
    String outcome;
    TransplantRecord next;

    TransplantRecord(String operationId, String patientId, String organId, LocalDateTime timestamp, String surgeon, String outcome) {
        this.operationId = operationId;
        this.patientId = patientId;
        this.organId = organId;
        this.timestamp = timestamp;
        this.surgeon = surgeon;
        this.outcome = outcome;
        this.next = null;
    }
    TransplantRecord(String operationId, String patientId, String organId, String surgeon, String outcome) {
        this.operationId = operationId;
        this.patientId = patientId;
        this.organId = organId;
        this.surgeon = surgeon;
        this.outcome = outcome;
        this.next = null;
    }

    public String toString() {
        return (operationId + ": " + patientId + " (" + outcome + ")"); // TR-003: PAT-003 (Pending)
    }

}
