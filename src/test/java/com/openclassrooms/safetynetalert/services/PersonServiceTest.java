package com.openclassrooms.safetynetalert.services;


import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.impl.PersonServiceImpl;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    //@InjectMocks
    @InjectMocks 
    private PersonServiceImpl personServiceImpl;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PersonRepository personRepository;

    Logger log = LoggerFactory.getLogger(PersonService.class);

/*    @BeforeAll
    public static void setUp() {
        List<PersonEntity> listPerson = new ArrayList<>();
        List<FireStationEntity> listFireStation = new ArrayList<>();
        List<MedicalRecordEntity> listMedicalRecord =new ArrayList<>();

        // Création de deux personnes
        PersonEntity person1 = new PersonEntity();
        PersonEntity person2 = new PersonEntity();

        person1.setLastName("lastName1");
        person1.setFirstName("firstName1");
        person1.setCity("city1");
        person1.setZip("zip1");
        person1.setPhone("phone1");
        person1.setEmail("email1");
        person1.setAddress("address1");

        person2.setLastName("lastName2");
        person2.setFirstName("firstName2");
        person2.setCity("city2");
        person2.setZip("zip2");
        person2.setPhone("phone2");
        person2.setEmail("email2");
        person2.setAddress("address2");

        listPerson.add(person1);
        listPerson.add(person2);

        // Création de deux fireStation
        FireStationEntity fireStation1 = new FireStationEntity();
        FireStationEntity fireStation2 = new FireStationEntity();

        fireStation1.setStation("station1");
        fireStation1.setAddress("address1");

        fireStation2.setStation("station2");
        fireStation2.setAddress("address2");

        listFireStation.add(fireStation1);
        listFireStation.add(fireStation2);

        // Création de deux medicalRecord
        MedicalRecordEntity medicalRecord1 = new MedicalRecordEntity();
        MedicalRecordEntity medicalRecord2 = new MedicalRecordEntity();
        List<String> medicationsAndAllergies = new ArrayList<>();

        medicalRecord1.setLastName("mrLastName1");
        medicalRecord1.setFirstName("mrFirstName1");
        medicalRecord1.setBirthdate("11/03/2010"); //min
        medicalRecord1.setMedications(medicationsAndAllergies);
        medicalRecord1.setAllergies(medicationsAndAllergies);

        medicalRecord2.setLastName("mrLastName2");
        medicalRecord2.setFirstName("mrFirstName2");
        medicalRecord2.setBirthdate("21/10/1990"); //maj
        medicalRecord2.setMedications(medicationsAndAllergies);
        medicalRecord2.setAllergies(medicationsAndAllergies);

        listMedicalRecord.add(medicalRecord1);
        listMedicalRecord.add(medicalRecord2);
    }*/


/*    @Test
    void testGetPersonList() {
        List<PersonEntity> listPerson = new ArrayList<>();
        // Création de deux personnes
        PersonEntity person1 = new PersonEntity();
        PersonEntity person2 = new PersonEntity();

        person1.setLastName("lastName1");
        person1.setFirstName("firstName1");
        person1.setCity("city1");
        person1.setZip("zip1");
        person1.setPhone("phone1");
        person1.setEmail("email1");
        person1.setAddress("address1");

        person2.setLastName("lastName2");
        person2.setFirstName("firstName2");
        person2.setCity("city2");
        person2.setZip("zip2");
        person2.setPhone("phone2");
        person2.setEmail("email2");
        person2.setAddress("address2");

        listPerson.add(person1);
        listPerson.add(person2);
        //listPerson = personServiceImpl.getPersonList();
        when(personServiceImpl.getPersonList()).thenReturn(listPerson);
        
        log.info("Résultat : {}", listPerson);
        assertEquals(listPerson, personServiceImpl.getPersonList());
    }*/


/*   @Test
   void testFindByLastNameAndFirstName() {

       PersonEntity person3 = new PersonEntity();
       person3.setLastName("person3LastName");
       person3.setFirstName("person3FirstName");

       when(personRepository.findByLastNameAndFirstName(person3.getLastName(),
               person3.getFirstName())).thenReturn(new PersonEntity());
       //when(personRepository.getAllPerson()).thenReturn();
       personServiceImpl.findByLastNameAndFirstName(person3.getLastName(), person3.getFirstName());

        log.info("Résultat : {}", person3);

        assertEquals(person3.getLastName(), "person3LastName");
        assertEquals(person3.getFirstName(), "person3FirstName");

   }*/

/*    @Test
    void testGetCommunityEmail() {

        List<String> emailList = new ArrayList<>();
        when(personServiceImpl.getPersonList()).thenReturn();

    }*/


}
