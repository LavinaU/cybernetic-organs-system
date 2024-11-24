package com.cybernetic;

import java.time.LocalDate;
import java.util.ArrayList;

public class CyberneticOrgan {

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public double getCompatibilityScore() {
        return compatibilityScore;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public ArrayList<String> getOrganTypes() {
        return types;
    }

    /**
     * This method validates the id
     * @param id
     * @return true if it is a valid id (format: ORG-XXXX)
     */
    private boolean validateId(String id) {
        if (id.startsWith(IDSTARTSWITH) && id.length() == IDLENGTH) {
            return true;
        } else {
            throw new IllegalArgumentException("Organ id is invalid.");
        }
    }

    /**
     * This method validates the type
     * @param type
     * @return true if it is a valid type (valid values: HEART, LUNG, KIDNEY, LIVER, BRAIN)
     */
    private boolean validateType(String type){
        if (types.contains(type)) {
            return true;
        } else {
            throw new IllegalArgumentException("Organ type is invalid.");
        }
    }

    /**
     * This method validates the powerLevel
     * @param powerLevel
     * @return true if it is a valid powerLevel (range: 1-100)
     */
    private boolean validatePowerLevel(int powerLevel){
        if (powerLevel >= POWERLEVELMIN &&
                powerLevel <= POWERLEVELMAX) {
            return true;
        } else {
            throw new IllegalArgumentException("Organ power level is invalid.");
        }
    }

    /**
     * This method validates the compatibilityScore
     * @param compatibilityScore
     * @return true if it is a valid compatibilityScore (range: 0.0-1.0)
     */
    public boolean validateCompatibilityScore(double compatibilityScore){
        if (compatibilityScore >= COMPATIBILITYMIN &&
                compatibilityScore <= COMPATIBILITYMAX) {
            return true;
        } else {
            throw new IllegalArgumentException("Organ compatibility score is invalid.");
        }
    }

    /**
     * This method validates the manufactureDate
     * @param manufactureDate
     * @return true if it is a valid manufactureDate (cannot be future date)
     */
    private boolean validateManufactureDate(LocalDate manufactureDate){
        if (manufactureDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Organ manufacture date is future date.");
        } else {
            return true;
        }
    }

    public CyberneticOrgan() {
    }

    public CyberneticOrgan(String id, String type, String model, int powerLevel, double compatibilityScore, LocalDate manufactureDate, String status, String manufacturer) {

        if(validateId(id)) {
            this.id = id;
        }
        if(validateType(type)) {
            this.type = type;
        }
        this.model = model;
        if(validatePowerLevel(powerLevel)) {
            this.powerLevel = powerLevel;
        }
        if(validateCompatibilityScore(compatibilityScore)) {
            this.compatibilityScore = compatibilityScore;
        }
        if(validateManufactureDate(manufactureDate)) {
            this.manufactureDate = manufactureDate;
        }
        this.status = status;
        this.manufacturer = manufacturer;
    }

    String id; // format: ORG-XXXX
    String type; // valid values: HEART, LUNG, KIDNEY, LIVER
    String model; // format: [type]X-[version] e.g., HEARTX-2000
    int powerLevel; // range: 1-100
    double compatibilityScore; // range: 0.0-1.0
    LocalDate manufactureDate;// manufactureDate: LocalDate (cannot be future date)
    String status;// valid values: AVAILABLE, ALLOCATED, DEFECTIVE
    String manufacturer;//manufacturer: String

    private final String IDSTARTSWITH = "ORG-";
    private final int IDLENGTH=8;
    private final int POWERLEVELMIN = 1;
    private final int POWERLEVELMAX = 100;
    private final double COMPATIBILITYMIN = 0.0;
    private final double COMPATIBILITYMAX = 1.0;

    // BRAIN type is not mentioned in the Organ properties.
    // But BRAIN is used in validation and sorting. That is why added here.
    private final ArrayList<String> types = new ArrayList<String>() {
        {
            add("HEART");
            add("LUNG");
            add("KIDNEY");
            add("LIVER");
            add("BRAIN");
        }
    };
    private ArrayList<String> statuss = new ArrayList<String>() {
        {
            add("AVAILABLE");
            add("ALLOCATED");
            add("DEFECTIVE");
        }
    };
}
