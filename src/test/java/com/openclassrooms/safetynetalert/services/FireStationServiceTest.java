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

import javax.print.DocFlavor;
import java.time.LocalDate;
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
    AgeUtils age;

    
     // il faut l'initialiser important
   //private static final List<FireStationModel> listFireStation = new ArrayList<>();
     private List<PersonEntity> listPerson  = new ArrayList<>();
    private List<FireStationEntity> listFireStation  = new ArrayList<>();
    private List<MedicalRecordEntity> listMedicalRecord = new ArrayList<>();

    private FireStationModel fireStationModelActual = new FireStationModel();
    private FireStationModel fireStationModelExpected = new FireStationModel();


   @BeforeEach
    public void init() {

      // Création de deux PersonEntity
        PersonEntity person1 = new PersonEntity();
        PersonEntity person2 = new PersonEntity();

        person1.setLastName("lastName1");
        person1.setFirstName("firstName1");
        person1.setPhone("999-999-999");
        person1.setAddress("Paris");

        person2.setLastName("lastName2");
        person2.setFirstName("firstName2");
        person2.setPhone("888-888-888");
        person2.setAddress("Brest");

        listPerson.add(person1);
        listPerson.add(person2);


      // Création de deux FireStation
      FireStationEntity fireStation1 = new FireStationEntity();
      FireStationEntity fireStation2 = new FireStationEntity();

      fireStation1.setStation("3");
      fireStation1.setAddress("Paris");
      listFireStation.add(fireStation1);

      fireStation2.setStation("2");
      fireStation2.setAddress("Lille");
      listFireStation.add(fireStation2);

       // Création de MedicalRecordEntity des 2 Person créées
       MedicalRecordEntity medicalRecordEntity1 = new MedicalRecordEntity();
       MedicalRecordEntity medicalRecordEntity2 = new MedicalRecordEntity();
       List<String> medicationsAndAllergies = new ArrayList<>();
       medicalRecordEntity1.setLastName("lastName1");
       medicalRecordEntity1.setFirstName("firstName1");
       medicalRecordEntity1.setBirthdate("09/06/2000");
       medicalRecordEntity1.setMedications(medicationsAndAllergies);
       medicalRecordEntity1.setAllergies(medicationsAndAllergies);
       medicalRecordEntity2.setLastName("lastName2");
       medicalRecordEntity2.setFirstName("firstName2");
       medicalRecordEntity2.setBirthdate("09/06/2017");
       medicalRecordEntity2.setMedications(medicationsAndAllergies);
       medicalRecordEntity2.setAllergies(medicationsAndAllergies);
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
       PersonCovertModel personCovertModel1 = new PersonCovertModel();
       List<PersonCovertModel> listPersonCovert = new ArrayList<>();
       personCovertModel1.setLastName("lastName1");
       personCovertModel1.setFirstName("firstName1");
       listPersonCovert.add(personCovertModel1);

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
       fireStationModel1.setAddress("address1");
       fireStationModel1.setStation("1");        // nvelle station à update

       FireStationEntity fireStationEntity1 = new FireStationEntity();  // caserne recup dans bdd
       fireStationEntity1.setAddress("address1");
       fireStationModel1.setStation("2");           //ancien num

       FireStationEntity fireStationUpdate = new FireStationEntity();  // caserne updated
       fireStationUpdate.setAddress("address1");
       fireStationUpdate.setStation("1");             // nveau num updated

       when(fireStationRepository.findByAddress("address1")).thenReturn(fireStationEntity1);
       when(fireStationRepository.updateFireStation(fireStationEntity1)).thenReturn(fireStationUpdate);

       FireStationModel result = fireStationImpl.updateFireStation(fireStationModel1);

       assertEquals("1", result.getStation());
       verify(fireStationRepository, times(1)).updateFireStation(fireStationEntity1);

    }

    @Test
    void testDeleteFireStation() {
       String address = "address1";
       when(fireStationRepository.deleteFireStation(address)).thenReturn(true);
       Boolean result = fireStationImpl.deleteFireStation(address);

       assertEquals(true, result);
       verify(fireStationRepository,times(1)).deleteFireStation(address);
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
     // Je veux récupérer l'adresse des personnes d'un même foyer
        // Créatino d'un jeu de données fictif
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

     // Création de MedicalRecordEntity des 2 Person créées
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

/*    @Test
    void testGetPersonCovertByFireStation() {

        long numberOfAdult;
        long numberOfChildren;
        String stationNumber = "1";
        PersonEntity person1 = new PersonEntity();
        PersonCovertModel personCovertModel1 = new PersonCovertModel();
        MedicalRecordEntity medicalRecordEntity1 = new MedicalRecordEntity();
        medicalRecordEntity1.setLastName("lastName1");
        medicalRecordEntity1.setFirstName("firstName1");
        medicalRecordEntity1.setBirthdate("09/06/2000");

        when(fireStationRepository.findByStation(stationNumber)).thenReturn(listFireStation);
        when(personRepository.findByAddress("Address1")).thenReturn(listPerson);
        when(medicalRecordRepository.findByLastNameAndFirstName("lastName1", "firstName1")).thenReturn(medicalRecordEntity1);
        when(age.getMinor(medicalRecordEntity1.getBirthdate())).thenReturn(false);       // adulte car 24 ans
        when(personMapper.mapToPersonCovertModel(person1)).thenReturn(personCovertModel1);

        PersonFireStationModel pfsm = fireStationImpl.getPersonCovertByFireStation(stationNumber);

        assertEquals("lastName1", pfsm.getMembers().get(0).getLastName());
        assertEquals(1, pfsm.getNbreAdult());
        // problème medicalRecord is null  pb avec le birthday


    }*/

}
