package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class SerializationDriverTest {
    SerializationDriver serializationDriver;

    @BeforeEach
    void init() throws IOException {
        serializationDriver = new SerializationDriver();

    }


    @Test
    public void testSerializationDriver() throws IOException {

      /*  String input = "{\"name\":{\"firstName\":\"Joe\"}}";
        Any any = JsonIterator.deserialize(input);
        assertThat(any.toString("name", "firstName")).isEqualTo("Joe");*/
        
        serializationDriver.deserialization();


    }



}
