
package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.model.FireModel;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.model.PersonFireStationModel;

import java.util.List;

public interface FireStationService {

    FireStationModel addFireStation(FireStationModel fireStationModel);

    FireStationModel updateFireStation(FireStationModel fireStationModel);
    

    //URL n°1 :
    /**
     *   Renvoie la liste des personnes couvertes par la caserne de pompier
     *   correspondante
     *
     * @param stationNumber
     * @return PersonFireStationModel
     */
    PersonFireStationModel getPersonCovertByFireStation(String stationNumber);


    //URL n°3 :
    /**
     *   Renvoie une liste des numéros de téléphone des habitants desservie
     *   par la caserne de pompier correspondante
     *
     * @param stationNumber
     * @return List<String>
     */
    List<String> getPhoneAlert(String stationNumber);


    // URL n°4
    /**
     *   Renvoie la liste des habitants vivant à une adresse donnée + num de la caserne
     *   correspondante
     *
     * @param address
     * @return FireModel
     */
    FireModel getFireMembersAddress(String address);

    Boolean deleteFireStation(String address);
    
}
  //javadoc 