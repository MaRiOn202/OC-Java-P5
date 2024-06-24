package com.openclassrooms.safetynetalert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PersonFireStationModel {

    private List<PersonModel> members;
    private Long nbreAdult;       //+ 18 ans
    private Long nbreEnfant;      //- 18 ans

    public PersonFireStationModel() {

    }
}
