package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.ChildModel;
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

    // Laisser comme cela
    public PersonEntity updatePerson(PersonEntity personUpdate) {

        return personUpdate;
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

    public PersonEntity findByLastNameAndFirstName(String lastName, String firstName) {
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            if (person.getLastName().equals(lastName) &&
                    person.getFirstName().equals(firstName))
                return person;
            }
        return null;     //optional.empty
    }

    public List<PersonEntity> findByLastNameAndFirstNameList(String lastName, String firstName) {
        List<PersonEntity> personEntity = new ArrayList<>();
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            if (person.getLastName().equals(lastName) &&
                    person.getFirstName().equals(firstName))
                return personEntity;
        }
        return null;     //optional.empty
    }

    
    public List<PersonEntity> findByAddress(String address) {
        List<PersonEntity> personEntity = new ArrayList<>();
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            if (person.getAddress().equals(address))
                personEntity.add(person);
            }
            return personEntity;
        }





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






