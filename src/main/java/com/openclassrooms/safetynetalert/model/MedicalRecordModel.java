package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordModel {

    private String lastName;
    private String firstName;
    private String birthdate;
    private List<String> medications ;
    private List<String> allergies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecordModel that = (MedicalRecordModel) o;
        return Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, birthdate, medications, allergies);
    }
}
