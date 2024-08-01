package com.openclassrooms.safetynetalert.model;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonModel {

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String email;

    
}
