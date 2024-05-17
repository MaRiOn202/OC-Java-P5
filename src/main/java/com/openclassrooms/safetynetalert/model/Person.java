package com.openclassrooms.safetynetalert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class Person implements Serializable {

/*    @Serial
    private static final long serialVersionUID = 1L;*/

        private String firstName;
        private String lastName;
        private String address;
        private String city;
        private String zip;
        private String phone;
        private String email;

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;

    }
    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
