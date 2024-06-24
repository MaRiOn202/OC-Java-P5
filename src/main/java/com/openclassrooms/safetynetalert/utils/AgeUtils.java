package com.openclassrooms.safetynetalert.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;

@Component
public class AgeUtils {

    public long getAge(String birthdate) {

        LocalDate currentTime = LocalDate.now();
        LocalDate birthdayTime = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(birthdate) );
        Duration result =  Duration.between(currentTime,birthdayTime);

        long age = result.toDays()/365;

        return age;
    }

    //  calcule si une personne est mineure ou pas sous forme de booléen
    public boolean getMinor(String birthdate) {

        LocalDate currentTime = LocalDate.now();
        LocalDate birthdayTime = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(birthdate) );
        Duration result =  Duration.between(currentTime,birthdayTime);

        if(result.toDays()/365 <= 18 ) {
            return true;
        }
        return false;
    }

}
