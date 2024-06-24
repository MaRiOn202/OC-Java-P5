package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.FireModel;
import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.services.FireStationService;
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
    private FireStationService fireStationService;

    private static final Logger log = LoggerFactory.getLogger(FireStationController.class);


/*    @PostMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<FireStationModel> addFireStation(@NotNull @RequestBody FireStationModel fireStationModel) {
        this.fireStationService.addFireStation(fireStationModel);
        return ResponseEntity.status(HttpStatus.OK)
                .body("La station n°"+ fireStationModel.getStation()
                + " située au "
                + fireStationModel.getAddress() + " a bien été créée. " );

                //   FireStationModel n'est pas encore créee besoin
                //pour addFireStation
    }*/


    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<FireStationEntity>> findByStation(
            @RequestParam(name = "station", required = true)
                                                                     String station) {
        log.info("URL : http://localhost:8080/firestation?station");
        return ResponseEntity.status(HttpStatus.OK).body(fireStationService
                .findByStation(station));

    }

    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<FireStationEntity> findByAddress(@RequestParam(name = "address", required = true)
                                                                 String address) {
        log.info("URL : http://localhost:8080/firestation?address");
        return ResponseEntity.status(HttpStatus.OK).body((FireStationEntity) fireStationService
                .findByStation(address));

    }    //cast ?

    @DeleteMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> deleteFireStation(@RequestParam(name = "address", required = true) String address) {

        log.info("URL : http://localhost:8080/firestation?address");
        this.fireStationService.deleteFireStation(address);
        return ResponseEntity.status(HttpStatus.OK).body("La caserne se situant au " + address + " a bien été supprimée. " );
    }
    //pourquoi pas Boolean ?

//////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<String>> getPhoneAlert(@NotNull @RequestParam (name = "stationNumber",
            required = true) String stationNumber) {

        log.info("URL : http://localhost:8080/phoneAlert?firestation"+stationNumber);
        return ResponseEntity.status(HttpStatus.OK).body(fireStationService.getPhoneAlert(stationNumber));
    }


    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<FireModel> getFireMembersAddress(@NotNull @RequestParam (name = "address",
            required = true) String address) {

        log.info("URL : http://localhost:8080/fire?address="+address);
        return ResponseEntity.status(HttpStatus.OK).body(fireStationService.getFireMembersAddress(address));
    }

}





