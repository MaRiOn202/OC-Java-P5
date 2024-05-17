package com.openclassrooms.safetynetalert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class FireStation {

    private String station;

    private String address;

    public FireStation() {
    }


    public FireStation( String address, String station) {
        this.address = address;
        this.station = station;
    }

  
}
