package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.model.FloodModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;


public interface PersonService {

    //List<PersonModel> getPersonList();
    
    PersonModel addPerson(PersonModel personModel);

    PersonModel updatePerson(PersonModel personModel);

//    PersonModel findByLastNameAndFirstName(String lastName, String firstName);

 //   List<PersonModel> findByAddress(String address);

    Boolean deletePerson(String lastName, String firstName);

    //URL n°2 :
    List<ChildModel> getChildAlert(String address);

    // URL n°5
    FloodModel getFlood(List<String> stations);

    // URL n°6
    List<PersonInfoModel> getPersonInfo(String firstName, String lastName);

    // Spécificité de cette méthode ?
    //PersonEntity getPersonEntityWithMedicalRecord(MedicalRecordEntity medicalRecordEntity, @NotNull List<PersonEntity> listPersonEntity);

    //URL n°7 :
    List<String> getCommunityEmail(String city);


}



    







    




