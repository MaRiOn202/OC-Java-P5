package com.openclassrooms.safetynetalert.services;


import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.model.FloodModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;

import java.util.List;



public interface PersonService {
    
    PersonModel addPerson(PersonModel personModel);

    PersonModel updatePerson(PersonModel personModel);

    Boolean deletePerson(String lastName, String firstName);

    //URL n°2 :
    List<ChildModel> getChildAlert(String address);

    // URL n°5
    FloodModel getFlood(List<String> stations);

    // URL n°6
    List<PersonInfoModel> getPersonInfo(String firstName, String lastName);

    //URL n°7 :
    List<String> getCommunityEmail(String city);
    
}



    







    




