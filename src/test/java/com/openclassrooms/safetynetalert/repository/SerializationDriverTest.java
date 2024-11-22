package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.exception.FileNotReadException;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class SerializationDriverTest {
    SerializationDriver serializationDriver;

    @BeforeEach
    void init() throws FileNotReadException {
        serializationDriver = new SerializationDriver();

    }


    @Test
    public void testSerializationDriver() throws FileNotReadException {

      /*  String input = "{\"name\":{\"firstName\":\"Joe\"}}";
        Any any = JsonIterator.deserialize(input);
        assertThat(any.toString("name", "firstName")).isEqualTo("Joe");*/
        
        serializationDriver.deserialization();


    }



}
