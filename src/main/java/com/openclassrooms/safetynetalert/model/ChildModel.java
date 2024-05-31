package com.openclassrooms.safetynetalert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ChildModel {

     private String firstName;
     private String lastName;
     private long age;

     private List<PersonModel> membersFamily;


}
