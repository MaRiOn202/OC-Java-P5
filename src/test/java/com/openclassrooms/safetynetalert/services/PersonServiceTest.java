package com.openclassrooms.safetynetalert.services;


import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.mapper.PersonMapper;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.impl.PersonServiceImpl;
import com.openclassrooms.safetynetalert.utils.AgeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks 
    private PersonServiceImpl personServiceImpl;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;;

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AgeUtils age;

    @Mock
    private PersonMapper personMapper;

    private List<PersonEntity> listPerson = new ArrayList<>();
    private List<FireStationEntity> listFireStation = new ArrayList<>();
    private List<MedicalRecordEntity> listMedicalRecord =new ArrayList<>();
    private PersonModel personModelActual = new PersonModel();
    private PersonModel personExpected = new PersonModel();
    private static final PersonEntity person1 = new PersonEntity();

    Logger log = LoggerFactory.getLogger(PersonService.class);

    @BeforeEach
    public void init() {
        //List<PersonEntity> listPerson = new ArrayList<>();
        
        // Création de deux personnes
        PersonEntity person1 = new PersonEntity();
        PersonEntity person2 = new PersonEntity();

        person1.setLastName("lastName1");
        person1.setFirstName("firstName1");
        person1.setCity("Culver");
        person1.setZip("zip1");
        person1.setPhone("phone1");
        person1.setEmail("email1@safety.com");
        person1.setAddress("address1");

        person2.setLastName("lastName2");
        person2.setFirstName("firstName2");
        person2.setCity("Culver");
        person2.setZip("zip2");
        person2.setPhone("phone2");
        person2.setEmail("email2@safety.com");
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

        // Création d'une PersonModel
        personExpected = new PersonModel();
        personExpected.setLastName("lastName1");
        personExpected.setFirstName("firstName1");
        personExpected.setAddress("Culver");
        personExpected.setCity("city1");
        personExpected.setZip("zip");
        personExpected.setEmail("email1@safety.com");
        personExpected.setPhone("phone1");

        personModelActual = new PersonModel();
        personModelActual.setLastName("lastName1");
        personModelActual.setFirstName("firstName1");
        personModelActual.setAddress("Culver");
        personModelActual.setCity("city1");
        personModelActual.setZip("zip");
        personModelActual.setEmail("email1@safety.com");
        personModelActual.setPhone("phone1");
        
    }

     @Test
     void testAddPersonShouldReturnPersonModel() {
         PersonEntity personEntity = new PersonEntity();
         personEntity.setLastName("lastName1");
         personEntity.setFirstName("firstName1");
         personEntity.setAddress("Culver");
         personEntity.setCity("Culver");
         personEntity.setZip("zip1");
         personEntity.setPhone("phone1");
         personEntity.setEmail("email1@safety.com");

         PersonModel personModel = new PersonModel();
         personModel.setLastName("lastName1");
         personModel.setFirstName("firstName1");
         personModel.setAddress("Culver");
         personModel.setCity("city1");
         personModel.setZip("zip");
         personModel.setEmail("email1@safety.com");
         personModel.setPhone("phone1");

         when(personMapper.mapToPersonEntity(any(PersonModel.class))).thenReturn(personEntity);
         when(personMapper.mapToPersonModel(any(PersonEntity.class))).thenReturn(personModel);
         when(personRepository.addPerson(personEntity)).thenReturn(personEntity);

        assertEquals(personExpected, personServiceImpl.addPerson(personModelActual));

     }

    @Test
    void testDeletePerson() {
        String lastName = "lastName2";
        String firstName = "firstName2";
        when(personRepository.deletePerson(lastName, firstName )).thenReturn(true);
        Boolean result = personServiceImpl.deletePerson(lastName, firstName);

        assertEquals(true, result);
        verify(personRepository,times(1)).deletePerson(lastName, firstName);
    }


    @Test
    void testGetCommunityEmail() {
        //Attention : pas de variable de classe
        //List<PersonEntity> listPerson = new ArrayList<>();

/*        // Création de deux personnes
        PersonEntity person1 = new PersonEntity();
        PersonEntity person2 = new PersonEntity();

        person1.setLastName("lastName1");
        person1.setFirstName("firstName1");
        person1.setCity("Culver");
        person1.setZip("zip1");
        person1.setPhone("phone1");
        person1.setEmail("email1@safety.com");
        person1.setAddress("address1");

        person2.setLastName("lastName2");
        person2.setFirstName("firstName2");
        person2.setCity("Culver");
        person2.setZip("zip2");
        person2.setPhone("phone2");
        person2.setEmail("email2@safety.com");
        person2.setAddress("address2");

        listPerson.add(person1);
        listPerson.add(person2);*/
       
        when(personRepository.getPersonList()).thenReturn(listPerson);
        List<String> emailList = personServiceImpl.getCommunityEmail("Culver");

        assertEquals(2, emailList.size());
        assertEquals("email1@safety.com", emailList.get(0));
        verify(personRepository, times(1)).getPersonList();
    }



}
