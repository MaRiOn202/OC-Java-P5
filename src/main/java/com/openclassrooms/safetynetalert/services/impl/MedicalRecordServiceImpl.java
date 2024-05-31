package com.openclassrooms.safetynetalert.services.impl;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Autowired
    public SerializationDriver serializationDriver;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecordEntity> listMedicalRecord = new ArrayList<>();


    @Override
    public List<MedicalRecordEntity> getMedicalRecordList() {
        return medicalRecordRepository.getMedicalRecordList();
    }

    public void addMedicalRecord(MedicalRecordEntity medicalRecordEntity) {
        medicalRecordRepository.addMedicalRecord(medicalRecordEntity);
    }
    

    public MedicalRecordEntity findByLastNameAndFirstName(String lastName, String firstName) {
        return medicalRecordRepository.findByLastNameAndFirstName(lastName, firstName );
    }

 //  public MedicalRecord findAll() {




/*    public MedicalRecordEntity removeMedicalRecord(MedicalRecordEntity medicalRecord) {
        return medicalRecordRepository.removeMedicalRecord(medicalRecord);
    }*/

}
