package com.openclassrooms.safetynetalert.model;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonModel {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;


    // égalité entre deux objets créés pour les besoins de l'appli 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonModel that = (PersonModel) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phone, address, city, zip, email);
    }
}
