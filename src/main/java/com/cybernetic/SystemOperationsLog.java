package com.cybernetic;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SystemOperationsLog {

    private int maxSize;
    private int top;
    private SystemOperation[] stackArray;

    /**
     *
     * @param size
     */
    public SystemOperationsLog(int size) {
        maxSize = size;
        stackArray = new SystemOperation[maxSize];
        top = -1;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return (top == -1);
    }

    /**
     *
     * @return
     */
    public boolean isFull() {
        return (top == maxSize - 1);
    }

    /**
     *
     * @return
     */
    public int size() {
        return top + 1;
    }

    /**
     *
     */
    public void display() {
        System.out.print("Stack: ");
        for (int i = 0; i <= top; i++) {
            System.out.print(stackArray[i] + " ");
        }
        System.out.println();
    }

    /**
     *
     * @return
     */
    public void undoLastOperation() {

        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        } else {
            SystemOperation result = stackArray[top];
            top--;
        }
    }

    /**
     *
     * @param systemOperation
     */
    public void pushOperation(SystemOperation systemOperation) {

        if (isFull()) {
            throw new StackOverflowError("Stack is full");
        }
        stackArray[++top] = systemOperation;
    }

    /**
     *
     * @param n
     * @return
     */
    public List<SystemOperation> getRecentOperations(int n) {

        List<SystemOperation> result = new LinkedList<SystemOperation>();

        for (int i = 0; i < size() && n!=0; i++) {
            result.add(stackArray[top - i]);
            n--;
        }

        return result;
    }

    /**
     *
     * @return
     */
    public SystemOperation popLastOperation() {

        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        } else {
            SystemOperation result = stackArray[top];
            top--;
            return result;
        }
    }

    /**
     *
     * @return
     */
    public SystemOperation peekLastOperation() {

        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        } else {
            return stackArray[top];
        }
    }
}

class SystemOperation {

    String operationId;
    String operationType; // "MATCH", "TRANSPLANT", "EMERGENCY"
    LocalDateTime timestamp;
    String description;
    boolean isReversible;

    /**
     *
     * @param operationId
     * @param operationType
     * @param description
     * @param isReversible
     */
    public SystemOperation(String operationId, String operationType, String description, boolean isReversible) {
        this.operationId = operationId;
        this.operationType = operationType;
        this.description = description;
        this.isReversible = isReversible;
    }

    public String getOperationId() {
        return this.operationId;
    }

    /**
     *
     * @return
     */
    public String toString() {
        return (operationId + ": " + operationType); // TR-003: PAT-003 (Pending)
    }
}
