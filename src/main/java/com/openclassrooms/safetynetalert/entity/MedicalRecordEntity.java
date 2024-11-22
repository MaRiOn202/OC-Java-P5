package com.openclassrooms.safetynetalert.entity;


import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordEntity {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications ;
    private List<String> allergies;

    

}