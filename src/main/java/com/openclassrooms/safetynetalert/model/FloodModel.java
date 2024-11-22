package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FloodModel {

    private Map<String, List<PersonInfoModel>> listFamille;
    

}
