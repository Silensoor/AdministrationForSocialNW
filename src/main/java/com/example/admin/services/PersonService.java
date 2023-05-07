package com.example.admin.services;

import com.example.admin.dto.request.PersonRq;
import com.example.admin.model.Person;

import java.text.ParseException;
import java.util.List;

public interface PersonService {

    List<Person> getListPerson();
    void createPersonBySettings(PersonRq personRq) ;
    void createPerson();
    List<Person>getAllPerson();
    void delete(Long id);
    void registerUser();
}
