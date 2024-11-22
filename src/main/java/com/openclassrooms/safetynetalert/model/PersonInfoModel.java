package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoModel {

    private String firstName;
    private String lastName;
    private String address;
    private long age;
    private String email;
    private List<String> medications;
    private List<String> allergies;

   
}
