package com.openclassrooms.safetynetalert.services.impl;


import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.mapper.PersonMapper;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.PersonService;
import com.openclassrooms.safetynetalert.utils.AgeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;



@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private final AgeUtils age;

    @Autowired
    private final PersonMapper personMapper;

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private final FireStationRepository fireStationRepository;

    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    public PersonServiceImpl(AgeUtils age, PersonMapper personMapper, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, FireStationRepository fireStationRepository) {
        this.age = age;
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.fireStationRepository = fireStationRepository;
    }

    @Override
    public PersonModel addPerson(PersonModel personM) {
        PersonEntity personEntity = personMapper.mapToPersonEntity(personM);

        PersonEntity pe = personRepository.addPerson(personEntity);

        PersonModel personModel = personMapper.mapToPersonModel(pe);

        log.info("La personne a bien été créée !" );
        return personModel;
    }

    @Override
    public PersonModel updatePerson(PersonModel personModel) {
        PersonEntity personUpdate = personRepository.findByLastNameAndFirstName(personModel.getLastName(), personModel.getFirstName());
        personUpdate = personMapper.mapToPersonEntity(personModel);
        //sauvegarder
        personUpdate = personRepository.updatePerson(personUpdate);
        PersonModel personReturn = personMapper.mapToPersonModel(personUpdate);
        log.info("La personne a bien été mise à jour !" );
        return personReturn;
    }

    @Override
    public Boolean deletePerson(String lastName, String firstName) {
         final boolean personDeleted = personRepository.deletePerson(lastName, firstName);
         if(personDeleted) {
             log.info("La personne " + lastName + " " + firstName + " a bien été supprimée");
         } else {
             log.info("La personne " + lastName + " " + firstName + " n'a pas été supprimée");
         }
        return personDeleted;
    }


    /**
     *   Renvoie une liste d'enfants habitant à une adresse.
     *   Nom + prénom + âge ainsi que la liste des autres membres du foyer.
     *
     */
    // URL n°2 : http://localhost:8080/childAlert?address=<address>
    @Override
    public List<ChildModel> getChildAlert(String address) {

        List<PersonEntity> listPerson = personRepository.findByAddress(address);

        // Si personne n'est trouvé renvoie liste vide
        if (listPerson.isEmpty()) {
            return Collections.emptyList();
        }

        // Construit un nouvel objet : ChildModel
        List<ChildModel> listChildModel = new ArrayList<>();

        for (PersonEntity personEntity : listPerson)   {

            MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                    .findByLastNameAndFirstName(personEntity.getLastName(), personEntity.getFirstName());

            // enfant mineur ?
            if (age.getMinor(medicalRecordEntity.getBirthdate())) {
                ChildModel childModel = new ChildModel();
                childModel.setLastName(medicalRecordEntity.getLastName());
                childModel.setFirstName(medicalRecordEntity.getFirstName());
                childModel.setAge(age.getAge(medicalRecordEntity.getBirthdate()));

                // listPersonFamily
                List<PersonModel> listPersonFamily = listPerson
                        .stream()
                        .filter(person -> person.getLastName().equals(childModel.getLastName())
                                && person.getAddress().equals(address))   // habitant à la même adresse
                        .map(personMapper::mapToPersonModel)

                        //.map(personE -> personMapper.mapToPersonModel(personE))
                        .toList();

                childModel.setMembersFamily(listPersonFamily);        
                listChildModel.add(childModel);
            }

        }

        // Pb de la liste vide
        if (listChildModel.isEmpty()) {
            return Collections.emptyList();
        }
        
        return listChildModel;  // correspond à l'objet ChildModel
    }


    /**
     *   Renvoie la liste de tous les foyers desservis par une caserne.
     *   Regroupe les personnes par adresse.
     *
     */
    // URL n°5 :   http://localhost:8080/flood/stations?stations=<a list of station_numbers>
   @Override
   public FloodModel getFlood(List<String> stationNumber) {

        List<PersonEntity> listPerson = personRepository.getPersonList();
       List<FireStationEntity> listFireStationModel  = stationNumber
               .stream()
               .map(station -> fireStationRepository.findByStation(station))
               .flatMap(fireStationEntities -> fireStationEntities.stream()).toList();

        List<String> addressListStation = new ArrayList<>();

        // Récupérer la liste des adresses des FS
        for (FireStationEntity fireStationEntity : listFireStationModel) {
            if (stationNumber.contains(fireStationEntity.getStation()))  {
                addressListStation.add(fireStationEntity.getAddress());
            }
        }
        
       // Chaque adresse récupère liste de personnes / regroupe par famille par address
       // Créer famille correspondante et les ajouter dans une liste
       FloodModel floodModel = new FloodModel();
        
       Map<String, List<PersonEntity>> listPersonEntity = addressListStation
               .stream()
               .map(address ->  {
           List<PersonEntity> listPersonByAddress = listPerson
                   .stream()
                   .filter(personModel
                          -> personModel.getAddress().equals(address)).collect(toList());
           return listPersonByAddress;
       })
               .flatMap(Collection::stream)     //récupère le contenu de toutes lists/collections en une seule nouvelle
               .collect(groupingBy(personEntity -> personEntity.getAddress()));

       Map<String, List<PersonInfoModel>> listFamille = new HashMap<>();

           for(String address : listPersonEntity.keySet()) {
               List<PersonEntity> listPersonByAddress = listPersonEntity.get(address);
               List<PersonInfoModel> listPersonInfoModelByAddress = listPersonByAddress.stream().map(personEntity -> {

                   // On récupère le LastName et le FirstName de MRE
                   MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                           .findByLastNameAndFirstName(personEntity
                                   .getLastName(), personEntity
                                   .getFirstName());

                   // On récupère age, medications et allergies de MRE
                   long resultAge = age.getAge(medicalRecordEntity.getBirthdate());
                   
                   return personMapper.mapToPersonInfoModel
                           (resultAge,personEntity, medicalRecordEntity);
               }).collect(toList());
            listFamille.put(address, listPersonInfoModelByAddress);
           }

           floodModel.setListFamille(listFamille);
       return floodModel;
   }


    /**
     *   Retourne le nom, l'adresse, l'âge, l'email et les antécédents médicaux de chaque habitant.
     *   Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
     *
     */
    // URL n° 6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @Override
    public List<PersonInfoModel> getPersonInfo(String lastName, String firstName) {

        log.info("LastName {},  FirstName {}",lastName, firstName );
        List<PersonEntity> listPerson = personRepository.getPersonList();

         log.info(listPerson.toString() );
        // Récupère la liste des personnes ayant le même nom
        List<PersonModel> personWithSameName = new ArrayList<>();

        for (PersonEntity personEntity : listPerson) {
            if (personEntity.getLastName()
                    .equals(lastName)) {
                personWithSameName.add(personMapper.mapToPersonModel(personEntity));
            } 
        }

        if(!personWithSameName.stream().anyMatch(personModel ->
                personModel.getLastName().equals(lastName))) {
            return null;
        }
        // Au moins une personne portant le nom et le prénom demandé
        if(!personWithSameName.stream().anyMatch(personModel ->
                (personModel.getLastName().equals(lastName)
                && personModel.getFirstName().equals(firstName)))) {
            return null;
        }

        // On souhaite récupérer les paramètres de MREntity
        List<PersonInfoModel> listPersonInfoModel = new ArrayList<>();

        // Récupère une liste de à partir des données de la classe MR
        for (PersonModel personModel : personWithSameName)   {

            MedicalRecordEntity result = medicalRecordRepository
                    .findByLastName(personModel.getLastName());

            long resultAge = age.getAge(result.getBirthdate());
            
            listPersonInfoModel.add(personMapper.mapToPersonInfoModel
                    (personModel, resultAge, result));
        }
        return listPersonInfoModel;

    }


    /**
     *   Renvoie les adresses email de tous les habitants de la ville 
     *
     */
    // URL n° 7 :  http://localhost:8080/communityEmail?city=<city>
    @Override
    public List<String> getCommunityEmail(String city) {

        List<PersonEntity> listPerson = personRepository.getPersonList();
        List<String> emails = new ArrayList<>();

        listPerson
                .stream()
                .filter(personEntity -> personEntity.getCity().equalsIgnoreCase(city))
                .forEach(personEntity -> emails.add(personEntity.getEmail()));
        return emails;
    }

}



