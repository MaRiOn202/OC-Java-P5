package com.openclassrooms.safetynetalert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.model.FloodModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.services.PersonService;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();  // conversion objet en mapper +++

    Logger log = LoggerFactory.getLogger(PersonController.class);

    @BeforeEach
    void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void testAddPersonShouldReturnCreated() throws Exception {

        PersonModel personModel = new PersonModel();
        personModel.setLastName("Fb");
        personModel.setFirstName("Michel");
        personModel.setAddress("112 Steppes Pl");
        personModel.setCity("Paris");
        personModel.setZip("75000");
        personModel.setEmail("MichelFd@email.com");
        personModel.setPhone("111-111-111");

        PersonModel personModelExit = new PersonModel();
        personModelExit.setLastName("Fb");
        personModelExit.setFirstName("Michel");
        personModelExit.setAddress("112 Steppes Pl");
        personModelExit.setCity("Paris");
        personModelExit.setZip("75000");
        personModelExit.setEmail("MichelFd@email.com");
        personModelExit.setPhone("111-111-111");

        when(personService.addPerson(any(PersonModel.class))).thenReturn(personModelExit);
        log.info(" Personne en sortie: {} ", personModelExit );

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(personModel)))
                        .andExpect(jsonPath("$.length()").value(7))  
                        .andExpect(status().isCreated())
                        .andReturn();
    }

    @Test
    void testUpdatePersonShouldReturnOK() throws Exception {

        PersonModel personModel = new PersonModel();
        personModel.setLastName("Cadigan");
        personModel.setFirstName("Eric");
        personModel.setAddress("951 LoneTree Rd");
        personModel.setCity("Culver");
        personModel.setZip("97451");
        personModel.setEmail("gramps@email.com");
        personModel.setPhone("841-874-7458");

        when(personService.updatePerson(any(PersonModel.class))).thenReturn(personModel);
        log.info(" Personne updated : {} ", personModel );
        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(personModel)))
                .andExpect(status().isOk())
                .andReturn();
        verify(personService, times(1)).updatePerson(any(PersonModel.class));
    }

    @Test
    void testDeletePersonSuccess() throws Exception {
        String lastName = "Cadigan";
        String firstName = "Eric";
        when(personService.deletePerson(lastName, firstName)).thenReturn(true);
        log.info(" Person deleted : {} ", true );
        mockMvc.perform(delete("/person")
                        .param("lastName", lastName)
                        .param("firstName", firstName)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();
        verify(personService, times(1)).deletePerson(lastName, firstName);
    }

    @Test
    void testGetChildAlertSuccess() throws Exception {

        // Paramètre d'entrée
        String address = "748 Townings Dr";
        // Création de 2 enfants + membres de la famille  2 et 1
        List<ChildModel> childrenList = Arrays.asList(
                new ChildModel("Pierre", "Jacques", 5L,
                        List.of(new PersonModel("Simone","Stran", "1509 Culver St", "Paris", "75000", "111-111-111", "mail@safety.com"),
                                new PersonModel("Simon","Stran", "1509 Culver St", "Paris", "75000", "111-111-111", "mail1@safety.com"))),
                new ChildModel("Paulette", "Jacques", 15L,
                        List.of(new PersonModel("Jean-Paul","Fb", "1509 Culver St", "Paris", "75000", "111-111-111", "mail@safety.com")))
        );

        when(personService.getChildAlert(address)).thenReturn(childrenList);

        log.info("Liste: {} ", childrenList );

        mockMvc.perform(get("/childAlert")
                .param("address", address)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testGetFloodSuccess() throws Exception {
        List<String> stationNumberList = Arrays.asList(
                "1",
                "2");
        // Liste de 2 familles pour renvoyer les foyers
        List<PersonInfoModel> famille1 = Arrays.asList(
                new PersonInfoModel("Simone","Stran", "1509 Culver St", 30L, "mail@safety.com", List.of("Paracétamol"), List.of("Fruits rouges")),
                new PersonInfoModel("Simon","Stran", "1509 Culver St", 40L, "mail1@safety.com", List.of("Paracétamol","Coumadine"), List.of("Cacahuètes")));
        List<PersonInfoModel> famille2 = Arrays.asList(
                new PersonInfoModel("Michel","Tan", "112 Steppes Pl", 25L, "mail3@safety.com", List.of("Sirop"), List.of("Fruits rouges")),
                new PersonInfoModel("Michelle","Tan", "112 Steppes Pl", 28L, "mail4@safety.com", List.of("Paracétamol"), List.of("Cacahuètes")));
        // Construction de la Map associant stationNumber et liste de PIM
        Map<String, List<PersonInfoModel>> listFamilles = new HashMap<>();
        listFamilles.put("1", famille1);
        listFamilles.put("2", famille2);

        // Construction FloodModel
        FloodModel floodModelResult = new FloodModel();
        floodModelResult.setListFamille(listFamilles);

        when(personService.getFlood(stationNumberList)).thenReturn(floodModelResult);

        log.info("Liste des foyers: {} ", floodModelResult );

        mockMvc.perform(get("/flood/stations")
                        .param("stationNumber", "1", "2")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();
    }

    @Test
    void testGetPersonInfoModelSuccess() throws Exception {
        String lastName = "Stran";
        String firstName = "Simone";
        List<PersonInfoModel> personInfofList = Arrays.asList(
                new PersonInfoModel("Simone", "Stran", "1509 Culver St", 30L, "mail@safety.com", List.of("Paracétamol"), List.of("Fruits rouges")),
                new PersonInfoModel("Simon", "Stran", "1509 Culver St", 40L, "mail1@safety.com", List.of("Paracétamol", "Coumadine"), List.of("Cacahuètes")));
        when(personService.getPersonInfo(lastName, firstName)).thenReturn(personInfofList);

        log.info("Liste: {} ", personInfofList);

        mockMvc.perform(get("/personInfo")
                        .param("lastName", lastName)
                        .param("firstName", firstName)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testGetCommunityEmailSuccess() throws Exception {
       String city = "Paris";
       List<String> emailList = Arrays.asList(
               "email1@safety.com",
               "email2@safety.com");
       when(personService.getCommunityEmail(city)).thenReturn(emailList);

        log.info("Liste d'email: {} ", emailList);

        mockMvc.perform(get("/communityEmail")
                        .param("city", city)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(jsonPath("$.length()").value(2))
                        .andExpect(status().isOk())
                        .andReturn();

    }

}
