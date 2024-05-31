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
    private String address;
    private String city;
    private String phone;
    
}
