package com.openclassrooms.safetynetalert.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class FireModel {

    private List<FireMembersModel> fireMembersModels;
    private int station; 


}
