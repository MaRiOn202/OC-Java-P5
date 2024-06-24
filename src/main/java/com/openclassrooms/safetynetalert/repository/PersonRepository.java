package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private List<PersonEntity> listPerson = new ArrayList<>();

    public PersonRepository( SerializationDriver serializationDriver) {
        this.serializationDriver = serializationDriver;
        this.listPerson = getPersonList();
    }

    public final SerializationDriver serializationDriver;

    public List<PersonEntity> getPersonList() {

        return serializationDriver.safetyAlert.getPersons();
    }

    public PersonEntity addPerson(PersonEntity personEntity) {

        this.listPerson.add(personEntity);
        return personEntity;
    }
    

    public PersonEntity findByLastNameAndFirstName(String lastName, String firstName) {
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            if (person.getLastName().equals(lastName) &&
                    person.getFirstName().equals(firstName))
                return person;
            }
        return null;     //optional.empty
    }

    
    public List<PersonEntity> findByAddress(String address) {
        List<PersonEntity> personResult = new ArrayList<>();
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            if (person.getAddress().equals(address))
                personResult.add(person);
            }
            return personResult;
        }


    public Boolean deletePerson(String lastName, String firstName) {
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            if (person.getLastName().equals(lastName) &&
                    person.getFirstName().equals(firstName))
                return listPerson.remove(person);    //true
        }
        return false;
    }

/*    public List<Person> getAllPerson() {
        return this.listPerson;
    }*/

}
/*    public Person findAll(String firstName, String lastName, String address, String city,
                          String zip, String phone, String email) {
        for(Person person : listPerson) {
           if(person.getLastName().equals(lastName)) 
               return person;
           }
           return new Person();
           // à voir recherche avec d'autre param précis cf attendu
        }*/






