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
        PersonEntity personEntity = new PersonEntity();
         personEntity = personMapper.mapToPersonEntity(personM);
    /*    PersonEntity personEntity = new PersonEntity();
        personEntity.setLastName(personM.getLastName());
        personEntity.setFirstName(personM.getFirstName());
        personEntity.setAddress(personM.getAddress());
        personEntity.setCity(personM.getCity());
        personEntity.setZip(personM.getZip());
        personEntity.setPhone(personM.getPhone());
        personEntity.setEmail(personM.getEmail());*/                //   model en entity

        PersonEntity pe = personRepository.addPerson(personEntity);

        PersonModel personModel = personMapper.mapToPersonModel(pe);    //  entity en model
/*        personModel.setLastName(pe.getLastName());
        personModel.setFirstName(pe.getFirstName());
        personModel.setAddress(pe.getAddress());
        personModel.setCity(pe.getCity());
        personModel.setZip(pe.getZip());
        personModel.setEmail(pe.getEmail());*/

        log.info("New person saved !");                                                          //Ok
        return personModel;
    }

    @Override
    public PersonModel updatePerson(PersonModel personModel) {
        PersonEntity personUpdate =
                personRepository.findByLastNameAndFirstName(personModel.getLastName(), personModel.getFirstName());
        personUpdate.setAddress(personModel.getAddress());
        personUpdate.setCity(personModel.getCity());
        personUpdate.setPhone(personModel.getPhone());
        personUpdate.setEmail(personModel.getEmail());
        personUpdate.setZip(personModel.getZip());               // Model en entity
        //A VERIFIER
        //personMapper.mapToPersonEntity(personModel);

        //sauvegarder
        personUpdate = personRepository.updatePerson(personUpdate);
        PersonModel personReturn = personMapper.mapToPersonModel(personUpdate);
        return personReturn;
    }

    @Override
    public Boolean deletePerson(String lastName, String firstName) {
         final boolean personDeleted = personRepository.deletePerson(lastName, firstName);
         if(personDeleted) {
             //slf4j log à utiliser // log.info
             log.info("La personne " + lastName + " " + firstName + " a bien été supprimée");
         } else {
             log.info("La personne " + lastName + " " + firstName + " n'a pas été supprimée");
         }
        return personDeleted;
    }

    
    
    // URL n°2 : http://localhost:8080/childAlert?address=<address>

    @Override
    public List<ChildModel> getChildAlert(String address) {

        List<PersonEntity> listPerson = personRepository.findByAddress(address);
        List<MedicalRecordEntity> listMedicalRecord = medicalRecordRepository.getMedicalRecordList();

        // récupère une liste de MedicalRecord à partir des données de la classe MR
        // Celle-ci permet de distinguer enfants/adultes
        for (PersonEntity personEntity : listPerson)   {
            MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                    .findByLastNameAndFirstName(personEntity.getLastName(), personEntity.getFirstName());
            listMedicalRecord.add(medicalRecordEntity);
        }

        // Récupère seulement la liste des enfants
        List<MedicalRecordEntity> medicalRecordChild = listMedicalRecord
                .stream()
                .filter(medicalRecord -> age.getMinor(medicalRecord.getBirthdate()))
                .toList();     //liste des mineurs

        // Construit un nouvel objet : ChildModel
        List<ChildModel> listChildModel = new ArrayList<>();

        // Celui-ci possède plusieurs paramètres donnés décrit dans ChildModel
        for (MedicalRecordEntity medicalRecordEntity : medicalRecordChild) {
            ChildModel childModel = new ChildModel();
            childModel.setLastName(medicalRecordEntity.getLastName());
            childModel.setFirstName(medicalRecordEntity.getFirstName());
            childModel.setAge(age.getAge(medicalRecordEntity.getBirthdate()));

            // On souhaite également retourner la liste des membres de la famille
            // dont les attributs sont présents dans PersonModel
            List<PersonModel> listPersonFamily = listPerson
                    .stream()
                    .filter(person -> person.getLastName().equals(childModel.getLastName()))
                    .map(personEntity -> new PersonModel(
                            personEntity.getFirstName(),
                            personEntity.getLastName(),
                            personEntity.getPhone(),
                            personEntity.getAddress(),
                            personEntity.getCity(),
                            personEntity.getZip(),
                            personEntity.getEmail()))     //changement de type personEntity en PersonModel
                    .toList();

            childModel.setMembersFamily(listPersonFamily);                       //1 childmodel 
            listChildModel.add(childModel);

        }
        return listChildModel;          // renvoie uniquement liste des enfants plus autres membres
           // renvoie une liste vide s'il n'y a pas d'enfants ?
    }



    // URL n°5 :   http://localhost:8080/flood/stations?stations=<a list of station_numbers>
   @Override
   public FloodModel getFlood(List<String> stationNumber) {

        List<PersonEntity> listPerson = personRepository.getPersonList();
       List<FireStationEntity> listFireStationModel  = stationNumber
               .stream()
               .map(station -> fireStationRepository.findByStation(station))
               .flatMap(fireStationEntities -> fireStationEntities.stream()).toList();
       
        //List<MedicalRecordEntity> listMedicalRecordEntity = medicalRecordService.getMedicalRecordList();
        List<String> addressListStation = new ArrayList<>();

        // Etape 1 : Récupérer la liste des adresses des FS
        for (FireStationEntity fireStationEntity : listFireStationModel) {
            if (stationNumber.contains(fireStationEntity.getStation()))  {
                addressListStation.add(fireStationEntity.getAddress());
            }
        }
        
       // Etape 2 chaque adresse récupère liste de personnes / regroupe par famille par le LastName
       // Etape 3 créer famille correspondante et les ajouter dans une liste
       FloodModel floodModel = new FloodModel();

       // très obscur
       Map<String, List<PersonEntity>> listPersonEntity = addressListStation
               .stream()
               .map(address ->  {
           List<PersonEntity> listPersonByAddress = listPerson
                   .stream()
                   .filter(personModel
                          -> personModel.getAddress().equals(address)).collect(toList());
           return listPersonByAddress;
       }).flatMap(Collection::stream)     //récupère le contenu de toutes lists/collections en une seule nouvelle
               .collect(groupingBy(personEntity -> personEntity.getAddress()));

       Map<String, List<PersonInfoModel>> listFamille = new HashMap<>();

           for(String address : listPersonEntity.keySet()) {
               List<PersonEntity> listPersonByAddress = listPersonEntity.get(address);
               List<PersonInfoModel> listPersonInfoModelByAddress = listPersonByAddress.stream().map(personEntity -> {

                   //ex ici On récupère le LastName et le FirstName de MRE
                   MedicalRecordEntity medicalRecordEntity = medicalRecordRepository
                           .findByLastNameAndFirstName(personEntity
                                   .getLastName(), personEntity
                                   .getFirstName());

                   // On récupère age, medications et allergies de MRE
                   long resultAge = age.getAge(medicalRecordEntity.getBirthdate());
                   List<String> medications = medicalRecordEntity.getMedications();
                   List<String> allergies = medicalRecordEntity.getAllergies();

                   // A DEMANDER       !!!!
                   return personMapper.mapToPersonInfoModel(personEntity, medicalRecordEntity);
  /*                 return new PersonInfoModel(
                           personEntity.getFirstName(),
                           personEntity.getLastName(),
                           personEntity.getAddress(),
                           resultAge,
                           personEntity.getEmail(),
                           medications,
                           allergies);*/

               }).collect(toList());
            listFamille.put(address, listPersonInfoModelByAddress);
           }

           floodModel.setListFamille(listFamille);
       return floodModel;
   }
   


    // URL n° 6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @Override
    public List<PersonInfoModel> getPersonInfo(String firstName, String lastName) {

        List<PersonEntity> listPerson = personRepository.getPersonList();
        List<MedicalRecordEntity> listMedicalRecordEntities = medicalRecordRepository.getMedicalRecordList();

        // Récupère la liste des personnes ayant le même nom
        List<PersonModel> personWithSameName = new ArrayList<>();

        for (PersonEntity personEntity : listPerson) {
            if (personEntity.getLastName()
                    .equals(lastName)) {
                personWithSameName.add(personMapper.mapToPersonModel(personEntity));
            }
        }
        
        // récupère une liste de à partir des données de la classe MR
        for (PersonModel personModel : personWithSameName)   {
            MedicalRecordEntity result = medicalRecordRepository
                    .findByLastNameAndFirstName(personModel.getLastName(), personModel
                            .getFirstName());
            listMedicalRecordEntities.add(result);
        }

        // On souhaite récupérer les paramètres de MREntity
        List<PersonInfoModel> listPersonInfoModel = new ArrayList<>();

        for (MedicalRecordEntity medicalRecordEntity : listMedicalRecordEntities) {
            PersonInfoModel personInfoModel = new PersonInfoModel();
            personInfoModel.setFirstName(medicalRecordEntity.getFirstName());
            personInfoModel.setLastName(medicalRecordEntity.getLastName());
            //PersonEntity personEntity = getPersonEntityWithMedicalRecord(medicalRecordEntity, personWithSameName);
            PersonEntity personEntity = personRepository.findByLastNameAndFirstName(lastName, firstName);
            personInfoModel.setAddress(personEntity.getAddress());
            personInfoModel.setEmail(personEntity.getEmail());
            personInfoModel.setAge(age.getAge(medicalRecordEntity.getBirthdate()));
            personInfoModel.setMedications(medicalRecordEntity.getMedications());
            personInfoModel.setAllergies(medicalRecordEntity.getAllergies());
            listPersonInfoModel.add(personInfoModel);
        }
        return listPersonInfoModel;

    }

    //    cf. l. 287
/*    @Override
    public PersonEntity getPersonEntityWithMedicalRecord(MedicalRecordEntity medicalRecordEntity,
                                                         @NotNull List<PersonEntity> listPersonEntity) {
        //if isPresent return resultat sinon null
        return listPersonEntity
                .stream()
                .filter(person -> person.getLastName().equals(medicalRecordEntity.getLastName()) &&
                        person.getFirstName().equals(medicalRecordEntity.getFirstName()))
                .findFirst()
                .get();              //retourne le premier élément

    }*/

    
    // URL n° 7 :  http://localhost:8080/communityEmail?city=<city>
    @Override
    public List<String> getCommunityEmail(String city) {

        List<PersonEntity> listPerson = personRepository.getPersonList();
        List<String> emails = new ArrayList<>();

        listPerson
                .stream()
                .filter(personEntity -> personEntity.getCity().equalsIgnoreCase(city))
                .forEach(personEntity -> emails.add(personEntity.getEmail()));

/*        for (PersonEntity personEntity : listPerson) {
            if (personEntity.getCity().equals(city)) {
                emails.add(personEntity.getEmail());
            }
        }*/
        return emails;
    }

}



