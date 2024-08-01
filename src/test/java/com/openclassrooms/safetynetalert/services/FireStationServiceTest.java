/*
package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.model.FireStationModel;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.services.impl.FireStationImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @InjectMocks
    private FireStationImpl fireStationImpl;

    @Mock
    FireStationRepository fireStationRepository;

    private static List<FireStationModel> listFireStation;

    @BeforeEach
    public void setUp() {

        listFireStation = new ArrayList<>();

        // Création de deux fireStation
        FireStationModel fireStation1 = new FireStationModel();
        FireStationModel fireStation2 = new FireStationModel();

        fireStation1.setStation("station1");
        fireStation1.setAddress("address1");

        fireStation2.setStation("station2");
        fireStation2.setAddress("address2");

        listFireStation.add(fireStation1);
        listFireStation.add(fireStation2);

    }

*/
/*    @Test
    void testAddFireStation() {

        FireStationModel fireStationModel = new FireStationModel();
        fireStationModel.setAddress("dfdfrg");
        fireStationModel.setStation("6");


    }*//*


*/
/*    @Test
    void testFindByAddress() {

      FireStationEntity newFireStation = new FireStationEntity();
      newFireStation.setAddress("NewAddress");

      when(fireStationRepository.findByAddress(newFireStation.getAddress())).thenReturn(new FireStationEntity());
      when(fireStationRepository.addFireStation(newFireStation)).thenReturn(listFireStation.add(newFireStation));
      assertEquals("NewAddress", newFireStation.getAddress());


    }*//*




}
*/
