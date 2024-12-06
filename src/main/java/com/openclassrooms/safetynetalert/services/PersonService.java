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
    /**
     *   Renvoie une liste d'enfants habitant à une adresse.
     *   Nom + prénom + âge ainsi que la liste des autres membres du foyer.
     *
     * @param address
     * @return List<ChildModel>
     */
    List<ChildModel> getChildAlert(String address);


    // URL n°5
    /**
     *   Renvoie la liste de tous les foyers desservis par une caserne.
     *   Regroupe les personnes par adresse.
     *
     * @param stations
     * @return FloodModel
     */
    FloodModel getFlood(List<String> stations);


    // URL n°6
    /**
     *   Retourne le nom, l'adresse, l'âge, l'email et les antécédents médicaux de chaque habitant.
     *   Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
     *
     * @param firstName
     * @param lastName
     * @return List<PersonInfoModel>
     */
    List<PersonInfoModel> getPersonInfo(String firstName, String lastName);


    //URL n°7 :
    /**
     *   Renvoie les adresses email de tous les habitants de la ville
     *
     * @param city
     * @return List<String>
     */
    List<String> getCommunityEmail(String city);
    
}



    







    




