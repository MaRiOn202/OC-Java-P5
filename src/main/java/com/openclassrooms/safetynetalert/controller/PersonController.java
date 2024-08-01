package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.ChildModel;
import com.openclassrooms.safetynetalert.model.FloodModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.services.PersonService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PersonController {

    @Autowired
    private PersonService personService;

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);


    @PostMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonModel addPerson(@NotNull @RequestBody PersonModel personModel) {
       // this.personService.addPerson(personModel);
        return personService.addPerson(personModel);
/*        return ResponseEntity.status(HttpStatus.OK)
                .body(person.getLastName() + " "
                        + person.getFirstName() + " a bien été créée. ");*/
    }

    @PutMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonModel updatePerson(@NotNull
            @RequestParam(name = "lastname", required = true) String lastName,
            @RequestParam(name = "firstName", required = true) String firstName,
            @RequestBody PersonModel personModel) {
        ///
        return personService.updatePerson(lastName, firstName, personModel);
    }
    
    @DeleteMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deletePerson(@RequestParam(name = "lastName", required = true) String lastName,
                               @RequestParam(name = "firstName", required = true) String firstName) {
        
        log.info("URL : http://localhost:8080/person?lastName="+lastName+"&firstName"+firstName);
         this.personService.deletePerson(lastName,firstName);
         return personService.deletePerson(lastName, firstName);
/*        return ResponseEntity.status(HttpStatus.OK).body(lastName + " "
        + firstName + " a bien été supprimé. ");*/
    }


    
    /////////////////////////////////////////////////////////////////////////////////////////
    // URL n°2
    @GetMapping(value = "/childAlert", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<ChildModel> getChildAlert(@RequestParam (name = "address", required = true)
    String address) {

        log.info("URL : http://localhost:8080/childAlert?address="+address);
        return personService.getChildAlert(address);
    }

     // URL n°5 : 
     @GetMapping(value = "/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE )
     public FloodModel getFlood(@NotNull @RequestParam (name = "stationNumber",
             required = true) List<String> stationNumber) {

         log.info("URL : http://localhost:8080/flood/stations?stations="+stationNumber);
         return personService.getFlood(stationNumber);
     }
     

     // URL n°6
    @GetMapping(value = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<PersonInfoModel>> getPersonInfo(@NotNull @RequestParam (name = "lastName", required = true) String lastName,
                                                               @RequestParam(name = "firstName", required = true) String firstName) {

        log.info("URL : http://localhost:8080/personInfo?lastName="+lastName+"&firstName"+firstName);
        return ResponseEntity.status(HttpStatus.OK).body(personService
                .getPersonInfo(lastName, firstName));
    }

    // URL n°7
    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<String> getCommunityEmail(@NotNull @RequestParam(name = "city", required = true) String city) {

        log.info("URL : http://localhost:8080/communityEmail?city="+city);
        return personService.getCommunityEmail(city);
    }

}
