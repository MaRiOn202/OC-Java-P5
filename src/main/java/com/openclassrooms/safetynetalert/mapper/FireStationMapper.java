package com.openclassrooms.safetynetalert.mapper;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.FireMembersModel;

public class FireStationMapper {

    public FireMembersModel mapToFireMembersModel(PersonEntity personEntity, MedicalRecordEntity medicalRecordEntity) {
        FireMembersModel fireMembersModel = new FireMembersModel();
        fireMembersModel.setFirstname(personEntity.getFirstName());
        fireMembersModel.setLastname(personEntity.getLastName());
        fireMembersModel.setPhone(personEntity.getPhone());
        fireMembersModel.setAge(Long.parseLong(medicalRecordEntity.getBirthdate()));
        fireMembersModel.setMedications(medicalRecordEntity.getMedications());
        fireMembersModel.setAllergies(medicalRecordEntity.getAllergies());
        return fireMembersModel;
    }
}
