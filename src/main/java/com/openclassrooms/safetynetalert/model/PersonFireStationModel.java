package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonFireStationModel {

    private List<PersonCovertModel> members;
    private Long nbreAdult;       //+ 18 ans
    private Long nbreEnfant;      //- 18 ans


}
