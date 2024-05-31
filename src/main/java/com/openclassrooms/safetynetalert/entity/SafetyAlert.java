package com.openclassrooms.safetynetalert.entity;


import com.openclassrooms.safetynetalert.model.MedicalRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class SafetyAlert {

    List<PersonEntity> persons;

    List<FireStationEntity> firestations;
    
    List<MedicalRecordEntity> medicalrecords;

    public List<FireStationEntity> getFireStations() {
        return firestations;
    }

    public void setFireStations(List<FireStationEntity> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecordEntity> getMedicalRecords() {
        return medicalrecords;
    }

    public void setMedicalRecords(List<MedicalRecordEntity> medicalRecords) {
        this.medicalrecords = medicalRecords;
    }


}
