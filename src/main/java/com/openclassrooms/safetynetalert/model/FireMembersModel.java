package com.openclassrooms.safetynetalert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Setter
@Getter
@ToString
public class FireMembersModel {

       private String firstname;
       private String lastname;
       private String phone;
       private long age;
       private List<String> medications;
       private List<String> allergies;


}