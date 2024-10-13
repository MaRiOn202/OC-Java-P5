package com.openclassrooms.safetynetalert.services.impl;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.mapper.FireStationMapper;
import com.openclassrooms.safetynetalert.mapper.PersonMapper;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.FireStationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynetalert.utils.AgeUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FireStationImpl implements FireStationService {



    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AgeUtils age;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private FireStationMapper fireStationMapper;

    private static final Logger log = LoggerFactory.getLogger(FireStationImpl.class);

    @Override
    public FireStationModel addFireStation(FireStationModel fireStationM) {

        FireStationEntity fireStationEntity = new FireStationEntity();
        fireStationEntity.setAddress(fireStationM.getAddress());
        fireStationEntity.setStation(fireStationM.getStation());      //model en entity

        FireStationEntity fse = fireStationRepository.addFireStation(fireStationEntity);
        FireStationModel fireStationModel = new FireStationModel();
        fireStationModel.setAddress(fse.getAddress());
        fireStationModel.setStation(fse.getStation());
        log.info("La caserne de pompier a bien été créée !" );
        return fireStationModel;
    }

    @Override
    public FireStationModel updateFireStation(FireStationModel fireStationModel) {
        FireStationEntity fireStationUpdate =
                fireStationRepository.findByAddress(fireStationModel.getAddress());
        fireStationUpdate.setStation(fireStationModel.getStation());
        fireStationUpdate = fireStationRepository.updateFireStation(fireStationUpdate);
        
        // fireStationEntity en FSModel
        FireStationModel stationUpdated =  new FireStationModel();
        stationUpdated.setAddress(fireStationUpdate.getAddress());
        stationUpdated.setStation(fireStationUpdate.getStation());
        // donc mapper mapToFireStationModel
        log.info("La caserne de pompier a bien été mise à jour !" );
        return stationUpdated;
    }


    @Override
    public Boolean deleteFireStation(String address) {
            // à faire
            final boolean fireStationDeleted = fireStationRepository.deleteFireStation(address);
            if(fireStationDeleted) {
                log.info("La caserne de pompier " + address + " a bien été supprimée");
            } else {
                log.info("La caserne de pompier " + address + " n' a pas été supprimée");
            }
            return fireStationDeleted;
    }

    
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

        List<FireStationEntity> listFireStation = fireStationRepository.findByStation(stationNumber);     //plusieurs address
        List<MedicalRecordEntity> listMedicalRecord = new ArrayList<>();
        List<PersonEntity> personsCovert =  new ArrayList<>();

        // Récupère la liste des personnes autour d'une caserne 
        for(FireStationEntity fireStationEntity : listFireStation)  {
            List<PersonEntity> personEntity = personRepository
                    .findByAddress(fireStationEntity.getAddress());
            personsCovert.addAll(personEntity);
        }

        // Pour trouver le nbre adulte et enfants, passer par MedicalRecord
        for (PersonEntity personEntity : personsCovert)   {
            MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                    .findByLastNameAndFirstName(personEntity.getLastName(), personEntity.getFirstName());
            listMedicalRecord.add(medicalRecordEntity);                      //recupere un objet
        }

        // permet de déterminer l'âge des individus et distinguer enfants/adultes + nbre
        numberOfChildren =  listMedicalRecord
                .stream()
                .filter(medicalRecord -> age.getMinor(medicalRecord.getBirthdate()))
                .count();

        numberOfAdult = listMedicalRecord
                .stream()
                .filter(medicalRecord -> !age.getMinor(medicalRecord.getBirthdate()))      //filtre sur false
                .count();
        
        //retourner l'objet PersonFireStation
        // retourne le résultat à savoir listHabitant + nbre enfants et nbre adultes
        PersonFireStationModel pfsm = new PersonFireStationModel();
        List<PersonCovertModel> listHabitants = personsCovert
                .stream()
                .map(personEntity -> personMapper.mapToPersonCovertModel(personEntity))
/*                .map(personEntity -> new PersonCovertModel(
                        personEntity.getFirstName(),
                        personEntity.getLastName(),
                        personEntity.getPhone(),
                        personEntity.getAddress()))*/
                .toList();
        pfsm.setMembers(listHabitants);
        pfsm.setNbreAdult(numberOfAdult);
        pfsm.setNbreEnfant(numberOfChildren);

        return pfsm;
    }


    /**
     *   URL n°3  http://localhost:8080/phoneAlert?firestation=<firestation_number>
     *
     *   Renvoie une liste des numéros de téléphone des habitants desservie
     *   par la caserne de pompier correspondante
     *   
     * @param stationNumber
     * @return List<String> 
     */
    @Override
    public List<String> getPhoneAlert(String stationNumber) {

        List<PersonEntity> listPerson = personRepository.getPersonList();
        List<FireStationEntity> listFireStation = fireStationRepository.findByStation(stationNumber);
        List<String> phones = new ArrayList<>();
        List<String> addressList = new ArrayList<>();

        // Récupère la liste des adresses des fireStation
        for (FireStationEntity fireStationEntity : listFireStation) {
                addressList.add(fireStationEntity.getAddress());
        }

        // Associe les adresses aux personnes souhaitées
        for (String address : addressList ) {
            for (PersonEntity personEntity : listPerson) {
                if(address.equals(personEntity.getAddress())) {
                    phones.add(personEntity.getPhone());
                }
            }
        }
        return phones;
    }

    

    /**
     *   Renvoie la liste des habitants vivant à une adresse donnée + num de la caserne
     *   correspondante 
     *
     */
    // URL n°4 :http://localhost:8080/fire?address=<address>
    @Override
    public FireModel getFireMembersAddress(String address) {

        log.info("address: {}", address);
        List<PersonEntity> listPerson = personRepository.findByAddress(address);
        FireStationEntity fireStationEntity = fireStationRepository.findByAddress(address);

        List<FireMembersModel> listFireMembersModel = new ArrayList<>();
        for (PersonEntity personEntity : listPerson) {
/*            FireMembersModel fireMembersModel = new FireMembersModel();
            fireMembersModel.setFirstname(personEntity.getFirstName());
            fireMembersModel.setLastname(personEntity.getLastName());
            fireMembersModel.setPhone(personEntity.getPhone());
            MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                    .findByLastNameAndFirstName
                    (personEntity.getLastName(), personEntity.getFirstName());
            fireMembersModel.setAge(age.getAge(medicalRecordEntity.getBirthdate()));
            fireMembersModel.setMedications(medicalRecordEntity.getMedications());
            fireMembersModel.setAllergies(medicalRecordEntity.getAllergies());
            listFireMembersModel.add(fireMembersModel);*/
            
            MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                    .findByLastNameAndFirstName
                            (personEntity.getLastName(), personEntity.getFirstName());
            long resultAge = age.getAge(medicalRecordEntity.getBirthdate());
            listFireMembersModel.add(fireStationMapper.mapToFireMembersModel
                    (resultAge, personEntity, medicalRecordEntity));
        }

        FireModel fireModel = new FireModel();
        fireModel.setFireMembersModels(listFireMembersModel);
        fireModel.setStation(fireStationEntity.getStation());

        return fireModel;
    }
    
}