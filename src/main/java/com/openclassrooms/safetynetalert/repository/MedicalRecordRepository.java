package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.utils.SerializationDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;

@Repository
public class MedicalRecordRepository {

    private List<MedicalRecordEntity> listMedicalRecord = new ArrayList<>();

    public MedicalRecordRepository( SerializationDriver serializationDriver) {
        this.serializationDriver = serializationDriver;
        this.listMedicalRecord = getMedicalRecordList();
    }

    @Autowired
    public SerializationDriver serializationDriver;
    
    public List<MedicalRecordEntity> getMedicalRecordList() {
        return serializationDriver.safetyAlert.getMedicalRecords();
    }

    
    public MedicalRecordEntity addMedicalRecord(MedicalRecordEntity medicalRecordEntity) {
        this.listMedicalRecord.add(medicalRecordEntity);
        return medicalRecordEntity;
    }

    // Laisser comme ça 
    public MedicalRecordEntity updateMedicalRecord(MedicalRecordEntity medicalUpdate) {
        listMedicalRecord = getMedicalRecordList();
        MedicalRecordEntity medicalUpdated = new MedicalRecordEntity();
        for (MedicalRecordEntity medicalRecordEntity : listMedicalRecord) {
            if (medicalRecordEntity.getLastName().equalsIgnoreCase(medicalUpdate.getLastName())
            && medicalRecordEntity.getFirstName().equalsIgnoreCase(medicalUpdate.getFirstName())) {
                medicalRecordEntity.setBirthdate(medicalUpdate.getBirthdate());
                medicalRecordEntity.setMedications(medicalUpdate.getMedications());
                medicalRecordEntity.setAllergies(medicalUpdate.getAllergies());
            }
              return medicalUpdate;
        }
        return medicalUpdated;
    }

    public MedicalRecordEntity findByLastNameAndFirstName(String lastName, String firstName) {
        listMedicalRecord = getMedicalRecordList();
       for (MedicalRecordEntity medicalRecord : listMedicalRecord) {
           if (medicalRecord.getLastName().equals(lastName) &&
                   medicalRecord.getFirstName().equals(firstName))
               return medicalRecord;
       }
       return null;              // cours optional.empty https://medium.com/@JeremieGottero/optional-en-java-quand-lutiliser-et-quand-ne-pas-l-utiliser-9bc759e162cd
    }


/*    public MedicalRecord findAll(String firstName, String lastName, String birthdate, List<String> medications,
                                 List<String> allergies) {
       for (MedicalRecord medicalRecord : listMedicalRecord) {
           if (medicalRecord.getLastName().equals(lastName))
               return medicalRecord;
       }
       return new MedicalRecord();
    }*/

    public Boolean deleteMedicalRecord(String lastName, String firstName) {
        listMedicalRecord = getMedicalRecordList();
        for ( MedicalRecordEntity medicalRecord : listMedicalRecord) {
           if (medicalRecord.getLastName().equals(lastName) &&
           medicalRecord.getFirstName().equals(firstName))
               return listMedicalRecord.remove(medicalRecord);     //true
        }
        return false;
    }


    public MedicalRecordEntity findByLastName(String lastName) {
        listMedicalRecord = getMedicalRecordList();
        for(MedicalRecordEntity medicalRecordEntity : listMedicalRecord) {
            if(medicalRecordEntity.getLastName().equals(lastName)) {
                return medicalRecordEntity;
            }
        }
        return null;
    }
    
}
