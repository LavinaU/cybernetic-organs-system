package com.cybernetic;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class EmergencyWaitlist {

    private EmergencyCase[] heap;
    private int size;

    /**
     *
     */
    EmergencyWaitlist() {
        heap = new EmergencyCase[10];
        size = 0;
    }

    /**
     *
     * @param emrcase
     */
    public void addEmergencyCase(EmergencyCase emrcase) {

        if (size == heap.length) {
            resizeHeap();
        }

        heap[size++] = emrcase;
        // heapifyUp(size - 1);
    }

    /**
     *
     */
    private void resizeHeap() {
        int newCapacity = heap.length * 2;
        heap = Arrays.copyOf(heap, newCapacity);
    }

    /**
     *
     * @param index
     */
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && heap[index].getSeverityLevel() < heap[parentIndex].getSeverityLevel()) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    /**
     *
     * @param index
     */
    private void heapifyDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallestIndex = index;

        if (leftChildIndex < size && heap[leftChildIndex].getSeverityLevel() < heap[smallestIndex].getSeverityLevel()) {
            smallestIndex = leftChildIndex;
        }

        if (rightChildIndex < size && heap[rightChildIndex].getSeverityLevel() < heap[smallestIndex].getSeverityLevel()) {
            smallestIndex = rightChildIndex;
        }

        if (smallestIndex != index) {
            swap(index, smallestIndex);
            heapifyDown(smallestIndex);
        }
    }

    /**
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        EmergencyCase temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @return
     */
    public EmergencyCase getNextUrgentCase() {

        if (isEmpty()) {
            // throw new IllegalStateException("Priority Queue is empty");
            return null;
        }

        EmergencyCase result = heap[0];
        removeUrgentCase();
        return result;
    }

    /**
     *
     */
    public void removeUrgentCase() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority Queue is empty");
        }

        EmergencyCase root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
    }

    /**
     *
     * @param csId
     * @param sevLevel
     */
    public void updateCaseSeverity(String csId, int sevLevel) {

        if (isEmpty()) {
            throw new IllegalStateException("Priority Queue is empty");
        }
        int indx = size - 1;

        while (indx >= 0) {
            if(heap[indx].getCaseId().equals(csId)){
                heap[indx].setSeverityLevel(sevLevel);
            }
            indx--;
        }
    }

    /**
     *
     * @param csId
     * @return
     */
    public EmergencyCase findCaseById(String csId) {

        if (isEmpty()) {
            throw new IllegalStateException("Priority Queue is empty");
        }

        int indx = size - 1;
        EmergencyCase result = null;
        while (indx >= 0) {
            if(heap[indx].getCaseId().equals(csId)){
                result = heap[indx];
                break;
            }
            indx--;
        }
        return result;
    }

    /**
     *
     * @param sevLevel
     * @return
     */
    public List<EmergencyCase> getAllCasesBySeverity(int sevLevel) {

        if (isEmpty()) {
            throw new IllegalStateException("Priority Queue is empty");
        }

        List<EmergencyCase> result = new ArrayList<EmergencyCase>();
        int indx = size - 1;

        while (indx >= 0) {
            if(heap[indx].getSeverityLevel() == sevLevel){
                result.add(heap[indx]);
            }
            indx--;
        }

        return result;
    }
}

class EmergencyCase {

    String caseId;
    Patient patient;
    int severityLevel; // 1-5
    LocalDateTime registrationTime;
    EmergencyCase left, right;

    /**
     *
     * @param caseId
     * @param patient
     * @param severityLevel
     * @param registrationTime
     */
    public EmergencyCase(String caseId, Patient patient, int severityLevel, LocalDateTime registrationTime) {
        this.caseId = caseId;
        this.patient = patient;
        this.severityLevel = severityLevel;
        this.registrationTime = registrationTime;
        left = right = null;
    }

    public String getCaseId() {
        return caseId;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     *
     * @return
     */
    public String toString() {
        Duration duration = Duration.between(registrationTime, LocalDateTime.now());
        return (caseId + " (Severity " + severityLevel + ", Wait time: " + duration.toMinutes() + " min )"); // EMERG-001 (Severity 5, Wait time: 45 min)
    }
}
