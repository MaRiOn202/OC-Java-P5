package com.openclassrooms.safetynetalert.mapper;


import com.openclassrooms.safetynetalert.entity.MedicalRecordEntity;
import com.openclassrooms.safetynetalert.entity.PersonEntity;
import com.openclassrooms.safetynetalert.model.PersonCovertModel;
import com.openclassrooms.safetynetalert.model.PersonInfoModel;
import com.openclassrooms.safetynetalert.model.PersonModel;


public class PersonMapper {

    public PersonCovertModel mapToPersonCovertModel(PersonEntity personEntity) {
        PersonCovertModel personCovertModel = new PersonCovertModel();
        personCovertModel.setFirstName(personEntity.getFirstName());
        personCovertModel.setLastName(personEntity.getLastName());
        personCovertModel.setPhone(personEntity.getPhone());
        personCovertModel.setAddress(personEntity.getAddress());
        return personCovertModel;

    }

    public PersonInfoModel mapToPersonInfoModel(PersonEntity personEntity, MedicalRecordEntity medicalRecordEntity) {
        PersonInfoModel personInfoModel = new PersonInfoModel();
        personInfoModel.setFirstName(personEntity.getFirstName());
        personInfoModel.setLastName(personEntity.getLastName());
        personInfoModel.setAddress(personEntity.getAddress());
        personInfoModel.setEmail(personEntity.getEmail());
        personInfoModel.setAge(Long.parseLong(medicalRecordEntity.getBirthdate()));
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


    //tomapentity
    public PersonEntity mapToPersonEntity(PersonModel personModel) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setAddress(personModel.getAddress());
        personEntity.setCity(personModel.getCity());
        personEntity.setPhone(personModel.getPhone());
        personEntity.setEmail(personModel.getEmail());
        personEntity.setZip(personModel.getZip());
        return personEntity;
    }
}
