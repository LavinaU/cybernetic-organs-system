package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Main compatibility checker class
public class CyberneticOrganCompatibility {
    private List<String> incompatibilityReasons;

    public CyberneticOrganCompatibility() {
        this.incompatibilityReasons = new ArrayList<>();
    }

    public boolean isCompatible(Patient patient,
                                CyberneticOrgan organ,
                                DiagnosticDecisionTree diagnosticTree) {

        incompatibilityReasons.clear();
        boolean isCompatible = true;

        //Step 1: Get patient measurements and organ requirements
        // for getting measurements for patient
        Map<String, Double> patientMeasurements = patient.getAllMeasurements();
        // for getting the organ's necessary ranges
        Map<String, CyberneticOrgan.Range> organRequirements = organ.getRequirements();

        //Step 2: Run diagnostic tree analysis
        String diagnosis = diagnosticTree.diagnosePatient(patient.getAllMeasurements());

        if ("Inconclusive".equals(diagnosis)) {
            incompatibilityReasons.add("Diagnostic Tree Result: Inconclusive");
            return false;
        }

        //Step 3: Check each measurement against organ requirements
        for (Map.Entry<String, CyberneticOrgan.Range> entry : organ.getRequirements().entrySet()) {
            String measurementType = entry.getKey();
            CyberneticOrgan.Range range = entry.getValue();
            Double patientMeasurement = patient.getMeasurement(measurementType);

            if (patientMeasurement == null) {
                incompatibilityReasons.add(measurementType + " is missing for the patient.");
                continue;
            }

            if (patientMeasurement < range.min || patientMeasurement > range.max) {
                incompatibilityReasons.add(measurementType + " out of range: " + patientMeasurement +
                        " (required: " + range.min + " - " + range.max + ")");
            }
        }

        return incompatibilityReasons.isEmpty();

    }

    public List<String> getIncompatibilityReasons() {
        return new ArrayList<>(incompatibilityReasons);
    }
}