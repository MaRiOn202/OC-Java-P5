package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.services.FireStationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

    @InjectMocks
    private FireStationController fireStationController;

    @Mock
    private FireStationService fireStationService;

    @Autowired
    private MockMvc mockMvc;





}
