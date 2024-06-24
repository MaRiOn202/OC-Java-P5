package com.openclassrooms.safetynetalert.services.impl;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.services.FireStationService;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import com.openclassrooms.safetynetalert.services.PersonService;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynetalert.utils.AgeUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FireStationImpl implements FireStationService {

    @Autowired
    public SerializationDriver serializationDriver;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private AgeUtils age;

    public List<FireStationEntity> listFireStation;
    public List<PersonEntity> listPerson;

    /**
     *   Renvoie la liste des personnes couvertes par la caserne de pompier
     *   correspondante
     *   
     * @param stationNumber
     * @return
     */
    //URL n°1 :  http://localhost:8080/firestation?stationNumber=<station_number>
    @Override
    public PersonFireStationModel getPersonCovertByFireStation(String stationNumber) {

        long numberOfAdult;
        long numberOfChildren;

        List<FireStationEntity> listFireStation = fireStationService.findByStation(stationNumber);     //plusieurs address
        List<MedicalRecordEntity> listMedicalRecord = new ArrayList<>();
        List<PersonEntity> personsCovert =  new ArrayList<>();

        // associe l'adresse des habitants à celle de la caserne
        for(FireStationEntity fireStation : listFireStation)  {
            List<PersonEntity> result = personService
                    .findByAddress(fireStation.getAddress());
            personsCovert.addAll(result);
        }

        // Pour trouver le nbre adulte et enfants, passer par MedicalRecord
        for (PersonEntity person : personsCovert)   {
            MedicalRecordEntity result = medicalRecordService
                    .findByLastNameAndFirstName(person.getLastName(), person.getFirstName());
            listMedicalRecord.add(result);                      //recupere un objet
        }

        // permet de déterminer l'âge des individus et distinguer enfants/adultes  + nbre
        numberOfChildren =  listMedicalRecord
                .stream()
                .filter(medicalRecord -> age.getMinor(medicalRecord.getBirthdate()))
                .count();

        numberOfAdult = listMedicalRecord
                .stream()
                .filter(medicalRecord -> age.getMinor(medicalRecord.getBirthdate()))      //filtre sur false
                .count();


        //retourner l'objet PersonFireStation
        // retourne le résultat à savoir listHabitant + nbre enfants et nbre adultes
        PersonFireStationModel pfsm = new PersonFireStationModel();
        List<PersonModel> listHabitants = personsCovert
                .stream()
                .map(person -> new PersonModel(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getCity(),
                        person.getPhone()))
                .toList();

        pfsm.setMembers(listHabitants);
        pfsm.setNbreAdult(numberOfAdult);
        pfsm.setNbreEnfant(numberOfChildren);

        return pfsm;
    }


    /**
     *   Renvoie une liste des numéros de téléphone des habitants desservie
     *   par la caserne de pompier correspondante
     *   
     * @param stationNumber
     * @return
     */

    // URL n°3  http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @Override
    public List<String> getPhoneAlert(String stationNumber) {

        List<PersonEntity> listPerson = personService.getPersonList();
        List<FireStationEntity> listFireStation = fireStationService.findByStation(stationNumber);
        List<String> phones = new ArrayList<>();
        List<String> addressList = new ArrayList<>();

        // Récupère la liste des adresses des fireStation
        for (FireStationEntity fireStationEntity : listFireStation) {
            if (fireStationEntity.getStation().equals(stationNumber))
                addressList.add(fireStationEntity.getAddress());
        }

        // Associe les adresses aux personnes souhaitées 
        // Retourne les numéros de téléphone
        for (String address : addressList ) {
            for (PersonEntity personEntity : listPerson) {
                if(address.equals(personEntity.getAddress())) {
                    phones.add(personEntity.getPhone());
                }
            }
        }
        return phones;
    }

    @Override
    public List<FireStationEntity> findByStation(String stationNumber) {
        return fireStationService.findByStation(stationNumber);
    }

    @Override
    public FireStationEntity findByAddress(String address) {
        return fireStationService.findByAddress(address);
    }

    @Override
    public void deleteFireStation(String address) {
    }

    @Override
    public FireStationModel addFireStation(FireStationModel fireStationM) {

        FireStationEntity fireStationEntity = new FireStationEntity();
        fireStationEntity.setAddress(fireStationM.getAddress());
        fireStationEntity.setStation(fireStationM.getStation());      //model en entity

        FireStationEntity fse = fireStationRepository.addFireStation(fireStationEntity);
        FireStationModel fireStationModel = new FireStationModel();
        fireStationModel.setAddress(fse.getAddress());
        fireStationModel.setStation(fse.getStation());
        return fireStationModel;
    }


    /**
     *   Renvoie la liste des personnes couvertes par la caserne de pompiers correspondante
     *
     */
    @Override
    public PersonFireStationModel getPersonFireStationModel(String stationNumber) {

        return null;
    }


    /**
     *   Renvoie la liste des habitants vivant à une adresse donnée + num de la caserne
     *   correspondante 
     *
     */
    // URL n°4 :http://localhost:8080/fire?address=<address>
    @Override
    public FireModel getFireMembersAddress(String address) {

        List<PersonEntity> listPerson = personService.findByAddress(address);
        FireStationEntity fireStation = fireStationService.findByAddress(address);
        
        // récupérer la liste des habitants selon l'adresse d'une fireStation
        // Faire coincider l'adresse des habitants avec celle des fireStations
        //et une fois qu'on a les adresses de FS on récupère le n° des stations
        // ou
        // associer adresse des FS aux n° des stations
        // Et associer adresse des hab avec celles des FS et construire la liste avec les
        // paramètres voulus num tel age et MR

        List<FireMembersModel> listFireMembersModel = new ArrayList<>();
        for (PersonEntity personEntity : listPerson) {

            FireMembersModel fireMembersModel = new FireMembersModel();
            fireMembersModel.setFirstname(personEntity.getFirstName());
            fireMembersModel.setLastname(personEntity.getLastName());
            fireMembersModel.setPhone(personEntity.getPhone());
            MedicalRecordEntity medicalRecordEntity = medicalRecordService
                    .findByLastNameAndFirstName
                    (personEntity.getLastName(), personEntity.getFirstName());
            fireMembersModel.setAge(age.getAge(medicalRecordEntity.getBirthdate()));
            fireMembersModel.setMedications(medicalRecordEntity.getMedications());
            fireMembersModel.setAllergies(medicalRecordEntity.getAllergies());
            listFireMembersModel.add(fireMembersModel);
        }

        FireModel fireModel = new FireModel();
        fireModel.setFireMembersModels(listFireMembersModel);
        fireModel.setStation(fireStation.getStation());

        return fireModel;
    }



}