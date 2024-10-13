package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.model.FireModel;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.model.PersonFireStationModel;
import com.openclassrooms.safetynetalert.services.FireStationService;
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

@RestController
@AllArgsConstructor
public class FireStationController {


    @Autowired
    private final FireStationService fireStationService;

    @Autowired
    private final PersonService personService;

    private static final Logger log = LoggerFactory.getLogger(FireStationController.class);


    @PostMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<FireStationModel> addFireStation(@NotNull @RequestBody FireStationModel fireStationModel) {
        log.info("URL : http://localhost:8080/firestation?fireStationModel="+fireStationModel);
        fireStationService.addFireStation(fireStationModel);
       return new ResponseEntity<>(fireStationModel, HttpStatus.CREATED);
               //ResponseEntity.status(HttpStatus.OK)
               // .body("La station n°"+ fireStationModel.getStation()
                //+ " située au "
                //+ fireStationModel.getAddress() + " a bien été créée. " );
    }

     @PutMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
     public ResponseEntity<FireStationModel> updateFireStation(@NotNull
             @RequestBody FireStationModel fireStationModel) {
     log.info("URL : http://localhost:8080/firestation?fireStationModel="+fireStationModel);
     fireStationService.updateFireStation(fireStationModel);

         return new ResponseEntity<>(fireStationModel, HttpStatus.OK);
     }

    @DeleteMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Boolean> deleteFireStation(
            @RequestParam(name = "address", required = true) String address) {

        log.info("URL : http://localhost:8080/firestation?address="+address);

       // return ResponseEntity.status(HttpStatus.OK).body("La caserne se situant au " + address + " a bien été supprimée. " );
        fireStationService.deleteFireStation(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//////////////////////////////////////////////////////////////////////////////////////////////

     @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
     public PersonFireStationModel getPersonCovertByFireStation(@RequestParam(name = "stationNumber")
                                                                  String stationNumber) {
         return fireStationService.getPersonCovertByFireStation(stationNumber);
     }

    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<String> getPhoneAlert(@NotNull @RequestParam (name = "stationNumber",
            required = true) String stationNumber) {
        log.info("URL : http://localhost:8080/phoneAlert?stationNumber="+stationNumber);
        return fireStationService.getPhoneAlert(stationNumber);
    }

    //  URL n°4
    @GetMapping(value = "/fire", produces = MediaType.APPLICATION_JSON_VALUE )
    public FireModel getFireMembersAddress(@NotNull @RequestParam (name = "address",
            required = true) String address) {

        log.info("URL : http://localhost:8080/fire?address="+address);
        return fireStationService.getFireMembersAddress(address);
    }

}





