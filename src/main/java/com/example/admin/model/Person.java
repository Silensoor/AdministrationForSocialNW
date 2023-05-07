package com.example.admin.model;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Person {
    private Long id;

    private String about;

    private Timestamp birthDate;

    private String changePasswordToken;

    private Integer configurationCode;

    private Timestamp deletedTime;

    private String email;

    private String firstName;

    private Boolean isApproved;

    private Boolean isBlocked;

    private Boolean isDeleted;

    private String lastName;

    private Timestamp lastOnlineTime;

    private String messagePermissions;

    private String notificationsSessionId;

    private String onlineStatus;

    private String password;

    private String phone;

    private String photo;

    private Timestamp regDate;

    private String city;

    private String country;

    private Long telegramId;

    private Long personSettingsId;



}
