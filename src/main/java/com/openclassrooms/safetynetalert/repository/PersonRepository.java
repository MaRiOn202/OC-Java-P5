package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.services.impl.FireStationImpl;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private List<PersonEntity> listPerson = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(PersonRepository.class);

    public PersonRepository(SerializationDriver serializationDriver) {
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
        listPerson = getPersonList();
        PersonEntity personUpdated = new PersonEntity();
        for (PersonEntity personEntity : listPerson) {
            if (personEntity.getLastName().equalsIgnoreCase(personUpdate.getLastName()) &&
                    personEntity.getFirstName().equalsIgnoreCase(personUpdate.getFirstName())) {
                personEntity.setAddress(personUpdate.getAddress());
                personEntity.setCity(personUpdate.getCity());
                personEntity.setZip(personUpdate.getZip());
                personEntity.setPhone(personUpdate.getPhone());
                personEntity.setEmail(personUpdate.getEmail());
                return personUpdate;
            }
        }
        return personUpdated;
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
        log.info("Adresse : {} ", address);
        listPerson = getPersonList();
        for (PersonEntity person : listPerson) {
            log.info("Person : {}", person);
            if (person.getAddress().equals(address)) {
                personEntity.add(person);
            }
        }
        log.info("PersonEntity : {}", personEntity);
        return personEntity;
    }


}










