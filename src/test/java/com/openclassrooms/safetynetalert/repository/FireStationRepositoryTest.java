package com.openclassrooms.safetynetalert.repository;


import com.openclassrooms.safetynetalert.entity.FireStationEntity;
import com.openclassrooms.safetynetalert.exception.FileNotReadException;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FireStationRepositoryTest {

    private FireStationRepository fireStationRepository;

    Logger log = LoggerFactory.getLogger(PersonRepository.class);

    @BeforeEach
    void init() throws FileNotReadException {
        SerializationDriver serializationDriver = new SerializationDriver();
        fireStationRepository = new FireStationRepository(serializationDriver);
    }

    @Test
    void testFireStationRepositoryAddFireStation() {
        FireStationEntity fireStation = new FireStationEntity();
        fireStation.setStation("5");
        fireStationRepository.addFireStation(fireStation);
        List<FireStationEntity> listFireStation = fireStationRepository.getFireStationList();
        log.info("Résultat : {}", listFireStation);

        assertNotNull(listFireStation);
        assertNotNull(fireStation.getStation());
        assertTrue(listFireStation.stream().anyMatch(fireStation1 ->
                        fireStation1.getStation().equalsIgnoreCase("5")), "");
    }

    @Test
    void testFireStationRepositoryRemoveFireStation() {

      Boolean result = fireStationRepository.deleteFireStation("1509 Culver St");
      List<FireStationEntity> listFireStation = fireStationRepository.getFireStationList();
      log.info("Résultat : {} {}", result, listFireStation);

      assertTrue(result, "Le test a échoué");
      assertThat(listFireStation.size()).isEqualTo(12);
        
    }

    @Test
    void testFireStationRepositoryFindByStation() {
        List<FireStationEntity> fireStationResult = fireStationRepository.findByStation("3");
        List<FireStationEntity> listFireStation = fireStationRepository.findByStation("3");
        log.info("Résultat : {} {}", listFireStation, fireStationResult);

        assertEquals("3", listFireStation.get(0).getStation());
        assertThat(listFireStation.size()).isEqualTo(5);
    }

    @Test
    void testFireStationRepositoryFindByAddress() {

        FireStationEntity fireStation = fireStationRepository.findByAddress("1509 Culver St");
        log.info("Résultat : {}", fireStation);

        assertEquals("1509 Culver St", fireStation.getAddress());

    }
    
}
