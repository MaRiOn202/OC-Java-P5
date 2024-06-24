
package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.model.FireModel;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.model.PersonFireStationModel;

import java.util.List;

public interface FireStationService {

    public FireStationModel addFireStation(FireStationModel fireStationModel);

    public PersonFireStationModel getPersonFireStationModel(String stationNumber);

    //URL n°1 :  http://localhost:8080/firestation?stationNumber=<station_number>
    PersonFireStationModel getPersonCovertByFireStation(String stationNumber);

    public List<String> getPhoneAlert(String stationNumber);

    public FireModel getFireMembersAddress(String address);


    public List<FireStationEntity> findByStation(String station);

    public FireStationEntity findByAddress(String address);

    public void deleteFireStation(String address);
}
