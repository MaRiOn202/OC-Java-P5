package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    private List<Person> listPerson = new ArrayList<>();


    // URL n°2 : http://localhost:8080/childAlert?address=<address>

  //  public List<>


    







    



}
