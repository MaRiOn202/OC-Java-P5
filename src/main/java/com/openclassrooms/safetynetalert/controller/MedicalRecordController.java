package com.openclassrooms.safetynetalert.controller;


import com.openclassrooms.safetynetalert.exception.MedicalRecordNotFoundException;
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
    public ResponseEntity<MedicalRecordModel> addMedicalRecord(@NotNull @RequestBody MedicalRecordModel medicalRecordModel) {
        log.info("URL : http://localhost:8080/medicalRecord?medicalRecordModel="+medicalRecordModel);
        medicalRecordService.addMedicalRecord(medicalRecordModel);
        return new ResponseEntity<>(medicalRecordModel, HttpStatus.CREATED);
    }

    @PutMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<MedicalRecordModel> updateMedicalRecord(
            @NotNull
            @RequestBody MedicalRecordModel medicalRecordModel) throws MedicalRecordNotFoundException {

        log.info("URL : http://localhost:8080/medicalRecord?medicalRecordModel="+medicalRecordModel);
        medicalRecordService.updateMedicalRecord(medicalRecordModel);
        return new ResponseEntity<>(medicalRecordModel, HttpStatus.OK);
    }

    
    @DeleteMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Boolean> deleteMedicalRecord(@RequestParam(name = "lastName") String lastName,
                                       @RequestParam(name = "firstName") String firstName) {
        log.info("URL : http://localhost:8080/medicalRecord?lastName="+lastName+"&firstName"+firstName);
        medicalRecordService.deleteMedicalRecord(lastName, firstName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
