package com.openclassrooms.safetynetalert.services.impl;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.FireStationService;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import com.openclassrooms.safetynetalert.services.PersonService;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FireStationImpl implements FireStationService {

    @Autowired
    public SerializationDriver serializationDriver;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    public List<FireStationEntity> listFireStation = new ArrayList<>();
    public List<PersonEntity> listPerson = new ArrayList<>();

    /**
     *   Renvoie la liste des personnes couvertes par la caserne de pompier
     *   correspondante
     *   
     * @param stationNumber
     * @return
     */


    //URL n°1 :  http://localhost:8080/firestation?stationNumber=<station_number>

    public PersonFireStationModel getPersonCovertByFireStation(String stationNumber) {

        long numberOfAdult;
        long numberOfChildren;


        List<FireStationEntity> listFireStation = fireStationRepository.findByStation(stationNumber);     //plusieurs address
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
                .filter(medicalRecord -> this.getMinor(medicalRecord.getBirthdate()))
                .count();

        numberOfAdult = listMedicalRecord
                .stream()
                .filter(medicalRecord -> !this.getMinor(medicalRecord.getBirthdate()))      //filtre sur false
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
    //  calcule si une personne est mineure ou pas sous forme de booléen
    public boolean getMinor(String birthdate) {

        LocalDate currentTime = LocalDate.now();
        LocalDate birthdayTime = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd")
                .format(birthdate) );
        Duration result =  Duration.between(currentTime,birthdayTime);

        if(result.toDays()/365 <= 18 ) {
            return true;
        }
        return false;
    }


    /**
     *   Renvoie une liste des numéros de téléphone des habitants desservie
     *   par la caserne de pompier correspondante
     *   
     * @param stationNumber
     * @return
     */

    // URL n°3  http://localhost:8080/phoneAlert?firestation=<firestation_number>

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
        return fireStationRepository.findByStation(stationNumber);
    }

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

    public FireModel getFireMembersAddress(String address) {

        List<PersonEntity> listPerson = personService.findByAddress(address);
        List<FireStationEntity> listFireStation = fireStationService.findByStation(address);
        List<MedicalRecordEntity> listMedicalRecordEntities = medicalRecordService.getMedicalRecordList();

        
        // récupérer la liste des habitants selon l'adresse d'une fireStation
        List<PersonEntity> personsCovert =  new ArrayList<>();

        for (FireStationEntity fireStation : listFireStation)  {
            List<PersonEntity> result = personService
                    .findByAddress(fireStation.getAddress());
            personsCovert.addAll(result);
        }
        // Construit un nouvel objet FireModel
        List<FireModel> listFireModel = new ArrayList<>();

        //for (FireStationEntity fireStationEntity : )


        // récupérer le num de la fireStation avec les adresses
        for (FireStationEntity fireStation : listFireStation) {
            List<FireStationEntity> result = fireStationService
                    .findByStation(fireStation.getStation());
            
        }

        //
        

        return null;
    }

}