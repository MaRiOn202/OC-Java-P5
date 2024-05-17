/*
package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService {


    @Autowired
    public SerializationDriver serializationDriver;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> listMedicalRecord = new ArrayList<>();

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.addMedicalRecord(medicalRecord);
    }

    public MedicalRecord findByLastName(String lastName, String firstName) {
        return medicalRecordRepository.findByLastName(lastName, firstName );
    }

*/
/*    public MedicalRecord findAll() {

    }*//*


    public MedicalRecord removeMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.removeMedicalRecord(medicalRecord);
    }























}
*/
