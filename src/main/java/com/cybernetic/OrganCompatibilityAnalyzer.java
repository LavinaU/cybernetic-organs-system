package com.cybernetic;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

public class OrganCompatibilityAnalyzer {
    private List<Organ> organs;
    private List<Patient> patients;

    public OrganCompatibilityAnalyzer() {
        organs = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public void addOrgan(Organ organ) {
        organs.add(organ);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    // for creating a compatibility matrix (blood type/weight/HLA compatibility)
    public int[][] createCompatibilityMatrix() {
        int[][] matrix = new int[organs.size()][patients.size() * 3]; // 3 factors: blood type, weight, HLA

        for (int i = 0; i < organs.size(); i++) {
            Organ organ = organs.get(i);
            for (int j = 0; j < patients.size(); j++) {
                Patient patient = patients.get(j);
                int index = j * 3; // 3 columns for each patient category: blood type, weight, HLA

                // for blood type compatibility
                matrix[i][index] = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());

                // for weight compatibility
                matrix[i][index + 1] = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());

                // for HLA compatibility
                matrix[i][index + 2] = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
            }
        }

        return matrix;
    }

    // for calculating blood type compatibility
    private int calculateBloodTypeCompatibility(String donorType, String recipientType) {
        if (donorType.equals(recipientType)) {
            return 100; // exact match!
        }

        if (donorType.equals("O+") || donorType.equals("O-")) {
            if (recipientType.equals("A+") || recipientType.equals("A-")
                    || recipientType.equals("B+") || recipientType.equals("B-")
                    || recipientType.equals("AB+") || recipientType.equals("AB-")
                    || recipientType.equals("O+") || recipientType.equals("O-")) {
                return 80; // O is a universal donor!
            }
        }

        if (donorType.equals("A+") && (recipientType.equals("A+") || recipientType.equals("AB+"))) {
            return 100; // A matches: A & AB
        }
        if (donorType.equals("B+") && (recipientType.equals("B+") || recipientType.equals("AB+"))) {
            return 100; // B matches: B & AB
        }

        return 0; // for incompatible blood types :(
    }

    // for calculation of weight compatibility
    private int calculateWeightCompatibility(int organWeight, int patientWeight) {
        double weightRatio = (double) organWeight / (patientWeight * 1000);

        if (weightRatio >= 0.8 && weightRatio <= 1.2) {
            return 100; // Ideal match
        } else if (weightRatio >= 0.6 && weightRatio <= 1.4) {
            return 50;  // acceptable range!
        }

        return 0;  // not compatible :(
    }

    // for calculating the HLA compatibility
    private int calculateHlaCompatibility(String organHla, String patientHla) {
        String[] organHlaParts = organHla.split("-");
        String[] patientHlaParts = patientHla.split("-");

        int matchCount = 0;
        for (String organ : organHlaParts) {
            for (String patient : patientHlaParts) {
                if (organ.equals(patient)) {
                    matchCount++;
                }
            }
        }

        double matchPercentage = (double) matchCount / organHlaParts.length * 100;
        return (int) matchPercentage;
    }

    // for calculating the weighted compatibility
    public double[][] calculateWeightedCompatibility(double[] weights) {
        int[][] compatibilityMatrix = createCompatibilityMatrix();
        double[][] resultMatrix = new double[organs.size()][patients.size()];

        for (int i = 0; i < organs.size(); i++) {
            for (int j = 0; j < patients.size(); j++) {
                // blood type, weight, & HLA compatibility are stored in columns j*3, j*3+1, j*3+2
                double weightedScore = 0;
                for (int k = 0; k < 3; k++) {
                    weightedScore += compatibilityMatrix[i][j * 3 + k] * weights[k];
                }
                resultMatrix[i][j] = weightedScore;
            }
        }

        return resultMatrix;
    }

    // for displaying the initial compatibility matrix
    public void displayMatrix(int[][] matrix) {
        System.out.print("     ");
        for (int j = 0; j < patients.size(); j++) {
            System.out.print("P" + (j + 1) + "   ");
        }
        System.out.println();

        for (int i = 0; i < organs.size(); i++) {
            System.out.print("O" + (i + 1) + " ");
            for (int j = 0; j < patients.size(); j++) {
                System.out.print(matrix[i][j * 3] + "  " + matrix[i][j * 3 + 1] + "  " + matrix[i][j * 3 + 2] + " ");
            }
            System.out.println();
        }
    }

    // for displaying the weight matrix
    public void displayWeightMatrix(double[] weights) {
        System.out.println("\nWeight Matrix:");
        for (double weight : weights) {
            System.out.printf("%.2f  ", weight);
        }
        System.out.println();
    }

    // for displaying the final weighted compatibility matrix
    public void displayWeightedMatrix(double[][] matrix) {
        System.out.print("     ");
        System.out.print("\nFINAL Weighted Compatibility matrix\n");
        for (int j = 0; j < patients.size(); j++) {
            System.out.print("P" + (j + 1) + "     ");
        }
        System.out.println();

        for (int i = 0; i < organs.size(); i++) {
            System.out.print("O" + (i + 1) + " ");
            for (int j = 0; j < patients.size(); j++) {
                System.out.printf("%.1f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // ASSIGNMENT 7
    public List<Organ> getCompatibleOrgans(Patient patient) {
        return organs.stream()
                .filter(organ -> isCompatible(organ, patient))  // Filter based on compatibility
                .collect(Collectors.toList());
    }

    private boolean isCompatible(Organ organ, Patient patient) {
        return calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType()) > 0 &&
                calculateWeightCompatibility(organ.getWeight(), patient.getWeight()) > 0 &&
                calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType()) > 0;
    }

    public Map<Patient, List<Double>> calculateCompatibilityScores() {
        return patients.stream()
                .collect(Collectors.toMap(
                        patient -> patient,
                        patient -> organs.stream()
                                .map(organ -> calculateCompatibilityScore(organ, patient))  // Calculate compatibility score for each organ
                                .collect(Collectors.toList())
                ));
    }

    double calculateCompatibilityScore(Organ organ, Patient patient) {
        double bloodTypeScore = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
        double weightScore = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
        double hlaScore = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
        return (bloodTypeScore * 0.4) + (weightScore * 0.3) + (hlaScore * 0.3); //IMPORTANT
    }

    // ASSIGNMENT 8
    public Patient findCompatiblePatient(Organ organ, WaitingList waitingList) {
        WaitingListNode current = waitingList.getHead();
        Patient compatiblePatient = null;
        int highestPriority = -1;

        while (current != null) {
            Patient patient = current.getPatient();
            if (isCompatible(organ, patient) && current.getPriority() > highestPriority) {
                compatiblePatient = patient;
                highestPriority = current.getPriority();
            }
            current = current.getNext();
        }

        return compatiblePatient;
    }

}
