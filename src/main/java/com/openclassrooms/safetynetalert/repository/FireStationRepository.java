package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FireStationRepository {

    @Autowired
    public SerializationDriver serializationDriver;

    public List<FireStation> listFireStation = new ArrayList<>();

    public FireStationRepository( SerializationDriver serializationDriver) {
        this.serializationDriver = serializationDriver;
        this.listFireStation = getFireStationList();
    }


    public List<FireStation> getFireStationList() {

        return serializationDriver.safetyAlert.getFireStations();
    }

    public void addFireStation(FireStation fireStation) {

        this.listFireStation.add(fireStation);
    }

    public List<FireStation> findByStation(String station) {
        List<FireStation> fireStationResult = new ArrayList<>();
        listFireStation = getFireStationList();
        for (FireStation fireStation : listFireStation) {
            if (fireStation.getStation().equals(station))
                fireStationResult.add(fireStation);
        }
        return fireStationResult;
    }

    public FireStation findByAddress(String address) {
        listFireStation = getFireStationList();
        for (FireStation fireStation : listFireStation) {
            if (fireStation.getAddress().equals(address))
                return fireStation;
        }
        return null;
    }



    // à gérer dans le service exception "pas de station trouvée
    //rajouter par station  aussi car rapporte plusieurs firestation

/*    public FireStation findAll(String station, String address) {
        for(FireStation fireStation : listFireStation) {
            if(fireStation.getStation().equals(station)
            && fireStation.getAddress().equals(address))
                return fireStation;
        }
        return new FireStation();
    }*/


/*    public List<FireStation> removeFireStation(FireStation fireStation) {
        listFireStation = getFireStationList();
        this.listFireStation.remove(fireStation);
        return null;
    }*/

    public Boolean removeFireStation(String address) {
        listFireStation = getFireStationList();
        for (FireStation fireStation : listFireStation) {
            if (fireStation.getAddress().equals(address))
                return listFireStation.remove(fireStation);   //True
        }
        return false;

    }


}
