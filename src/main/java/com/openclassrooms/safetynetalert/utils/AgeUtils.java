package com.openclassrooms.safetynetalert.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class AgeUtils {

    public long getAge(String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        long age = 0;
        try {
            log.info("birthdate {}", birthdate);
            // Convertir la chaîne de caractères en LocalDate
            LocalDateTime birthdayTime = LocalDate.parse(birthdate, formatter).atStartOfDay();
            // Obtenir la date actuelle
            LocalDateTime currentTime = LocalDateTime.now();
            log.info("currentTime {}", currentTime);
            log.info("birthdayTime {}", birthdayTime);
            Duration resultDuration =  Duration.between(birthdayTime, currentTime);

            age = resultDuration.toDays()/365;

        } catch (DateTimeParseException ex){
            log.error("Error : {}", ex);
        }

        return age;
    }

    // Calcule si une personne est mineure ou pas sous forme de booléen
    public boolean getMinor(String birthdate)  {

        if(getAge(birthdate) <= 18 ) {
            return true;
        }
        return false;
    }

}