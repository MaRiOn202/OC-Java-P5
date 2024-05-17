package com.openclassrooms.safetynetalert.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class SafetyAlert {

    List<Person> persons;

    List<FireStation> firestations;
    
    List<MedicalRecord> medicalrecords;

    public List<FireStation> getFireStations() {
        return firestations;
    }

    public void setFireStations(List<FireStation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalrecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalrecords = medicalRecords;
    }

    
}
