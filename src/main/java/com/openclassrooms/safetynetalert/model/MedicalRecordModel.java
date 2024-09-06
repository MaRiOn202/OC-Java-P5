package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordModel {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications ;
    private List<String> allergies;
}
