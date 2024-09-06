
package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.model.FireModel;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.model.PersonFireStationModel;

import java.util.List;

public interface FireStationService {

    FireStationModel addFireStation(FireStationModel fireStationModel);

    FireStationModel updateFireStation(FireStationModel fireStationModel);
    

    //URL n°1 :  http://localhost:8080/firestation?stationNumber=<station_number>
    PersonFireStationModel getPersonCovertByFireStation(String stationNumber);

    //URL n°3 :
    List<String> getPhoneAlert(String stationNumber);

    //FireStationModel addFireStation(FireStationModel fireStationM);

    // URL n°4
    FireModel getFireMembersAddress(String address);

    Boolean deleteFireStation(String address);


}
