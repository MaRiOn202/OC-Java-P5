package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.mapper.FireStationMapper;
import com.openclassrooms.safetynetalert.mapper.PersonMapper;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.impl.FireStationImpl;

import com.openclassrooms.safetynetalert.utils.AgeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @InjectMocks
    private FireStationImpl fireStationImpl;

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @Mock
    FireStationMapper fireStationMapper;

    @Mock
    PersonMapper personMapper;

    @Mock
    AgeUtils age;


    private List<FireStationEntity> listFireStation  = new ArrayList<>();
    private List<MedicalRecordEntity> listMedicalRecord = new ArrayList<>();
    private List<PersonEntity> listPerson= new ArrayList<>();
    private List<PersonCovertModel> listPersonCovert = new ArrayList<>();
    private FireStationModel fireStationModelActual = new FireStationModel();
    private FireStationModel fireStationModelExpected = new FireStationModel();
    private PersonEntity person1 = new PersonEntity();
    private PersonEntity person2 = new PersonEntity();
    private FireStationEntity fireStation1 = new FireStationEntity();
    private FireStationEntity fireStation2 = new FireStationEntity();
    private MedicalRecordEntity medicalRecordEntity1 = new MedicalRecordEntity();
    private MedicalRecordEntity medicalRecordEntity2 = new MedicalRecordEntity();
    private PersonCovertModel personCovertModel1 = new PersonCovertModel();
    private PersonCovertModel personCovertModel2 = new PersonCovertModel();


   @BeforeEach
    public void init() {

      // Création de deux PersonEntity
        person1.setLastName("lastName1");
        person1.setFirstName("firstName1");
        person1.setAddress("Rue de Paris");
        person1.setCity("Paris");
        person1.setZip("75000");
        person1.setPhone("999-999-999");
        person1.setAddress("Paris");
        person1.setEmail("email@safety.com");

        person2.setLastName("lastName2");
        person2.setFirstName("firstName2");
        person2.setPhone("888-888-888");
        person2.setAddress("Rue de Brest");
        person2.setCity("Brest");
        person2.setZip("29000");
        person2.setEmail("email2@safety.com");

        listPerson.add(person1);
        listPerson.add(person2);


      // Création de deux FireStation
      fireStation1.setStation("1");
      fireStation1.setAddress("Paris");
      listFireStation.add(fireStation1);

      fireStation2.setStation("2");
      fireStation2.setAddress("Lille");
      listFireStation.add(fireStation2);

       // Création de MedicalRecordEntity des 2 Person créées
       medicalRecordEntity1.setLastName("lastName1");
       medicalRecordEntity1.setFirstName("firstName1");
       medicalRecordEntity1.setBirthdate("09/06/2000");            // adulte
       medicalRecordEntity1.setMedications(List.of("Paracetamol"));
       medicalRecordEntity1.setAllergies(List.of("Fruits rouges"));
       
       medicalRecordEntity2.setLastName("lastName2");
       medicalRecordEntity2.setFirstName("firstName2");
       medicalRecordEntity2.setBirthdate("09/06/2017");           // enfant
       medicalRecordEntity2.setMedications(List.of("Coumadine"));
       medicalRecordEntity2.setAllergies(List.of("Gluten"));
       listMedicalRecord.add(medicalRecordEntity1);
       listMedicalRecord.add(medicalRecordEntity2);

       // Création de l'objet FireMembersModel qui représente les deux personnes du foyer créées précéd
       FireMembersModel fireMembersModel1 = new FireMembersModel();
       FireMembersModel fireMembersModel2 = new FireMembersModel();

       fireMembersModel1.setLastname("lastName1");
       fireMembersModel1.setFirstname("firstName1");
       fireMembersModel2.setLastname("lastName2");
       fireMembersModel2.setFirstname("firstName2");

       // Personnes couvertes
       personCovertModel1.setLastName("lastName1");
       personCovertModel1.setFirstName("firstName1");
       personCovertModel1.setAddress("Rue de Paris");
       personCovertModel1.setPhone("999-999-999");
       listPersonCovert.add(personCovertModel1);
       personCovertModel2.setLastName("lastName2");
       personCovertModel2.setFirstName("firstName2");
       personCovertModel2.setAddress("Rue de Brest");
       personCovertModel2.setPhone("999-999-999");
       listPersonCovert.add(personCovertModel2);

       // Création d'une FireStationModel
       fireStationModelExpected = new FireStationModel();
       fireStationModelExpected.setAddress("644 Gershwin Cir");
       fireStationModelExpected.setStation("4");
       
       fireStationModelActual = new FireStationModel();
       fireStationModelActual.setAddress("644 Gershwin Cir");
       fireStationModelActual.setStation("4");

    }

    @Test
    void testAddFireStationShouldReturnFireStationModel() {

        FireStationEntity fireStationEntity = new FireStationEntity();
        fireStationEntity.setAddress("644 Gershwin Cir");
        fireStationEntity.setStation("4");
        
        when(fireStationRepository.addFireStation(any(FireStationEntity.class))).thenReturn(fireStationEntity);
        FireStationModel result = fireStationImpl.addFireStation(fireStationModelActual);

        assertEquals(fireStationModelExpected, result);
    }


    @Test
    void testUpdateFireStation() {
       FireStationModel fireStationModel1 = new FireStationModel();  
       fireStationModel1.setAddress("Paris");
       fireStationModel1.setStation("1");        // nvelle station à update

       FireStationEntity fireStationEntity1 = new FireStationEntity();  // caserne recup dans bdd
       fireStationEntity1.setAddress("Paris");
       fireStationModel1.setStation("2");           //ancien num

       FireStationEntity fireStationUpdate = new FireStationEntity();  // caserne updated
       fireStationUpdate.setAddress("Paris");
       fireStationUpdate.setStation("1");             // nveau num updated

       when(fireStationRepository.findByAddress("Paris")).thenReturn(fireStationEntity1);
       when(fireStationRepository.updateFireStation(fireStationEntity1)).thenReturn(fireStationUpdate);

       FireStationModel result = fireStationImpl.updateFireStation(fireStationModel1);

       assertEquals("1", result.getStation());
       verify(fireStationRepository, times(1)).updateFireStation(fireStationEntity1);

    }

    @Test
    void testDeleteFireStationTrueResult() {
       String address = "address1";
       when(fireStationRepository.deleteFireStation(address)).thenReturn(true);
       Boolean result = fireStationImpl.deleteFireStation(address);

       assertEquals(true, result);
       verify(fireStationRepository,times(1)).deleteFireStation(address);
    }

    @Test
    void testDeleteFireStationFalseResult() {
        String address = "address1";
        when(fireStationRepository.deleteFireStation(address)).thenReturn(false);
        Boolean result = fireStationImpl.deleteFireStation(address);

        assertEquals(false, result);
        verify(fireStationRepository,times(1)).deleteFireStation(address);
    }

    @Test
    void testGetPersonCovertByFireStation() {

        String stationNumber = "1";
        
        when(fireStationRepository.findByStation(stationNumber)).thenReturn(listFireStation);
        when(personRepository.findByAddress("Paris")).thenReturn(listPerson);
        when(medicalRecordRepository.findByLastNameAndFirstName("lastName1", "firstName1")).thenReturn(medicalRecordEntity1);
        when(medicalRecordRepository.findByLastNameAndFirstName("lastName2", "firstName2")).thenReturn(medicalRecordEntity2);
        when(age.getMinor(medicalRecordEntity1.getBirthdate())).thenReturn(false);
        when(age.getMinor(medicalRecordEntity2.getBirthdate())).thenReturn(true);

        when(personMapper.mapToPersonCovertModel(person1)).thenReturn(personCovertModel1);
        when(personMapper.mapToPersonCovertModel(person2)).thenReturn(personCovertModel2);

        PersonFireStationModel pfsm = fireStationImpl.getPersonCovertByFireStation(stationNumber);

        assertEquals(2, pfsm.getMembers().size());
        assertEquals(1, pfsm.getNbreAdult());      
        assertEquals(1, pfsm.getNbreEnfant());

    }

    @Test
    void testGetPhoneAlert() {

      when(personRepository.getPersonList()).thenReturn(listPerson);
      when(fireStationRepository.findByStation("1")).thenReturn(listFireStation);
      List<String> phoneList = fireStationImpl.getPhoneAlert("1");

      assertEquals(1, phoneList.size());
      assertEquals("999-999-999", phoneList.get(0));
      verify(fireStationRepository, times(1)).findByStation("1");

    }

    @Test
    void testGetFireMembersAddress() {

       // Création d'un jeu de données fictif
     String address = "Address1";

     List<PersonEntity> listPerson = new ArrayList<>();

    // Création de deux personnes
     PersonEntity person1 = new PersonEntity();
     PersonEntity person2 = new PersonEntity();

     person1.setLastName("lastName1");
     person1.setFirstName("firstName1");
     person1.setAddress(address);
     person2.setLastName("lastName2");
     person2.setFirstName("firstName2");
     person2.setAddress(address);
     listPerson.add(person1);
     listPerson.add(person2);

     // Création de FireStationEntity
     FireStationEntity fireStation1 = new FireStationEntity();
     fireStation1.setStation("1");
     fireStation1.setAddress(address);

     // Création de MedicalRecordEntity des 2 Personnes créées
     MedicalRecordEntity medicalRecordEntity1 = new MedicalRecordEntity();
     MedicalRecordEntity medicalRecordEntity2 = new MedicalRecordEntity();
     medicalRecordEntity1.setLastName("lastName1");
     medicalRecordEntity1.setFirstName("firstName1");
     medicalRecordEntity2.setLastName("lastName2");
     medicalRecordEntity2.setFirstName("firstName2");

     // Création de l'objet FireMembersModel qui représente les deux personnes du foyer créées précéd
     FireMembersModel fireMembersModel1 = new FireMembersModel();
     FireMembersModel fireMembersModel2 = new FireMembersModel();

     fireMembersModel1.setLastname("lastName1");
     fireMembersModel1.setFirstname("firstName1");
     fireMembersModel2.setLastname("lastName2");
     fireMembersModel2.setFirstname("firstName2");

     long resultAge = age.getAge(medicalRecordEntity1.getBirthdate());

     // Lecture des différentes méthodes avec jeux de données fictifs
     when(personRepository.findByAddress(address)).thenReturn(listPerson);
     when(fireStationRepository.findByAddress(address)).thenReturn(fireStation1);
     when(medicalRecordRepository.findByLastNameAndFirstName
             ("lastName1", "firstName1")).thenReturn(medicalRecordEntity1);
     when(medicalRecordRepository.findByLastNameAndFirstName
             ("lastName2", "firstName2")).thenReturn(medicalRecordEntity2);
     // liste de personEntity et medicalEntity
     when(fireStationMapper.mapToFireMembersModel
             (resultAge,person1, medicalRecordEntity1)).thenReturn(fireMembersModel1);
     when(fireStationMapper.mapToFireMembersModel
             (resultAge,person2, medicalRecordEntity2)).thenReturn(fireMembersModel2);

     FireModel result = fireStationImpl.getFireMembersAddress(address);

     // Résultats jeux de données proposés
     assertEquals("lastName1", result.getFireMembersModels().get(0).getLastname());
     assertEquals("firstName1", result.getFireMembersModels().get(0).getFirstname());
     assertEquals("lastName2", result.getFireMembersModels().get(1).getLastname());
     assertEquals("firstName2", result.getFireMembersModels().get(1).getFirstname());
     assertEquals("1", result.getStation());

     // Vérification méthodes
     verify(personRepository, times(1)).findByAddress(address);
     verify(fireStationRepository, times(1)).findByAddress(address);
     verify(medicalRecordRepository, times(1)).findByLastNameAndFirstName("lastName1", "firstName1");
     verify(fireStationMapper, times(1)).mapToFireMembersModel(resultAge,person1, medicalRecordEntity1);
    }

}
