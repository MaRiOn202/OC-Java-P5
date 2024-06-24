package com.openclassrooms.safetynetalert.services;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MedicalRecordService {

    public List<MedicalRecordEntity> getMedicalRecordList();
     public void addMedicalRecord(MedicalRecordEntity medicalRecordEntity);

    public MedicalRecordEntity findByLastNameAndFirstName(String lastName, String firstName);

    public Boolean deleteMedicalRecord(String lastName, String firstName);


}

