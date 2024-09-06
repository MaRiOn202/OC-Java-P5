package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.model.MedicalRecordModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MedicalRecordService {


    MedicalRecordModel addMedicalRecord(MedicalRecordModel medicalRecordModel);

    MedicalRecordModel updateMedicalRecord(MedicalRecordModel medicalRecordModel);

    Boolean deleteMedicalRecord(String lastName, String firstName);



}

