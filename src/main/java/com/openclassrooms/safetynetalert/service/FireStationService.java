package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    @Autowired
    public SerializationDriver serializationDriver;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<FireStation> listFireStation = new ArrayList<>();
    public List<Person> listPerson = new ArrayList<>();




    //URL n°1 :  http://localhost:8080/firestation?stationNumber=<station_number>

 public  PersonFireStation getPersonCovertByFireStation(String stationNumber) {
      //recuperer le fireStation à partir du stationNumber
     //recuperer la liste des personnes à partir de l'adresse du fireStation
     // (enrichir le repository chercher personne
     // à partir de leur adresse
     // déterminer le nbreadulte et enfants
     // enrichir puis retourner

     long nbreAdulte;
     long nbreEnfant;


     List<FireStation> listFireStation = fireStationRepository.findByStation(stationNumber);     //plusieurs address
     List<MedicalRecord> listMedicalRecord = new ArrayList<>();
     List<Person> personsCovert =  new ArrayList<>();


     for(FireStation fireStation : listFireStation)  {
         List<Person> result = personRepository.findByAddress(fireStation.getAddress());
         personsCovert.addAll(result);
     }


     for (Person person : personsCovert)   {
        MedicalRecord result = medicalRecordRepository.findByLastNameAndFirstName(person.getLastName(), person.getFirstName());
        listMedicalRecord.add(result);                      //recupere un objet
     }

     nbreEnfant =  listMedicalRecord.stream()
             .filter(medicalRecord -> this.getMinor(medicalRecord.getBirthdate()))
             .count();

     nbreAdulte = listMedicalRecord.stream()
             .filter(medicalRecord -> !this.getMinor(medicalRecord.getBirthdate()))            //filtre sur false
             .count();


     //retourner l'objet PersonFireStation
     PersonFireStation pfs = new PersonFireStation();
     List<PersonDto> listHabitants = personsCovert.stream()
             .map(person -> new PersonDto(
                     person.getFirstName(),
                     person.getLastName(),
                     person.getAddress(),
                     person.getCity(),
                     person.getPhone()))
             .toList();

     pfs.setMembers(listHabitants);
     pfs.setNbreAdult(nbreAdulte);
     pfs.setNbreEnfant(nbreEnfant);

     return pfs;
 }

 public boolean getMinor(String birthdate) {

     LocalDate currentTime = LocalDate.now();
     LocalDate birthdayTime = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(birthdate) );
      Duration result =  Duration.between(currentTime,birthdayTime);

     if(result.toDays()/365 <= 18 ) {
           return true;
     }
     return false;
 }



   // URL n°3  http://localhost:8080/phoneAlert?firestation=<firestation_number>
    
 public List<String> getListOfPhoneNumber(String stationNumber) {

     List<Person> listPerson = personRepository.getPersonList();
     List<FireStation> listFireStation = fireStationRepository.findByStation(stationNumber);
     List<String> phones = new ArrayList<>();
     List<String> addressList = new ArrayList<>();

     for (FireStation fireStation : listFireStation) {
         if (fireStation.getStation().equals(stationNumber))
             addressList.add(fireStation.getAddress());
     }
     for (String address : addressList ) {
         for (Person person : listPerson) {
             if(address.equals(person.getAddress())) {
                 phones.add(person.getPhone());
             }
         }
     }
     return phones;
 }









}
