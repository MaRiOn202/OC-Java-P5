package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    //Logger log = LoggerFactory.getLogger(PersonController.class);


    @Test
    void testAddPersonShouldReturnCreated() {

        PersonModel personModel = new PersonModel();
        personModel.setLastName("Fb");
        personModel.setFirstName("Michel");
        personModel.setAddress("44 rue du Chène");
        personModel.setCity("Paris");
        personModel.setZip("75000");
        personModel.setEmail("MichelFd@email.com");
        personModel.setPhone("111-111-111");

        doNothing().when(personService).addPerson(any(PersonModel.class));
        // A finir

    }
}
