package com.openclassrooms.safetynetalert.services.impl;


import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.FireStationService;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import com.openclassrooms.safetynetalert.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonService personService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private PersonRepository personRepository;



    private List<PersonEntity> listPerson = new ArrayList<>();


    @Override
    public void addPerson(PersonEntity person) {
        
    }

    @Override
    public PersonEntity findByLastNameAndFirstName(String lastName, String firstName) {
        return personRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    @Override
    public List<PersonEntity> findByAddress(String address) {
        return personRepository.findByAddress(address);
    }

    @Override
    public Boolean removePerson(String lastName, String firstName) {
        return personRepository.removePerson(lastName, firstName);
    }

    
    // URL n°2 : http://localhost:8080/childAlert?address=<address>

    @Override
    public List<ChildModel> getChildAlert(String address) {

        List<PersonEntity> listPerson = personService.findByAddress(address);
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
                .filter(medicalRecord -> this.getMinor(medicalRecord.getBirthdate()))
                .toList();     //liste des mineurs

        // Construit un nouvel objet : ChildModel
        List<ChildModel> listChildModel = new ArrayList<>();

        // Celui-ci possède plusieurs paramètres donnés décrit dans ChildModel
        for (MedicalRecordEntity medicalRecordEntity : medicalRecordChild) {
            ChildModel childModel = new ChildModel();
            childModel.setLastName(medicalRecordEntity.getLastName());
            childModel.setFirstName(medicalRecordEntity.getFirstName());
            childModel.setAge(this.getAge(medicalRecordEntity.getBirthdate()));

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

    @Override
    public List<PersonEntity> getPersonList() {
        return personRepository.getPersonList();
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

    public long getAge(String birthdate) {

        LocalDate currentTime = LocalDate.now();
        LocalDate birthdayTime = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(birthdate) );
        Duration result =  Duration.between(currentTime,birthdayTime);

        long age = result.toDays()/365;
          
        return age;
    }


     // URL n° 6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    public List<PersonInfoModel> getPersonInfo(String firstName, String lastName) {

        List<PersonEntity> listPerson = personService.getPersonList();
        List<MedicalRecordEntity> listMedicalRecordEntities = medicalRecordService.getMedicalRecordList();

        List<PersonEntity> personWithSameName = new ArrayList<>();
        for (PersonEntity personEntity : listPerson) {
             if (personEntity.getFirstName().equals(firstName) && personEntity.getLastName().equals(lastName)) {
                 personWithSameName.add(personEntity);
             }
        }

        // récupère une liste de  à partir des données de la classe MR
        for (PersonEntity personEntity : personWithSameName)   {
            MedicalRecordEntity result = medicalRecordService
                    .findByLastNameAndFirstName(personEntity.getLastName(), personEntity.getFirstName());
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
             personInfoModel.setAge(getAge(medicalRecordEntity.getBirthdate()));
             personInfoModel.setMedications(medicalRecordEntity.getMedications());
             personInfoModel.setAllergies(medicalRecordEntity.getAllergies());
             listPersonInfoModel.add(personInfoModel);
        }

        return listPersonInfoModel;
    }

    public PersonEntity getPersonEntityWithMedicalRecord(MedicalRecordEntity medicalRecordEntity, List<PersonEntity> listPersonEntity) {
        //if isPresent return resultat sinon null
        return listPersonEntity
                .stream()
                .filter(person -> person.getLastName().equals(medicalRecordEntity.getLastName()) &&
                        person.getFirstName().equals(medicalRecordEntity.getFirstName()))
                .findFirst()
                .get();              //retourne le premier élément

    }

    // URL n° 7 :  http://localhost:8080/communityEmail?city=<city>
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



