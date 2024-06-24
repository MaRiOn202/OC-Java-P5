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
@AllArgsConstructor                              //@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);


/*    @PostMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<PersonModel> addPerson(@NotNull @RequestBody PersonModel person) {
        this.personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.OK)
                .body(person.getLastName() + " "
                + person.getFirstName() + " a bien été créée. " );
    }*/


    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<PersonEntity> findByLastNameAndFirstName(@RequestParam(name = "lastName", required = true) String lastName,
                                                                   @RequestParam(name = "firstName", required = true) String firstName) {

        log.info("URL : http://localhost:8080/person");
        return ResponseEntity.status(HttpStatus.OK).body(personService
                .findByLastNameAndFirstName(lastName,firstName));
    }

    
    @DeleteMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePerson(@RequestParam(name = "lastName", required = true) String lastName,
                                                @RequestParam(name = "firstName", required = true) String firstName) {
        
        log.info("URL : http://localhost:8080/person?lastName="+lastName+"&firstName"+firstName);
         this.personService.deletePerson(lastName,firstName);
        return ResponseEntity.status(HttpStatus.OK).body(lastName + " "
        + firstName + " a bien été supprimé. ");
    }
     // Pourquoi String plutôt que Boolean ? 
     // CRUD : absence de UPDATE dans les repository


    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<ChildModel>> getChildAlert(@RequestParam (name = "address", required = true)
    String address) {

        log.info("URL : http://localhost:8080/childAlert?address="+address);
        return ResponseEntity.status(HttpStatus.OK).body(personService
                .getChildAlert(address));
    }
     // /person quand même ? ou comme doc /get ou childAlert ? l1
    // dois-je changer le nom des méthodes ? pour coller au DCN ?
/*     @GetMapping(value = "/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE )
     public ResponseEntity<Map<String,List<FloodModel>>> getFlood(@NotNull @RequestParam (name = "stationNumber",
             required = true) List<String> stationNumber) {

         log.info("URL : http://localhost:8080/flood/stations?stations="+stationNumber);
         return ResponseEntity.status(HttpStatus.OK).body(personService
                 .getFlood(stationNumber));
     }*/
     

    @GetMapping(value = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<PersonInfoModel>> getPersonInfo(@NotNull @RequestParam (name = "lastName", required = true) String lastName,
                                                               @RequestParam(name = "firstName", required = true) String firstName) {

        log.info("URL : http://localhost:8080/personInfo?lastName="+lastName+"&firstName"+firstName);
        return ResponseEntity.status(HttpStatus.OK).body(personService
                .getPersonInfo(lastName, firstName));
    }


    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<String>> getCommunityEmail(@NotNull @RequestParam (name = "city", required = true) String city) {

        log.info("URL : http://localhost:8080/communityEmail?city="+city);
        return ResponseEntity.status(HttpStatus.OK).body(personService
                .getCommunityEmail(city));
    }

}
