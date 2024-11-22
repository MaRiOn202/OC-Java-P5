package com.openclassrooms.safetynetalert.mapper;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.model.MedicalRecordModel;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    public MedicalRecordEntity mapToMedicalRecordEntity(MedicalRecordModel medicalRecordModel) {
        MedicalRecordEntity medicalRecordEntity  = new MedicalRecordEntity();
        medicalRecordEntity.setLastName(medicalRecordModel.getLastName());
        medicalRecordEntity.setFirstName(medicalRecordModel.getFirstName());
        medicalRecordEntity.setBirthdate(medicalRecordModel.getBirthdate());
        medicalRecordEntity.setMedications(medicalRecordModel.getMedications());
        medicalRecordEntity.setAllergies(medicalRecordModel.getAllergies());
        return medicalRecordEntity;
    }

    public MedicalRecordModel mapToMedicalRecordModel(MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordModel medicalRecordModel  = new MedicalRecordModel();
        medicalRecordModel.setLastName(medicalRecordEntity.getLastName());
        medicalRecordModel.setFirstName(medicalRecordEntity.getFirstName());
        medicalRecordModel.setBirthdate(medicalRecordEntity.getBirthdate());
        medicalRecordModel.setMedications(medicalRecordEntity.getMedications());
        medicalRecordModel.setAllergies(medicalRecordEntity.getAllergies());
        return medicalRecordModel;
    }


}
