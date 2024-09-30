package com.openclassrooms.safetynetalert.mapper;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.FireMembersModel;
import com.openclassrooms.safetynetalert.utils.AgeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FireStationMapper {

    @Autowired
    private final AgeUtils age;

    public FireStationMapper(AgeUtils age) {
        this.age = age;
    }

    public FireMembersModel mapToFireMembersModel(long age,PersonEntity personEntity, MedicalRecordEntity medicalRecordEntity) {
        FireMembersModel fireMembersModel = new FireMembersModel();
        fireMembersModel.setFirstname(personEntity.getFirstName());
        fireMembersModel.setLastname(personEntity.getLastName());
        fireMembersModel.setPhone(personEntity.getPhone());
        //TODO Calculer l'âge avant de set 
        fireMembersModel.setAge(age);
        fireMembersModel.setMedications(medicalRecordEntity.getMedications());
        fireMembersModel.setAllergies(medicalRecordEntity.getAllergies());
        return fireMembersModel;
    }
}
