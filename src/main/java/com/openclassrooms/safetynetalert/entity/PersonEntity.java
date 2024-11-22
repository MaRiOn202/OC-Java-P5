package com.openclassrooms.safetynetalert.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@ToString
public class PersonEntity implements Serializable {

        private String firstName;
        private String lastName;
        private String address;
        private String city;
        private String zip;
        private String phone;
        private String email;


    public PersonEntity() {

    }
}
