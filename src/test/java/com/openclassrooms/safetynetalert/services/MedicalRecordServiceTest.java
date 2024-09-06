package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.mapper.MedicalRecordMapper;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.services.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)


public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordServiceImpl medicalServiceImpl;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    MedicalRecordMapper medicalMapper;

    @BeforeEach
    public void init() {

    }

    @Test
    void testDeleteMedicalRecord() {
        String lastName = "lastName1";
        String firstName = "firstName1";
        when(medicalRecordRepository.deleteMedicalRecord(lastName, firstName)).thenReturn(true);
        Boolean result = medicalServiceImpl.deleteMedicalRecord(lastName, firstName);

        assertEquals(true, result);
        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(lastName, firstName);
    }



}
