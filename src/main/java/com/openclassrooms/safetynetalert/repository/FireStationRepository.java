package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class FireStationRepository {

    @Autowired
    public SerializationDriver serializationDriver;

    public List<FireStationEntity> listFireStation = new ArrayList<>();

    public FireStationRepository( SerializationDriver serializationDriver) {
        this.serializationDriver = serializationDriver;
        this.listFireStation = getFireStationList();
    }


    /**
     *   Récupère l'ensemble des données des fireStations du fichier json
     *   @ return une liste des fireStations
     */
    public List<FireStationEntity> getFireStationList() {
        return serializationDriver.safetyAlert.getFireStations();
    }

    public FireStationEntity addFireStation(FireStationEntity fireStationEntity) {
        this.listFireStation.add(fireStationEntity);
        return fireStationEntity;
    }


    public FireStationEntity updateFireStation(FireStationEntity fireStationUpdate) {
        listFireStation = getFireStationList();
        FireStationEntity fireStationUpdated = new FireStationEntity();
        for(FireStationEntity fireStationEntity : listFireStation) {
            if(fireStationEntity.getAddress().equalsIgnoreCase(fireStationUpdate.getAddress())) {
                fireStationEntity.setStation(fireStationUpdate.getStation());
            }
             return fireStationUpdate;
        }
        return fireStationUpdated;
    }

    
    public Boolean deleteFireStation(String address) {
        listFireStation = getFireStationList();
        for (FireStationEntity fireStation : listFireStation) {
            if (fireStation.getAddress().equals(address))
                return listFireStation.remove(fireStation);   //True
        }
        return false;
    }


    public List<FireStationEntity> findByStation(String station) {
        List<FireStationEntity> fireStationResult = new ArrayList<>();
        listFireStation = getFireStationList();
        for (FireStationEntity fireStation : listFireStation) {
            if (fireStation.getStation().equals(station))
                fireStationResult.add(fireStation);
        }
        return fireStationResult;
    }

    public FireStationEntity findByAddress(String address) {
        listFireStation = getFireStationList();
        for (FireStationEntity fireStation : listFireStation) {
            log.info("fireStation.getAddress(): {}", fireStation.getAddress());
            if (fireStation.getAddress().equals(address))
                return fireStation;
        }
        return null;
    }


}
