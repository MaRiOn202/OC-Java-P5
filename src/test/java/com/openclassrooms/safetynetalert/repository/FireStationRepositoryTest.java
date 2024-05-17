package com.openclassrooms.safetynetalert.repository;


import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FireStationRepositoryTest {

    FireStationRepository fireStationRepository;

    private FireStation fireStation;
    Logger log = LoggerFactory.getLogger(PersonRepository.class);

    @BeforeEach
    void init() throws IOException {
        SerializationDriver serializationDriver = new SerializationDriver();
        fireStationRepository = new FireStationRepository(serializationDriver);
    }

    @Test
    void testFireStationRepositoryAddFireStation() {
        FireStation fireStation = new FireStation();
        fireStation.setStation("5");
        fireStationRepository.addFireStation(fireStation);
        List<FireStation> listFireStation = fireStationRepository.getFireStationList();
        log.info("Résultat : {}", listFireStation);

        assertNotNull(listFireStation);
        assertNotNull(fireStation.getStation());
        assertTrue(listFireStation.stream().anyMatch(fireStation1 ->
                        fireStation1.getStation().equalsIgnoreCase("5")), "");
    }

    @Test
    void testFireStationRepositoryRemoveFireStation() {

      Boolean result = fireStationRepository.removeFireStation("1509 Culver St");
      List<FireStation> listFireStation = fireStationRepository.getFireStationList();
      log.info("Résultat : {} {}", result, listFireStation);

      assertTrue(result, "Le test a échoué");
      assertThat(listFireStation.size()).isEqualTo(12);
        
    }

    @Test
    void testFireStationRepositoryFindByStation() {
        List<FireStation> fireStationResult = fireStationRepository.findByStation("3");
        List<FireStation> listFireStation = fireStationRepository.findByStation("3");
        log.info("Résultat : {} {}", listFireStation, fireStationResult);

        assertEquals("3", listFireStation.get(0).getStation());
        assertThat(listFireStation.size()).isEqualTo(5);
    }

    @Test
    void testFireStationRepositoryFindByAddress() {

        fireStation = fireStationRepository.findByAddress("1509 Culver St");
        log.info("Résultat : {}", fireStation);

        assertEquals("1509 Culver St", fireStation.getAddress());

    }
    
}
