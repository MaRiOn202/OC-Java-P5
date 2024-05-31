
package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.model.PersonFireStationModel;

import java.util.List;

public interface FireStationService {



    public PersonFireStationModel getPersonFireStationModel(String stationNumber);

    public List<String> getPhoneAlert(String stationNumber);


    public List<FireStationEntity> findByStation(String stationNumber);
}
