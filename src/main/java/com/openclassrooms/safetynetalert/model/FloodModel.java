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
public class FloodModel {

    private List<FamilyWithoutChildModel> famille;
    
    public FloodModel() {

    }
}
