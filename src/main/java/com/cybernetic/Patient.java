package com.cybernetic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Patient {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getOrganNeeded() {
        return organNeeded;
    }

    /**
     * This method validates the id of a patient
     * @param id
     * @return true if it is a valid id (format: PAT-XXXX)
     */
    private boolean validateId(String id) {
        if (id.startsWith(IDSTARTSWITH) && id.length() == IDLENGTH) {
            return true;
        } else {
            throw new IllegalArgumentException("Patient id is invalid.");
        }
    }

    /**
     * This method validates the age of a patient
     * @param age
     * @return true if it is a valid age (range: 1-120)
     */
    private boolean validateAge(int age){
        if (age >= AGEMIN && age <= AGEMAX) {
            return true;
        } else {
            throw new IllegalArgumentException("Patient age is invalid.");
        }
    }

    /**
     * This method validates the bloodType of a patient
     * @param bloodType
     * @return true if it is a valid bloodType (valid values: A+, A-, B+, B-, AB+, AB-, O+, O-)
     */
    private boolean validateBloodType(String bloodType){
        if (bloodTypes.contains(bloodType)) {
            return true;
        } else {
            throw new IllegalArgumentException("Patient blood type is invalid.");
        }
    }

    /**
     * This method validates the organNeeded of a patient
     * @param organNeeded
     * @return true if it is a valid organNeeded (valid values same as organ type)
     */
    public boolean validateOrganNeeded(String organNeeded){
        CyberneticOrgan cyberneticOrgan = new CyberneticOrgan();
        if (cyberneticOrgan.getOrganTypes().contains(organNeeded)) {
            return true;
        } else {
            throw new IllegalArgumentException("Patient Organ type is invalid.");
        }
    }

    /**
     * This method validates the urgencyLevel of a patient
     * @param urgencyLevel
     * @return true if it is a valid urgencyLevel (range: 1-10)
     */
    private boolean validateUrgencyLevel(int urgencyLevel){
        if (urgencyLevel >= URGENCYLEVELMIN && urgencyLevel <= URGENCYLEVELMAX) {
            return true;
        } else {
            throw new IllegalArgumentException("Urgency level is invalid.");
        }
    }

    /**
     * This method validates the status of a patient
     * @param status
     * @return true if it is a valid status (valid values: WAITING, MATCHED, TRANSPLANTED)
     */
    private boolean validateStatus(String status){
        if (statuses.contains(status)) {
            return true;
        } else {
            throw new IllegalArgumentException("Patient status is invalid.");
        }
    }

    public Patient(String id, String name, int age, String bloodType, String organNeeded, int urgencyLevel, LocalDate registrationDate, String status) {

        if(validateId(id)){
            this.id = id;
        }
        this.name = name;
        if(validateAge(age)){
            this.age = age;
        }
        if(validateBloodType(bloodType)){
            this.bloodType = bloodType;
        }
        if(validateOrganNeeded(organNeeded)){
            this.organNeeded = organNeeded;
        }
        if(validateUrgencyLevel(urgencyLevel)){
            this.urgencyLevel = urgencyLevel;
        }
        this.registrationDate = registrationDate;
        if(validateStatus(status)){
            this.status = status;
        }
    }

    String id; // format: PAT-XXXX
    String name; //
    int age; // range: 1-120
    String bloodType; // valid values: A+, A-, B+, B-, AB+, AB-, O+, O-
    String organNeeded; // valid values same as organ type
    int urgencyLevel; // range: 1-10, 10 being most urgent
    LocalDate registrationDate; //
    String status; // valid values: WAITING, MATCHED, TRANSPLANTED

    // constants for validations
    private final String IDSTARTSWITH = "PAT-";
    private final int IDLENGTH=8;
    private final int AGEMIN = 1;
    private final int AGEMAX = 120;
    private final int URGENCYLEVELMIN = 1;
    private final int URGENCYLEVELMAX = 10;
    private final ArrayList<String> bloodTypes = new ArrayList<String>() {
        {
            add("A+");
            add("A-");
            add("B+");
            add("B-");
            add("AB+");
            add("AB-");
            add("O+");
            add("O-");
        }
    };
    private final ArrayList<String> statuses = new ArrayList<String>() {
        {
            add("WAITING");
            add("MATCHED");
            add("TRANSPLANTED");
        }
    };
}
