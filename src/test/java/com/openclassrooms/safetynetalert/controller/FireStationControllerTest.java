package com.openclassrooms.safetynetalert.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalert.model.*;
import com.openclassrooms.safetynetalert.services.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

    @InjectMocks
    private FireStationController fireStationController;

    @Mock
    private FireStationService fireStationService;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    Logger log = LoggerFactory.getLogger(FireStationController.class);

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(fireStationController).build();
    }

    @Test
    void testAddFireStationShouldReturnCreated() throws Exception {
        FireStationModel fireStationModel = new FireStationModel();
        fireStationModel.setAddress("112 Steppes Pl");
        fireStationModel.setStation("6");

        FireStationModel fireStationExit = new FireStationModel();
        fireStationExit.setAddress("112 Steppes Pl");
        fireStationExit.setStation("6");

        when(fireStationService.addFireStation(any(FireStationModel.class))).thenReturn(fireStationExit);
        log.info(" Caserne en sortie: {} ", fireStationExit );

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(fireStationModel)))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void testUpdateFireStationShouldReturnOK() throws Exception {

        FireStationModel fireStationModel = new FireStationModel();
        fireStationModel.setStation("5");
        fireStationModel.setAddress("644 Gershwin Cir");


        when(fireStationService.updateFireStation(any(FireStationModel.class))).thenReturn(fireStationModel);
        log.info(" Personne updated : {} ", fireStationModel );
        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(fireStationModel)))
                .andExpect(status().isOk())
                .andReturn();
        verify(fireStationService, times(1)).updateFireStation(any(FireStationModel.class));
    }

    @Test
    void testDeleteFireStationSuccess() throws Exception {
        String address = "644 Gershwin Cir";
        when(fireStationService.deleteFireStation(address)).thenReturn(true);
        log.info(" FireStation deleted : {} ", true );
        mockMvc.perform(delete("/firestation")
                        .param("address", address)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        verify(fireStationService, times(1)).deleteFireStation(address);
    }

    @Test
    void testGetPersonCovertByFireStation() throws Exception {
        String stationNumber = "2";
        List<PersonCovertModel> members = Arrays.asList(
                new PersonCovertModel("Simone", "Stran", "111-111-111", "1509 Culver St"),
                new PersonCovertModel("Simon", "Fb", "222-222-222", "1509 Culver St"));
        PersonFireStationModel personFireStationModel = new PersonFireStationModel();
        personFireStationModel.setMembers(members);
        personFireStationModel.setNbreAdult(2L);
        personFireStationModel.setNbreEnfant(0L);

        when(fireStationService.getPersonCovertByFireStation(stationNumber)).thenReturn(personFireStationModel);

        log.info("Liste des personnes couvertes: {} ", personFireStationModel );

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "2")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();
    }

    @Test
    void testGetPhoneAlertSuccess() throws Exception {
        String stationNumber = "2";
        List<String> phoneList = Arrays.asList(
                "111-111-111",
                "222-222-222",
                "333-333-333");
        when(fireStationService.getPhoneAlert(stationNumber)).thenReturn(phoneList);

        log.info("Liste des tel: {} ", phoneList );

        mockMvc.perform(get("/phoneAlert")
                        .param("stationNumber", "2")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(jsonPath("$.length()").value(3))
                        .andExpect(status().isOk())
                        .andReturn();

    }

    @Test
    void testGetFireMembersAddressSuccess() throws Exception {
         String address = "1509 Culver St";
         List<FireMembersModel> fireMembersList = Arrays.asList(
                new FireMembersModel("Michel","Tan", "111-111-111", 25L, List.of("Sirop"), List.of("Fruits rouges")),
                new FireMembersModel("Michelle","Tan", "222-222-222", 28L, List.of("Paracétamol"), List.of("Cacahuètes")));
        FireModel fireModel = new FireModel();
        fireModel.setFireMembersModels(fireMembersList);
        fireModel.setStation("2");

        when(fireStationService.getFireMembersAddress(address)).thenReturn(fireModel);

        log.info("Liste des fireMembers: {} ", fireMembersList );

        mockMvc.perform(get("/fire")
                        .param("address", "1509 Culver St")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isOk())
                .andReturn();
    }


}
