package com.openclassrooms.safetynetalert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class FireStationModel {

    private String address;
    private String station;

    public FireStationModel() {

    }
}
