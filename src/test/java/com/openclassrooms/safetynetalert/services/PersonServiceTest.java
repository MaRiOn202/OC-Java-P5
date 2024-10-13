package com.openclassrooms.safetynetalert.services;


import com.openclassrooms.safetynetalert.controller.PersonController;
import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.mapper.PersonMapper;
import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.model.FloodModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
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

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks 
    private PersonServiceImpl personServiceImpl;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private AgeUtils age;

    @Mock
    private PersonMapper personMapper;
    Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private List<PersonEntity> listPerson = new ArrayList<>();
    private List<FireStationEntity> listFireStation = new ArrayList<>();
    private List<MedicalRecordEntity> listMedicalRecord =new ArrayList<>();
    private PersonModel personModelActual = new PersonModel();
    private PersonModel personExpected = new PersonModel();

    private PersonEntity person1 = new PersonEntity();
    private PersonEntity person2 = new PersonEntity();

    private FireStationEntity fireStation1 = new FireStationEntity();
    private FireStationEntity fireStation2 = new FireStationEntity();
    private MedicalRecordEntity medicalRecord1 = new MedicalRecordEntity();
    private MedicalRecordEntity medicalRecord2 = new MedicalRecordEntity();
    

    @BeforeEach
    public void init() {
        
        // Création de deux personnes
        person1.setLastName("Cadigan");
        person1.setFirstName("Eric");
        person1.setAddress("951 LoneTree Rd");
        person1.setCity("Culver");
        person1.setZip("97451");
        person1.setPhone("841-874-7458");
        person1.setEmail("gramps@email.com");

        person2.setLastName("Fb");
        person2.setFirstName("Michel");
        person2.setAddress("Rue de Paris");
        person2.setCity("Culver");
        person2.setZip("75000");
        person2.setPhone("222-222-222");
        person2.setEmail("email2@safety.com");

        listPerson.add(person1);
        listPerson.add(person2);


        // Création de deux fireStation
        fireStation1.setStation("1");
        fireStation1.setAddress("951 LoneTree Rd");

        fireStation2.setStation("2");
        fireStation2.setAddress("Rue de Paris");

        listFireStation.add(fireStation1);
        listFireStation.add(fireStation2);

        // Création de deux medicalRecord
        medicalRecord1.setLastName("Cadigan");
        medicalRecord1.setFirstName("Eric");
        medicalRecord1.setBirthdate("11/03/2010"); //min
        medicalRecord1.setMedications(List.of("Paracetamol"));
        medicalRecord1.setAllergies(List.of("Fruits rouges"));

        medicalRecord2.setLastName("Fb");
        medicalRecord2.setFirstName("Michel");
        medicalRecord2.setBirthdate("21/10/1990"); //maj
        medicalRecord2.setMedications(List.of("Paracetamol"));
        medicalRecord2.setAllergies(List.of("Fruits rouges"));

        listMedicalRecord.add(medicalRecord1);
        listMedicalRecord.add(medicalRecord2);

        // Création d'une PersonModel
        personExpected = new PersonModel();
        personExpected.setLastName("Cadigan");
        personExpected.setFirstName("Eric");
        personExpected.setAddress("951 LoneTree Rd");
        personExpected.setCity("Culver");
        personExpected.setZip("97451");
        personExpected.setEmail("gramps@email.com");
        personExpected.setPhone("841-874-7458");

        personModelActual = new PersonModel();
        personModelActual.setLastName("Cadigan");
        personModelActual.setFirstName("Eric");
        personModelActual.setAddress("951 LoneTree Rd");
        personModelActual.setCity("Culver");
        personModelActual.setZip("97451");
        personModelActual.setEmail("gramps@email.com");
        personModelActual.setPhone("841-874-7458");
        
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
         personModel.setLastName("Cadigan");
         personModel.setFirstName("Eric");
         personModel.setAddress("951 LoneTree Rd");
         personModel.setCity("Culver");
         personModel.setZip("97451");
         personModel.setEmail("gramps@email.com");
         personModel.setPhone("841-874-7458");

         when(personMapper.mapToPersonEntity(any(PersonModel.class))).thenReturn(personEntity);
         when(personMapper.mapToPersonModel(any(PersonEntity.class))).thenReturn(personModel);
         when(personRepository.addPerson(personEntity)).thenReturn(personEntity);

        assertEquals(personExpected, personServiceImpl.addPerson(personModelActual));

     }

     @Test
     void testUpdatePersonSuccess() {
         PersonModel personModel = new PersonModel();
         personModel.setLastName("Cadigan");
         personModel.setFirstName("Eric");
         personModel.setAddress("951 LoneTree Rd");
         personModel.setCity("Paris");
         personModel.setZip("78000");
         personModel.setEmail("gramps@email.com");
         personModel.setPhone("111-111-111");

         PersonEntity personEntity = new PersonEntity();
         personEntity.setLastName("Cadigan");
         personEntity.setFirstName("Eric");
         personEntity.setAddress("951 LoneTree Rd");
         personEntity.setCity("Paris");
         personEntity.setZip("78000");
         personEntity.setPhone("111-111-111");
         personEntity.setEmail("gramps@email.com");

         when(personRepository.findByLastNameAndFirstName("Cadigan","Eric")).thenReturn(personEntity);
         when(personMapper.mapToPersonEntity(personModel)).thenReturn(personEntity);
         when(personRepository.updatePerson(personEntity)).thenReturn(personEntity);
         when(personMapper.mapToPersonModel(personEntity)).thenReturn(personModel);

         PersonModel result = personServiceImpl.updatePerson(personModel);

         assertEquals(personModel.getFirstName(), result.getFirstName());
         assertEquals("Paris", result.getCity());
         verify(personRepository, times(1)).updatePerson(personEntity);
         verify(personMapper).mapToPersonEntity(personModel);

     }

    @Test
    void testDeletePersonTrueResult() {
        String lastName = "lastName2";
        String firstName = "firstName2";
        when(personRepository.deletePerson(lastName, firstName )).thenReturn(true);
        Boolean result = personServiceImpl.deletePerson(lastName, firstName);

        assertEquals(true, result);
        verify(personRepository,times(1)).deletePerson(lastName, firstName);
    }
    
    @Test
    void testDeletePersonFalseResult() {
        String lastName = "lastName2";
        String firstName = "firstName2";
        when(personRepository.deletePerson(lastName, firstName )).thenReturn(false);
        Boolean result = personServiceImpl.deletePerson(lastName, firstName);

        assertEquals(false, result);
        verify(personRepository,times(1)).deletePerson(lastName, firstName);
    }


    @Test
    void testGetCommunityEmail() {
       
        when(personRepository.getPersonList()).thenReturn(listPerson);
        List<String> emailList = personServiceImpl.getCommunityEmail("Culver");

        assertEquals(2, emailList.size());
        assertEquals("gramps@email.com", emailList.get(0));
        verify(personRepository, times(1)).getPersonList();
    }

     @Test
    void testGetPersonInfoShouldReturnListPersonInfo() {

        when(personRepository.getPersonList()).thenReturn(listPerson);
        when(personMapper.mapToPersonModel(person1)).thenReturn(personExpected);
        when(medicalRecordRepository.findByLastName("Cadigan")).thenReturn(medicalRecord1);
        when(age.getAge(medicalRecord1.getBirthdate())).thenReturn(30L);
        when(personMapper.mapToPersonInfoModel(personExpected, 30L, medicalRecord1)).thenReturn(
                new PersonInfoModel
                        ("Eric", "Cadigan", "951 LoneTree Rd", 30L, "gramps@email.com",
                                List.of("Paracetamol"), List.of("Fruits rouges")));

        List<PersonInfoModel> personInfoModelList = personServiceImpl.getPersonInfo("Cadigan", "Eric");

        assertNotNull(personInfoModelList);
        assertEquals("Cadigan", personInfoModelList.get(0).getLastName());
        assertEquals(30, personInfoModelList.get(0).getAge());
        assertEquals(1, personInfoModelList.size());

        
    }

    @Test
    void testGetFloodSuccess() {

        List<String> stationNumber = Arrays.asList("1", "2");
        when(personRepository.getPersonList()).thenReturn(listPerson);
        when(fireStationRepository.findByStation("2")).thenReturn(listFireStation);
        when(fireStationRepository.findByStation("1")).thenReturn(listFireStation);
        when(medicalRecordRepository.findByLastNameAndFirstName("Cadigan", "Eric")).thenReturn(medicalRecord1);
        when(medicalRecordRepository.findByLastNameAndFirstName("Fb", "Michel")).thenReturn(medicalRecord2);
        when(age.getAge(medicalRecord1.getBirthdate())).thenReturn(13L);
        when(age.getAge(medicalRecord2.getBirthdate())).thenReturn(34L);
        when(personMapper.mapToPersonInfoModel( 13L,listPerson.get(0) ,medicalRecord1)).thenReturn(
                new PersonInfoModel
                        ("Cadigan", "Eric", "951 LoneTree Rd", 13L, "gramps@email.com",
                                List.of("Paracetamol"), List.of("Fruits rouges")));
        when(personMapper.mapToPersonInfoModel( 34L,listPerson.get(1) ,medicalRecord2)).thenReturn(
                new PersonInfoModel
                        ("Fb", "Michel", "Rue de Paris", 34L, "email2@safety.com",
                                List.of("Paracetamol"), List.of("Fruits rouges")));

        FloodModel result = personServiceImpl.getFlood(stationNumber);
        log.info(" Liste en sortie : {} ", result );

        assertNotNull(result);
        assertEquals(2, result.getListFamille().size());
        verify(fireStationRepository, times(1)).findByStation("1");
        
    }
                   //"11/03/2010"    "21/10/1990"
    @Test
    void testGetChildAlertShouldReturnAChild() {

         List<PersonEntity> listPerson = Arrays.asList(
                 person1,
                 new PersonEntity("Lola","Cadigan","951 LoneTree Rd","Culver","97451","111-111-111", "gramps2@email.com" )
         );
         when(personRepository.findByAddress("951 LoneTree Rd")).thenReturn(listPerson);

         MedicalRecordEntity medicalRecord = new MedicalRecordEntity(
                 "Lola", "Cadigan", "21/11/1962", List.of("Paracetamol"),List.of("Gluten")
         );
         when(medicalRecordRepository.findByLastNameAndFirstName("Cadigan", "Eric")).thenReturn(medicalRecord1);
        when(medicalRecordRepository.findByLastNameAndFirstName("Cadigan", "Lola")).thenReturn(medicalRecord);

        when(age.getMinor(medicalRecord1.getBirthdate())).thenReturn(true);
        when(age.getMinor(medicalRecord.getBirthdate())).thenReturn(false);
        when(age.getAge(medicalRecord1.getBirthdate())).thenReturn(13L);

        PersonModel personModelLola = new PersonModel(
                "Lola","Cadigan","951 LoneTree Rd","Culver","97451","111-111-111", "gramps2@email.com"
        );
        when(personMapper.mapToPersonModel(listPerson.get(0))).thenReturn(personModelLola);

        ChildModel childModel = new ChildModel();
        childModel.setAge(13L);

        List<PersonModel> listPersonFamily = new ArrayList<>();
        listPersonFamily.add(personModelLola);
        listPersonFamily.add(personExpected);

        List<ChildModel> result = personServiceImpl.getChildAlert("951 LoneTree Rd");

        assertEquals(2, listPersonFamily.size());
        assertEquals(1, result.size());
        assertEquals(13, childModel.getAge());
        assertTrue(listPersonFamily.contains(personModelLola));

    }

}
