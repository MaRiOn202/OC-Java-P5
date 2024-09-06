package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.model.MedicalRecordModel;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger log = LoggerFactory.getLogger(MedicalRecordController.class);


    @PostMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE )
    public MedicalRecordModel addMedicalRecord(@NotNull @RequestParam MedicalRecordModel medicalRecordModel) {
        log.info("URL : http://localhost:8080/medicalRecord");
    /*    return ResponseEntity.status(HttpStatus.OK).body("Le carnet de santé de "+ medicalRecord.getLastName()
                + " "
                + medicalRecord.getFirstName() + " a bien été créé. " );*/
        return medicalRecordService.addMedicalRecord(medicalRecordModel);
    }

    @PutMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE )
    public MedicalRecordModel updateMedicalRecord(
            @NotNull
            @RequestParam(name = "address", required = true) MedicalRecordModel medicalRecordModel) {

        return medicalRecordService.updateMedicalRecord(medicalRecordModel);
    }
    
    @DeleteMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE )
    public Boolean deleteMedicalRecord(@RequestParam(name = "lastName", required = true) String lastName,
                                       @RequestParam(name = "firstName", required = true) String firstName) {

        log.info("URL : http://localhost:8080/medicalRecord?lastName="+lastName+"&firstName"+firstName);
        //return ResponseEntity.status(HttpStatus.OK).body("LLe carnet de santé de " + lastName + " " + firstName + " a bien été supprimée. " );
        return medicalRecordService.deleteMedicalRecord(lastName, firstName);
    }


/*    @GetMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE )
    public MedicalRecordEntity findByLastNameAndFirstName
            (@RequestParam(name = "lastName", required = true) String lastName,
             @RequestParam(name = "firstName", required = true) String firstName) {

        log.info("URL : http://localhost:8080/medicalRecord?lastName="+lastName+"&firstName"+firstName);
  *//*      return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService
                .findByLastNameAndFirstName(lastName,firstName));*//*
        return medicalRecordService.findByLastNameAndFirstName(lastName, firstName);
    }*/


}
