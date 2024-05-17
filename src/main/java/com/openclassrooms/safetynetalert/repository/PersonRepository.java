package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private List<Person> listPerson = new ArrayList<>();

    public PersonRepository( SerializationDriver serializationDriver) {
        this.serializationDriver = serializationDriver;
        this.listPerson = getPersonList();
    }

    public final SerializationDriver serializationDriver;

    public List<Person> getPersonList() {
        return serializationDriver.safetyAlert.getPersons();
    }

    public void addPerson(Person person) {

        this.listPerson.add(person);
    }

    public Person findByLastNameAndFirstName(String lastName, String firstName) {
        listPerson = getPersonList();
        for (Person person : listPerson) {
            if (person.getLastName().equals(lastName) &&
                    person.getFirstName().equals(firstName))
                return person;
            }
        return null;     //optional.empty
    }

    public List<Person> findByAddress(String address) {
        List<Person> personResult = new ArrayList<>();
        listPerson = getPersonList();
        for (Person person : listPerson) {
            if (person.getAddress().equals(address))
                personResult.add(person);
            }
            return personResult;
        }


    public Boolean removePerson(String lastName, String firstName) {
        listPerson = getPersonList();
        for (Person person : listPerson) {
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






