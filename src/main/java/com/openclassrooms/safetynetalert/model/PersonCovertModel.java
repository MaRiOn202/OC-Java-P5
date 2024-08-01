package com.openclassrooms.safetynetalert.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonCovertModel {

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
