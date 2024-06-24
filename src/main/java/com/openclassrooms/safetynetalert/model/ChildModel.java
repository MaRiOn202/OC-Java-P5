package com.openclassrooms.safetynetalert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ChildModel {

     private String firstName;
     private String lastName;
     private long age;

     private List<PersonModel> membersFamily;


    public ChildModel() {

    }
}
