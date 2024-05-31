package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.ChildModel;

import java.util.List;


public interface PersonService {


    public void addPerson(PersonEntity personEntity);

    public PersonEntity findByLastNameAndFirstName(String lastName, String firstName);

    public List<PersonEntity> findByAddress(String address);

    public Boolean removePerson(String lastName, String firstName);


    public List<ChildModel> getChildAlert(String address);

    public List<PersonEntity> getPersonList();
    
    public List<String> getCommunityEmail(String city);
}



    







    




