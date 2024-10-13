package com.openclassrooms.safetynetalert.utils;


import com.openclassrooms.safetynetalert.entity.SafetyAlert;
import com.openclassrooms.safetynetalert.exception.FileNotReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.jsoniter.JsonIterator.deserialize;


@Component
public class SerializationDriver {

           Logger log = LoggerFactory.getLogger(SerializationDriver.class);

           public final SafetyAlert safetyAlert;

    public SerializationDriver() throws FileNotReadException {
        this.safetyAlert = deserialization();
    }

    public SafetyAlert deserialization() throws FileNotReadException {

        try {
            Resource resources = new ClassPathResource("fichierdedonnees.json");
            FileInputStream fileInputStream = new FileInputStream(resources.getFile());
            SafetyAlert safetyAlert = deserialize(fileInputStream.readAllBytes(), SafetyAlert.class);

            log.info("Résultat {}", safetyAlert );
            return safetyAlert;

        } catch (IOException e) {

            throw new FileNotReadException(e);
        }

    }

}
