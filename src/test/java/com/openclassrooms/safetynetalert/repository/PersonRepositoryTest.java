package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.PersonEntity;

import com.openclassrooms.safetynetalert.exception.FileNotReadException;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {
    
    private PersonRepository personRepository;

    Logger log = LoggerFactory.getLogger(PersonRepository.class);
    private PersonEntity person = new PersonEntity();

    @BeforeEach
    void init() throws FileNotReadException {
        SerializationDriver serializationDriver = new SerializationDriver();
        personRepository = new PersonRepository(serializationDriver);
    }

    @Test
    void testPersonRepositoryAddPersonReturnTrue() {

        person = new PersonEntity();
        person.setLastName("toto");
        person.setFirstName("tata");
        personRepository.addPerson(person);
        List<PersonEntity> listPerson = personRepository.getPersonList();
        log.info("Résultat : {}", listPerson);

        assertNotNull(listPerson);
        assertTrue(listPerson.stream().anyMatch(person1 ->
                person1.getLastName().equalsIgnoreCase("toto")),
                "Le test a échoué");
    }

    @Test
    void testPersonRepositoryUpdatePersonSuccess() {

        List<PersonEntity> listPerson = personRepository.getPersonList();

        log.info("Résultat : {}", listPerson);
        PersonEntity personUpdate =
               new PersonEntity("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "111-111-111", "change@email.com");

       PersonEntity personUpdated = personRepository.updatePerson(personUpdate);
       log.info("PersonUpdated : {}", personUpdated);

       assertNotNull(personUpdated);
       assertEquals("951 LoneTree Rd", personUpdated.getAddress());
    }

    @Test
    void testPersonRepositoryRemovePersonReturnTrue() {

        Boolean result = personRepository.deletePerson("Boyd",
                "John");
        List<PersonEntity> listPerson = personRepository.getPersonList();
        log.info("Résultat : {} {}", result, listPerson);

        assertTrue(result, "Le test a échoué");
        assertThat(listPerson.size()).isEqualTo(22);
        
    }

    @Test
    void testPersonRepositoryFindByLastNameAndFirstNameCasNominal() {
        
        person = personRepository.findByLastNameAndFirstName("Boyd","John");
        log.info("Résultat : {}", person);

        assertEquals("Boyd", person.getLastName());
        assertEquals("John", person.getFirstName());

    }

    @Test
    void testPersonRepositoryFindByAddressCasNominal() {

        List<PersonEntity> result = personRepository.findByAddress("1509 Culver St");
        List<PersonEntity> listPerson = personRepository.findByAddress("1509 Culver St");
        log.info("Résultat : {} {}", listPerson, result);

        assertEquals("1509 Culver St", listPerson.get(0).getAddress());
        assertThat(listPerson.size()).isEqualTo(5);
    }

}
