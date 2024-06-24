package com.openclassrooms.safetynetalert.services.impl;


import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.FireStationService;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import com.openclassrooms.safetynetalert.services.PersonService;
import com.openclassrooms.safetynetalert.utils.AgeUtils;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private AgeUtils age;

    @Autowired
    private PersonRepository personRepository;
   


    @Override
    public void addPerson(PersonModel personM) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setLastName(personM.getLastName());
        personEntity.setFirstName(personM.getFirstName());
        personEntity.setAddress(personM.getAddress());
        personEntity.setCity(personM.getCity());
        personEntity.setZip(personM.getZip());
        personEntity.setPhone(personM.getPhone());
        personEntity.setEmail(personM.getEmail());                //   model en entity

        PersonEntity pe = personRepository.addPerson(personEntity);
        PersonModel personModel= new PersonModel();               //  entity en model
        personModel.setLastName(pe.getLastName());
        personModel.setFirstName(pe.getFirstName());
        personModel.setAddress(pe.getAddress());
        personModel.setCity(pe.getCity());
        personModel.setZip(pe.getZip());
        personModel.setEmail(pe.getEmail());
                                                                  //Ok
    }

    @Override
    public List<PersonEntity> getPersonList() {
        return this.personService.getPersonList();
    }

    @Override
    public PersonEntity findByLastNameAndFirstName(String lastName, String firstName) {
         return  personService.findByLastNameAndFirstName(lastName, firstName);

        //return this.findByLastNameAndFirstName(lastName, firstName);
    }

    @Override
    public List<PersonEntity> findByAddress(String address) {

        return personService.findByAddress(address);
    }

    @Override
    public Boolean deletePerson(String lastName, String firstName) {

        return this.deletePerson(lastName, firstName);
    }

    
    // URL n°2 : http://localhost:8080/childAlert?address=<address>

    @Override
    public List<ChildModel> getChildAlert(String address) {

        List<PersonEntity> listPerson = this.findByAddress(address);
        List<MedicalRecordEntity> listMedicalRecord = medicalRecordService.getMedicalRecordList();

        // récupère une liste de MedicalRecord à partir des données de la classe MR
        // Celle-ci permet de distinguer enfants/adultes
        for (PersonEntity person : listPerson)   {
            MedicalRecordEntity result = medicalRecordService
                    .findByLastNameAndFirstName(person.getLastName(), person.getFirstName());
            listMedicalRecord.add(result);
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
                            personEntity.getAddress(),
                            personEntity.getCity(),
                            personEntity.getPhone()     //changement de type personEntity en PersonModel
                    ))
                    .toList();

            childModel.setMembersFamily(listPersonFamily);                       //1 childmodel 
            listChildModel.add(childModel);

        }
        return listChildModel;          // renvoie uniquement liste des enfants plus autres membres
           // renvoie une liste vide s'il n'y a pas d'enfants ?
    }



/*    @Override
    public List<PersonModel> getPersonList() {
        return personService.getPersonList();
    }*/


    

    // URL n°5 :   http://localhost:8080/flood/stations?stations=<a list of station_numbers>
   @Override
   public FloodModel getFlood(List<String> stationNumber) {

        List<PersonEntity> listPerson = personService.getPersonList();
        List<FireStationEntity> listFireStation = fireStationService.findByStation(stationNumber.toString());
        List<MedicalRecordEntity> listMedicalRecordEntity = medicalRecordService.getMedicalRecordList();
        List<String> addressListStation = new ArrayList<>();

        Map<String, List<FloodModel>> listFloodModel = new HashMap<String, List<FloodModel>>();

        // Etape 1 : Récupérer la liste des adresses des FS
        for (FireStationEntity fireStationEntity : listFireStation) {
            if (stationNumber.contains(fireStationEntity.getStation()))  {
                addressListStation.add(fireStationEntity.getAddress());
            }
        }
        
       // Etape 2 chaque adresse récupère liste de personnes / regroupe par famille par le LastName
       // Etape 3 créer famille correspondante et les ajouter dans une liste
       List<FamilyWithoutChildModel> familleList = new ArrayList<>();
       FloodModel floodModel = new FloodModel();

       List<PersonEntity> listPersonEntity = addressListStation.stream().map(address ->  {
           List<PersonEntity> listPersonEntity2 = listPerson.stream().filter(personEntity
                          -> personEntity.getAddress().equals(address)).collect(Collectors.toList());
           return listPersonEntity2;
       }).flatMap(Collection::stream).toList();

       Map<String, List<PersonEntity>> listPersonSameLastName = listPersonEntity.stream()
               .collect(groupingBy(personEntity -> personEntity.getLastName()));

           for(String key : listPersonSameLastName.keySet()) {
               List<PersonEntity> personSameName = listPersonSameLastName.get(key);
               List<PersonInfoModel> listPersonInfoModel = personSameName.stream().map(personEntity -> {

                   //ex ici On récupère le LastName et le FirstName de MRE
                   MedicalRecordEntity medicalRecordEntity = medicalRecordService
                           .findByLastNameAndFirstName(personEntity
                                   .getLastName(), personEntity
                                   .getFirstName());

                   // On récupère age, medications et allergies de MRE
                   long resultAge = age.getAge(medicalRecordEntity.getBirthdate());
                   List<String> medications = medicalRecordEntity.getMedications();
                   List<String> allergies = medicalRecordEntity.getAllergies();

                   return new PersonInfoModel(
                           personEntity.getFirstName(),
                           personEntity.getLastName(),
                           personEntity.getAddress(),
                           resultAge,
                           personEntity.getEmail(),
                           medications,
                           allergies);

               }).collect(Collectors.toList());
               FamilyWithoutChildModel familyWithoutChildModel = new FamilyWithoutChildModel(listPersonInfoModel);
               familleList.add(familyWithoutChildModel);
           }
           floodModel.setFamille(familleList);
       return floodModel;
   }


     // URL n° 6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @Override
    public List<PersonInfoModel> getPersonInfo(String firstName, String lastName) {

        List<PersonEntity> listPerson = personService.getPersonList();
        List<MedicalRecordEntity> listMedicalRecordEntities = medicalRecordService
                .getMedicalRecordList();

        // Récupère la liste des personnes ayant le même nom
        List<PersonEntity> personWithSameName = new ArrayList<>();
        for (PersonEntity personEntity : listPerson) {
             if (personEntity.getFirstName().equals(firstName) && personEntity.getLastName()
                     .equals(lastName)) {
                 personWithSameName.add(personEntity);
             }
        }

        // récupère une liste de  à partir des données de la classe MR
        for (PersonEntity personEntity : personWithSameName)   {
            MedicalRecordEntity result = medicalRecordService
                    .findByLastNameAndFirstName(personEntity.getLastName(), personEntity
                            .getFirstName());
            listMedicalRecordEntities.add(result);
        }

        // On souhaite récupérer les paramètres de MREntity
        List<PersonInfoModel> listPersonInfoModel = new ArrayList<>();
        for (MedicalRecordEntity medicalRecordEntity : listMedicalRecordEntities) {
             PersonInfoModel personInfoModel = new PersonInfoModel();
             personInfoModel.setFirstName(medicalRecordEntity.getFirstName());
             personInfoModel.setLastName(medicalRecordEntity.getLastName());
             PersonEntity result = getPersonEntityWithMedicalRecord(medicalRecordEntity, personWithSameName);
             personInfoModel.setAddress(result.getAddress());
             personInfoModel.setEmail(result.getEmail());
             personInfoModel.setAge(age.getAge(medicalRecordEntity.getBirthdate()));
             personInfoModel.setMedications(medicalRecordEntity.getMedications());
             personInfoModel.setAllergies(medicalRecordEntity.getAllergies());
             listPersonInfoModel.add(personInfoModel);
        }
        return listPersonInfoModel;
    }

    // Spécificité de cette méthode ?
    @Override
    public PersonEntity getPersonEntityWithMedicalRecord(MedicalRecordEntity medicalRecordEntity, @NotNull List<PersonEntity> listPersonEntity) {
        //if isPresent return resultat sinon null
        return listPersonEntity
                .stream()
                .filter(person -> person.getLastName().equals(medicalRecordEntity.getLastName()) &&
                        person.getFirstName().equals(medicalRecordEntity.getFirstName()))
                .findFirst()
                .get();              //retourne le premier élément

    }

    
    // URL n° 7 :  http://localhost:8080/communityEmail?city=<city>
    @Override
    public List<String> getCommunityEmail(String city) {

        List<PersonEntity> listPerson = personService.getPersonList();
        List<String> emails = new ArrayList<>();

        for (PersonEntity personEntity : listPerson) {
            if (personEntity.getCity().equals(city)) {
                emails.add(personEntity.getEmail());
            }
        }
        return emails;
    }

}



