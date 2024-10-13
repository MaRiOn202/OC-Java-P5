package com.openclassrooms.safetynetalert.mapper;


import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.PersonCovertModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;
import com.openclassrooms.safetynetalert.utils.AgeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PersonMapper {

    @Autowired
    private final AgeUtils age;

    public PersonMapper(AgeUtils age) {
        this.age = age;
    }

    public PersonCovertModel mapToPersonCovertModel(PersonEntity personEntity) {
        PersonCovertModel personCovertModel = new PersonCovertModel();
        personCovertModel.setFirstName(personEntity.getFirstName());
        personCovertModel.setLastName(personEntity.getLastName());
        personCovertModel.setAddress(personEntity.getAddress());
        personCovertModel.setPhone(personEntity.getPhone());
        return personCovertModel;

    }

    public PersonInfoModel mapToPersonInfoModel(long age, PersonEntity personEntity,
                                                MedicalRecordEntity medicalRecordEntity) {
        PersonInfoModel personInfoModel = new PersonInfoModel();
        personInfoModel.setFirstName(personEntity.getFirstName());
        personInfoModel.setLastName(personEntity.getLastName());
        personInfoModel.setAddress(personEntity.getAddress());
        personInfoModel.setEmail(personEntity.getEmail());
        // TODO Calculer l'âge avant de set
        personInfoModel.setAge(age);
        //personInfoModel.setAge(age.getAge(String.valueOf(Long.parseLong(medicalRecordEntity.getBirthdate()))));
        personInfoModel.setMedications(medicalRecordEntity.getMedications());
        personInfoModel.setAllergies(medicalRecordEntity.getAllergies());
        return personInfoModel;
    }

    public PersonModel mapToPersonModel(PersonEntity personEntity) {
         PersonModel personModel = new PersonModel();
         personModel.setFirstName(personEntity.getFirstName());
         personModel.setLastName(personEntity.getLastName());
         personModel.setPhone(personEntity.getPhone());
         personModel.setAddress(personEntity.getAddress());
         personModel.setCity(personEntity.getCity());
         personModel.setZip(personEntity.getZip());
         personModel.setEmail(personEntity.getEmail());
        return personModel;
    }

    
    public PersonEntity mapToPersonEntity(PersonModel personModel) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setLastName(personModel.getLastName());
        personEntity.setFirstName(personModel.getFirstName());
        personEntity.setAddress(personModel.getAddress());
        personEntity.setCity(personModel.getCity());
        personEntity.setPhone(personModel.getPhone());
        personEntity.setEmail(personModel.getEmail());
        personEntity.setZip(personModel.getZip());
        return personEntity;
    }

    // à revoir
    public PersonInfoModel mapToPersonInfoModel(PersonModel personModel,
                                                long age, MedicalRecordEntity medicalRecordEntity) {
        PersonInfoModel personInfoModel = new PersonInfoModel();
        personInfoModel.setLastName(personModel.getLastName());
        personInfoModel.setFirstName(personModel.getFirstName());
        personInfoModel.setAddress(personModel.getAddress());
        personInfoModel.setEmail(personModel.getEmail());
        personInfoModel.setAge(age);
        personInfoModel.setMedications(medicalRecordEntity.getMedications());
        personInfoModel.setAllergies(medicalRecordEntity.getAllergies());
        return personInfoModel;
    }
}
