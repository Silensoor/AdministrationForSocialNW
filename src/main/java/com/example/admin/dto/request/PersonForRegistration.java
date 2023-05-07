package com.example.admin.dto.request;

import lombok.Data;

@Data
public class PersonForRegistration {
    private String code;


    private String codeSecret;


    private String email;


    private String firstName;


    private String lastName;


    private String passwd;



}
