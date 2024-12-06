package com.openclassrooms.safetynetalert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<String> handlerMedicalRecordNotFoundException(MedicalRecordNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FireStationNotFoundException.class)
    public ResponseEntity<String> handlerFireStationNotFoundException(FireStationNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
