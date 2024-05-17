package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.Person;

import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

   // @Autowired
    PersonRepository personRepository;

    Logger log = LoggerFactory.getLogger(PersonRepository.class);
    private Person person;

    @BeforeEach
    void init() throws IOException {
        SerializationDriver serializationDriver = new SerializationDriver();
        personRepository = new PersonRepository(serializationDriver);
    }

    @Test
    void testPersonRepositoryAddPerson() {

        Person person = new Person();
        person.setLastName("toto");
        person.setFirstName("tata");
        personRepository.addPerson(person);
        List<Person> listPerson = personRepository.getPersonList();
        log.info("Résultat : {}", listPerson);

        assertNotNull(listPerson);
        assertTrue(listPerson.stream().anyMatch(person1 ->
                person1.getLastName().equalsIgnoreCase("toto")), "Le test a échoué");
    }

    @Test
    void testPersonRepositoryRemovePerson() {

        Boolean result = personRepository.removePerson("Boyd",
                "John");
        List<Person> listPerson = personRepository.getPersonList();
        log.info("Résultat : {} {}", result, listPerson);

        assertTrue(result, "Le test a échoué");
        assertThat(listPerson.size()).isEqualTo(22);
        
    }

    @Test
    void testPersonRepositoryFindByLastNameAndFirstName() {
        
        person = personRepository.findByLastNameAndFirstName("Boyd","John");
        log.info("Résultat : {}", person);

        assertEquals("Boyd", person.getLastName());
        assertEquals("John", person.getFirstName());

    }

    @Test
    void testPersonRepositoryFindByAddress() {

        List<Person> result = personRepository.findByAddress("1509 Culver St");
        List<Person> listPerson = personRepository.findByAddress("1509 Culver St");
        log.info("Résultat : {} {}", listPerson, result);

        assertEquals("1509 Culver St", listPerson.get(0).getAddress());
        assertThat(listPerson.size()).isEqualTo(5);
    }

}
