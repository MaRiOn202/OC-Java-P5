package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Test
    void testAddMedicalRecord() {
        
    }


}
