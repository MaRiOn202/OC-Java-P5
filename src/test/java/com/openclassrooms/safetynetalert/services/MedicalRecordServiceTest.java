package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.mapper.MedicalRecordMapper;
import com.openclassrooms.safetynetalert.model.MedicalRecordModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.services.impl.MedicalRecordServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordServiceImpl medicalServiceImpl;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private MedicalRecordMapper medicalMapper;

    Logger log = LoggerFactory.getLogger(MedicalRecordServiceTest.class);

    private MedicalRecordModel medicalModelActual = new MedicalRecordModel();
    private MedicalRecordModel medicalModelExpected = new MedicalRecordModel();

    private MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();

    @BeforeEach
    public void init() {

        // Création MedicalRecordModel
        medicalModelExpected = new MedicalRecordModel();
        medicalModelExpected.setLastName("Cadigan");
        medicalModelExpected.setFirstName("Eric");
        medicalModelExpected.setBirthdate("03/06/1984");
        medicalModelExpected.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        medicalModelExpected.setAllergies(List.of("nillacilan"));

        medicalModelActual = new MedicalRecordModel();
        medicalModelActual.setLastName("Cadigan");
        medicalModelActual.setFirstName("Eric");
        medicalModelActual.setBirthdate("03/06/1984");
        medicalModelActual.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));  // = null ?
        medicalModelActual.setAllergies(Arrays.asList("nillacilan", "fruits rouges"));
    }

  @Test
    void testAddMedicalRecordShouldReturnNewMedicalRecord() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        medicalRecordEntity.setLastName("Cadigan");
        medicalRecordEntity.setFirstName("Eric");
        medicalRecordEntity.setBirthdate("03/06/1984");    //MMddyyyy
        medicalRecordEntity.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));  //ok
        medicalRecordEntity.setAllergies(List.of("nillacilan"));   // 1 élément

        MedicalRecordModel medicalRecordModel = new MedicalRecordModel();
        medicalRecordModel.setLastName("Cadigan");
        medicalRecordModel.setFirstName("Eric");
        medicalRecordModel.setBirthdate("03/06/1984");
        medicalRecordModel.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecordModel.setAllergies(List.of("nillacilan"));

        when(medicalMapper.mapToMedicalRecordEntity(any(MedicalRecordModel.class))).thenReturn(medicalRecordEntity);
        when(medicalMapper.mapToMedicalRecordModel(any(MedicalRecordEntity.class))).thenReturn(medicalRecordModel);
        when(medicalRecordRepository.addMedicalRecord(medicalRecordEntity)).thenReturn(medicalRecordEntity);
        
        log.info("Résultat : {} {}", medicalModelExpected, medicalModelActual);
        assertEquals(medicalModelExpected, medicalServiceImpl.addMedicalRecord(medicalModelActual));
        
    }

    @Test
    void testUpdateMedicalRecordShouldReturnMedicalRecordUpdated() {
        MedicalRecordModel medicalRecordModel = new MedicalRecordModel();
        medicalRecordModel.setLastName("Cadigan");
        medicalRecordModel.setFirstName("Eric");
        medicalRecordModel.setBirthdate("03/25/1984");
        medicalRecordModel.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecordModel.setAllergies(List.of("nillacilan"));

        medicalRecordEntity = new MedicalRecordEntity();            //ex
        medicalRecordEntity.setLastName("Cadigan");
        medicalRecordEntity.setFirstName("Eric");
        medicalRecordEntity.setBirthdate("07/21/1989");    //MMddyyyy
        medicalRecordEntity.setMedications(Arrays.asList("paracétamol:500mg", "Coumadine:100mg"));  //ok
        medicalRecordEntity.setAllergies(List.of("noix"));

        MedicalRecordEntity updatedEntity = new MedicalRecordEntity();
        updatedEntity.setLastName("Cadigan");
        updatedEntity.setFirstName("Eric");
        updatedEntity.setBirthdate("03/25/1984");
        updatedEntity.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        updatedEntity.setAllergies(List.of("nillacilan"));

        MedicalRecordModel updatedModel = new MedicalRecordModel();
        updatedModel.setLastName("Cadigan");
        updatedModel.setFirstName("Eric");
        updatedModel.setBirthdate("03/25/1984");
        updatedModel.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        updatedModel.setAllergies(List.of("nillacilan"));

//        when(medicalRecordRepository.findByLastNameAndFirstName("Cadigan","Eric")).thenReturn(medicalRecordEntity);
        when(medicalMapper.mapToMedicalRecordEntity(medicalRecordModel)).thenReturn(updatedEntity);
        when(medicalRecordRepository.updateMedicalRecord(updatedEntity)).thenReturn(updatedEntity);
        when(medicalMapper.mapToMedicalRecordModel(updatedEntity)).thenReturn(updatedModel);
        
        MedicalRecordModel result = medicalServiceImpl.updateMedicalRecord(medicalRecordModel);
        log.info("Résultat : {} ",result );
        
        assertEquals("03/25/1984", result.getBirthdate());
        assertEquals(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), result.getMedications());
        assertEquals(List.of("nillacilan"), result.getAllergies());
        verify(medicalRecordRepository, times(1)).updateMedicalRecord(updatedEntity);
        
    }


    @Test
    void testDeleteMedicalRecordShouldReturnTrueResult() {
        String lastName = "R2";
        String firstName = "D2";
        when(medicalRecordRepository.deleteMedicalRecord(lastName, firstName)).thenReturn(true);
        Boolean result = medicalServiceImpl.deleteMedicalRecord(lastName, firstName);

        assertEquals(true, result);
        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(lastName, firstName);
    }

    @Test
    void testDeleteMedicalRecordShouldReturnFalseResult() {
        String lastName = "R2";
        String firstName = "D2";
        when(medicalRecordRepository.deleteMedicalRecord(lastName, firstName)).thenReturn(false);
        Boolean result = medicalServiceImpl.deleteMedicalRecord(lastName, firstName);

        assertEquals(false, result);
        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(lastName, firstName);
    }

}
