package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FireMembersModel {

       private String firstname;
       private String lastname;
       private String phone;
       private long age;
       private List<String> medications;
       private List<String> allergies;


}
