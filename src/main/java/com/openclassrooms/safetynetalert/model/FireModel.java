package com.openclassrooms.safetynetalert.model;


import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FireModel {

    private List<FireMembersModel> fireMembersModels;
    private String station;


}
