package com.cybernetic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class OrganManagementSystem {

    private List<Organ> organs;
    private List<Patient> patients;

    public OrganManagementSystem(List<Organ> organs, List<Patient> patients) {
        this.organs = organs;
        this.patients = patients;
    }

    // for returning a set of  unique blood types
    public Set<String> getUniqueBloodTypes() {
        Set<String> bloodTypes = new HashSet<>();

        // for adding organ blood types
        organs.forEach(organ -> bloodTypes.add(organ.getBloodType()));

        // for adding patient blood types
        patients.forEach(patient -> bloodTypes.add(patient.getBloodType()));

        return bloodTypes;
    }

    // for groupng patients by their blood type, returns a Map<String, List<Patient>>
    public Map<String, List<Patient>> groupPatientsByBloodType() {
        return patients.stream().collect(Collectors.groupingBy(Patient::getBloodType));
    }

    // for soring organs by weight & returns the sorted list
    public List<Organ> sortOrgansByWeight() {
        return organs.stream()
                .sorted((o1, o2) -> Integer.compare(o1.getWeight(), o2.getWeight()))
                .collect(Collectors.toList());
    }

    // for retirning the top 'n' compatible organs for a given patient using streams
    public List<Organ> getTopCompatibleOrgans(Patient patient, int n) {
        return organs.stream()
                .sorted((o1, o2) -> Double.compare(
                        calculateCompatibilityScore(o2, patient),
                        calculateCompatibilityScore(o1, patient)))
                .limit(n)
                .collect(Collectors.toList());
    }

    // helper method for calculating compatibility score
    private double calculateCompatibilityScore(Organ organ, Patient patient) {
        double bloodTypeScore = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
        double weightScore = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
        double hlaScore = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
        return (bloodTypeScore * 0.4) + (weightScore * 0.3) + (hlaScore * 0.3);
    }

    // for blood type compatibility check
    private int calculateBloodTypeCompatibility(String organBloodType, String patientBloodType) {
        if (organBloodType.equals(patientBloodType)) {
            return 100; // Exact match!
        }
        return 0; // Not compatible :(
    }

    // for weight compatibility check
    private int calculateWeightCompatibility(int organWeight, int patientWeight) {
        double weightRatio = (double) organWeight / (patientWeight * 1000);
        return (weightRatio >= 0.8 && weightRatio <= 1.2) ? 100 : 0;
    }

    // for HLA compatibility check
    private int calculateHlaCompatibility(String organHla, String patientHla) {
        return organHla.equals(patientHla) ? 100 : 0;
    }
}