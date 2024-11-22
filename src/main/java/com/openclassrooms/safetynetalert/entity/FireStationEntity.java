package com.openclassrooms.safetynetalert.entity;

import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FireStationEntity implements Serializable {

    private String station;
    private String address;



}
