package com.openclassrooms.safetynetalert.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Setter
@Getter
@ToString
public class MedicalRecordEntity {

    private String firstName;
    private String lastName;

    private String birthdate;

    private List<String> medications ;

    private List<String> allergies;


    public MedicalRecordEntity(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecordEntity() {

    }

}