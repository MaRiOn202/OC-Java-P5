package com.openclassrooms.safetynetalert.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class FireStationEntity implements Serializable {

    private String station;
    private String address;

    public FireStationEntity() {
    }


    public FireStationEntity(String address, String station) {
        this.address = address;
        this.station = station;
    }


}
