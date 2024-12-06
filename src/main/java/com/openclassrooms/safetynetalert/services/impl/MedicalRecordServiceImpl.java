package com.openclassrooms.safetynetalert.services.impl;

import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.exception.MedicalRecordNotFoundException;
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
    

    @Override
    public MedicalRecordModel addMedicalRecord(MedicalRecordModel medicalRecordM) {
        MedicalRecordEntity medicalRecordEntity = medicalMapper.mapToMedicalRecordEntity(medicalRecordM);

        MedicalRecordEntity mre = medicalRecordRepository.addMedicalRecord(medicalRecordEntity);

        MedicalRecordModel medicalRecordModel = medicalMapper.mapToMedicalRecordModel(mre);

        log.info("Le carnet de santé a bien été créé !" );
        return medicalRecordModel;
    }

    @Override
    public MedicalRecordModel updateMedicalRecord(MedicalRecordModel medicalRecordModel) {
        // Générer une exception
        try {
        MedicalRecordEntity existingMedicalRecord = medicalRecordRepository.findByLastName(medicalRecordModel.getLastName());

        if (existingMedicalRecord == null) {
            throw new MedicalRecordNotFoundException("Aucun carnet de santé n'a été trouvé à ce nom : " + medicalRecordModel.getLastName());
        }

        MedicalRecordEntity medicalUpdate = medicalMapper.mapToMedicalRecordEntity(medicalRecordModel);
        medicalUpdate = medicalRecordRepository.updateMedicalRecord(medicalUpdate);
        MedicalRecordModel medicalReturn = medicalMapper.mapToMedicalRecordModel(medicalUpdate);
        log.info("Le carnet de santé a bien été modifié !");
        return medicalReturn;

    } catch (MedicalRecordNotFoundException e) {
        log.error("Une erreur s'est produite lors de la mise à jour : " + e);
        throw e;
    }
}

    
    @Override
    public Boolean deleteMedicalRecord(String lastName, String firstName) {
        final boolean medicalRecordDeleted =
                medicalRecordRepository.deleteMedicalRecord(lastName, firstName);
        if(medicalRecordDeleted) {
            log.info("Le carnet de santé de " + lastName + " " + firstName + " a bien été supprimé");
        } else {
            log.info("Le carnet de santé de " + lastName + " " + firstName + " n'a pas été supprimé");
        }
        return medicalRecordDeleted;
    }
}
