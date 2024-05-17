package com.openclassrooms.safetynetalert.model;


import lombok.*;

import java.util.List;

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
