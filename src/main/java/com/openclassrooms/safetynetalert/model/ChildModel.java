package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChildModel {

     private String firstName;
     private String lastName;
     private long age;
     private List<PersonModel> membersFamily;


}
