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

    public List<PersonEntity> getPersonList();
    
    public void addPerson(PersonModel person);

    public PersonEntity findByLastNameAndFirstName(String lastName, String firstName);

    public List<PersonEntity> findByAddress(String address);

    public Boolean deletePerson(String lastName, String firstName);


    public List<ChildModel> getChildAlert(String address);

    public FloodModel getFlood(List<String> stations);

    public List<PersonInfoModel> getPersonInfo(String firstName, String lastName);

    // Spécificité de cette méthode ?
    PersonEntity getPersonEntityWithMedicalRecord(MedicalRecordEntity medicalRecordEntity, @NotNull List<PersonEntity> listPersonEntity);

    public List<String> getCommunityEmail(String city);
}



    







    




