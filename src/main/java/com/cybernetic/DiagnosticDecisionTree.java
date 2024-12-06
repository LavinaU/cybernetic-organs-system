package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiagnosticDecisionTree {
    private DiagnosticNode root;
    private List<String> diagnosticPath;

    public DiagnosticDecisionTree() {
        this.diagnosticPath = new ArrayList<>();
    }

    // Add this getter method that was missing
    public List<String> getDiagnosticPath() {
        return new ArrayList<>(diagnosticPath); // Return a copy for encapsulation
    }

    public void addDiagnosticCriteria(String measurementType, double threshold, String diagnosis) {
        root = addDiagnosticCriteriaRecursive(root, measurementType, threshold, diagnosis);
    }

    private DiagnosticNode addDiagnosticCriteriaRecursive(DiagnosticNode node,
                                                          String measurementType,
                                                          double threshold,
                                                          String diagnosis) {
        if (node == null) {
            DiagnosticNode newNode = new DiagnosticNode(measurementType, threshold);
            newNode.diagnosis = diagnosis;
            return newNode;
        }

        if (threshold < node.thresholdValue) {
            node.left = addDiagnosticCriteriaRecursive(node.left, measurementType, threshold, diagnosis);
        } else if (threshold > node.thresholdValue) {
            node.right = addDiagnosticCriteriaRecursive(node.right, measurementType, threshold, diagnosis);
        }

        return node;
    }


    // Method to diagnose patient
    public String diagnosePatient(Map<String, Double> measurements) {
        diagnosticPath.clear(); // Clear previous diagnostic path
        return diagnosePatientRecursive(root, measurements, 1);
    }

    private String diagnosePatientRecursive(DiagnosticNode node,
                                            Map<String, Double> measurements,
                                            int level) {
        if (node == null) {
            return "Inconclusive";
        }

        Double measurement = measurements.get(node.measurementType);
        if (measurement == null) {
            diagnosticPath.add("Measurement for " + node.measurementType + " not available.");
            return "Inconclusive";
        }

        diagnosticPath.add("Level " + level + ": " + node.measurementType + " = " + measurement +
                (measurement < node.thresholdValue ? " < " : " ≥ ") + node.thresholdValue);

        if (measurement < node.thresholdValue) {
            return node.left == null ? node.diagnosis : diagnosePatientRecursive(node.left, measurements, level + 1);
        } else {
            return node.right == null ? node.diagnosis : diagnosePatientRecursive(node.right, measurements, level + 1);
        }
    }

    // Utility method to print tree structure
    public void printTree() {
        System.out.println("\nDiagnostic Tree Structure:");
        printTreeRec(root, "", true);
    }

    private void printTreeRec(DiagnosticNode node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.measurementType + " (" + node.thresholdValue + ")");
            printTreeRec(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printTreeRec(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
}
}