package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;

@Repository
public class MedicalRecordRepository {

    private List<MedicalRecord> listMedicalRecord = new ArrayList<>();

    public MedicalRecordRepository( SerializationDriver serializationDriver) {
        this.serializationDriver = serializationDriver;
        this.listMedicalRecord = getMedicalRecordList();
    }

    @Autowired
    public SerializationDriver serializationDriver;
    
    public List<MedicalRecord> getMedicalRecordList() {

        return serializationDriver.safetyAlert.getMedicalRecords();
    }

    
    public void addMedicalRecord(MedicalRecord medicalRecord) {

        this.listMedicalRecord.add(medicalRecord);
    }

    public MedicalRecord findByLastNameAndFirstName(String lastName, String firstName) {
        listMedicalRecord = getMedicalRecordList();
       for (MedicalRecord medicalRecord : listMedicalRecord) {
           if (medicalRecord.getLastName().equals(lastName) &&
                   medicalRecord.getFirstName().equals(firstName))
               return medicalRecord;
       }
       return null;               //optional.empty
    }

/*    public MedicalRecord findAll(String firstName, String lastName, String birthdate, List<String> medications,
                                 List<String> allergies) {
       for (MedicalRecord medicalRecord : listMedicalRecord) {
           if (medicalRecord.getLastName().equals(lastName))
               return medicalRecord;
       }
       return new MedicalRecord();
    }*/

    public Boolean removeMedicalRecord(String lastName, String firstName) {
        listMedicalRecord = getMedicalRecordList();
        for ( MedicalRecord medicalRecord : listMedicalRecord) {
           if (medicalRecord.getLastName().equals(lastName) &&
           medicalRecord.getFirstName().equals(firstName))
               return listMedicalRecord.remove(medicalRecord);     //true
        }
        return false;
    }

}
