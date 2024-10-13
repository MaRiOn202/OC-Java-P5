package com.openclassrooms.safetynetalert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.model.MedicalRecordModel;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MockMvc mockMvc;

    private MedicalRecordModel medicalRecordModel = new MedicalRecordModel();

    private ObjectMapper mapper = new ObjectMapper();  // conversion objet en mapper +++

    Logger log = LoggerFactory.getLogger(MedicalRecordController.class);

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();
    }

    @Test
    void testAddMedicalRecordShouldReturnCreated() throws Exception {

        medicalRecordModel = new MedicalRecordModel();
        medicalRecordModel = new MedicalRecordModel();
        medicalRecordModel.setLastName("Fb");
        medicalRecordModel.setFirstName("Michel");
        medicalRecordModel.setBirthdate("03/11/2013");
        medicalRecordModel.setMedications(List.of("Paracétamol"));
        medicalRecordModel.setAllergies(List.of("Cacahuètes"));
        
        MedicalRecordModel medicalRecordExit = new MedicalRecordModel();
        medicalRecordExit.setLastName("Fb");
        medicalRecordExit.setFirstName("Michel");
        medicalRecordExit.setBirthdate("03/11/2013");
        medicalRecordExit.setMedications(List.of("Paracétamol"));
        medicalRecordExit.setAllergies(List.of("Cacahuètes"));

        when(medicalRecordService.addMedicalRecord(any(MedicalRecordModel.class))).thenReturn(medicalRecordExit);
        log.info(" Carnet de santé: {} ", medicalRecordExit );

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(medicalRecordModel)))
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void testUpdateMedicalRecordShouldReturnOK() throws Exception {
        
        medicalRecordModel = new MedicalRecordModel();
        medicalRecordModel.setLastName("Cadigan");
        medicalRecordModel.setFirstName("Michel");
        medicalRecordModel.setBirthdate("03/11/2013");
        medicalRecordModel.setMedications(List.of("Paracétamol"));
        medicalRecordModel.setAllergies(List.of("Cacahuètes"));
        
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecordModel.class))).thenReturn(medicalRecordModel);
        log.info(" MR updated : {} ", medicalRecordModel );
        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(medicalRecordModel)))
                        .andExpect(status().isOk())
                        .andReturn();
        verify(medicalRecordService, times(1)).updateMedicalRecord(any(MedicalRecordModel.class));
    }

    @Test
    void testDeleteMedicalRecordSuccess() throws Exception {
        String lastName = "Cadigan";
        String firstName = "Eric";
        when(medicalRecordService.deleteMedicalRecord(lastName, firstName)).thenReturn(true);
        log.info(" MR deleted : {} ", true );
        mockMvc.perform(delete("/medicalRecord")
                        .param("lastName", lastName)
                        .param("firstName", firstName)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();
        verify(medicalRecordService, times(1)).deleteMedicalRecord(lastName, firstName);
    }

}
