package com.openclassrooms.safetynetalert.model;


import lombok.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {



    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String phone;



}
