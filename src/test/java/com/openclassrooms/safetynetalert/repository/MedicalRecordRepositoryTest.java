package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.exception.FileNotReadException;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryTest {

    private MedicalRecordRepository medicalRecordRepository;

    Logger log = LoggerFactory.getLogger(PersonRepository.class);

    private MedicalRecordEntity medicalRecord;


    @BeforeEach
    void init() throws FileNotReadException {
        SerializationDriver serializationDriver = new SerializationDriver();
        medicalRecordRepository = new MedicalRecordRepository(serializationDriver);
    }

    @Test
    void testMedicalRecordRepositoryAddMedicalRecord() {

        MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
        medicalRecord.setFirstName("Toto");
        medicalRecord.setLastName("Tata");
        medicalRecord.setBirthdate("21/07/1989");
        
        List<MedicalRecordEntity> listMedicalRecord = medicalRecordRepository.getMedicalRecordList();
        List<String> medications = Arrays.asList("CellCept:350mg", "TITI:100mg");
        medicalRecord.setMedications(medications);
        medicalRecordRepository.addMedicalRecord(medicalRecord);

        log.info("Résultat : {} ", listMedicalRecord);

        assertNotNull(medications);
        assertTrue(listMedicalRecord.stream().anyMatch(medicalRecord1 ->
        medicalRecord1.getFirstName().equals("Toto")));

    }

    @Test
    void testMedicalRecordRepositoryRemoveMedicalRecord() {

       Boolean result = medicalRecordRepository.deleteMedicalRecord("Boyd", "John" );
       List<MedicalRecordEntity> listMedicalRecord = medicalRecordRepository.getMedicalRecordList();
       log.info("Résultat : {} {}", result, listMedicalRecord);

       assertTrue(result, "Le test a échoué");
       assertThat(listMedicalRecord.size()).isEqualTo(22);


    }

    @Test
    void testMedicalRecordRepositoryFindByLastNameAndFirstName() {

        medicalRecord = medicalRecordRepository.findByLastNameAndFirstName("Boyd","John");
        log.info("Résultat : {}", medicalRecord);

        assertEquals("Boyd", medicalRecord.getLastName());
        assertEquals("John", medicalRecord.getFirstName());
        
    }

}
