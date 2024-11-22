package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FamilyModel {

    private List<PersonModel> members;
    private List<ChildModel> children;



}
