package com.openclassrooms.safetynetalert.services.impl;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.mapper.MedicalRecordMapper;
import com.openclassrooms.safetynetalert.model.MedicalRecordModel;
import com.openclassrooms.safetynetalert.services.MedicalRecordService;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;
    
    @Autowired
    private final MedicalRecordMapper medicalMapper;

    private static final Logger log = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

/*    @Override
    public List<MedicalRecordEntity> getMedicalRecordList() {

        return medicalRecordRepository.getMedicalRecordList();
    }*/

    @Override
    public MedicalRecordModel addMedicalRecord(MedicalRecordModel medicalRecordM) {
        MedicalRecordEntity medicalRecordEntity  = new MedicalRecordEntity();
/*        medicalRecordEntity.setLastName(medicalRecordM.getLastName());
        medicalRecordEntity.setFirstName(medicalRecordM.getFirstName());
        medicalRecordEntity.setBirthdate(medicalRecordM.getBirthdate());
        medicalRecordEntity.setMedications(medicalRecordM.getMedications());
        medicalRecordEntity.setAllergies(medicalRecordM.getAllergies());*/    // Model en Entity
        medicalMapper.mapToMedicalRecordEntity(medicalRecordM);

        MedicalRecordEntity mre = medicalRecordRepository.addMedicalRecord(medicalRecordEntity);
        MedicalRecordModel medicalRecordModel = new MedicalRecordModel();
        medicalRecordModel.setLastName(mre.getLastName());
        medicalRecordModel.setFirstName(mre.getFirstName());
        medicalRecordModel.setBirthdate(mre.getBirthdate());
        medicalRecordModel.setMedications(mre.getMedications());
        medicalRecordModel.setAllergies(mre.getAllergies());
        // medicalMapper.mapToMedicalRecordModel(mre);

        return medicalRecordModel;
    }

    @Override
    public MedicalRecordModel updateMedicalRecord(MedicalRecordModel medicalRecordModel) {
        // à faire
        MedicalRecordEntity medicalUpdate = medicalRecordRepository.findByLastNameAndFirstName
                (medicalRecordModel.getLastName(), medicalRecordModel.getFirstName());
        medicalUpdate.setLastName(medicalRecordModel.getLastName());
        medicalUpdate.setFirstName(medicalRecordModel.getFirstName());
        medicalUpdate.setBirthdate(medicalRecordModel.getBirthdate());
        medicalUpdate.setMedications(medicalRecordModel.getMedications());
        medicalUpdate.setAllergies(medicalRecordModel.getAllergies());
        medicalUpdate = medicalRecordRepository.updateMedicalRecord(medicalUpdate);
        MedicalRecordModel medicalReturn = medicalMapper.mapToMedicalRecordModel(medicalUpdate);
        return medicalReturn;
    }

    
    @Override
    public Boolean deleteMedicalRecord(String lastName, String firstName) {
        final boolean medicalRecordDeleted =
                medicalRecordRepository.deleteMedicalRecord(lastName, firstName);
        if(medicalRecordDeleted) {
            //slf4j log à utiliser // log.info
            log.info("Le carnet de santé de " + lastName + " " + firstName + " a bien été supprimé");
        } else {
            log.info("Le carnet de santé de " + lastName + " " + firstName + " n'a pas été supprimée");
        }
        return medicalRecordDeleted;
    }



}
