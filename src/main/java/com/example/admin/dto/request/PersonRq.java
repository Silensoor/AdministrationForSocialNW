package com.example.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.sql.Date;
import java.sql.Timestamp;


@Data
public class PersonRq {

    private Long id;
    private String about;

    private String birthDate;

    private String changePasswordToken;

    private Integer configurationCode;

    private Timestamp deletedTime;

    private String email;

    private String firstName;

    private Boolean isApproved;

    private Boolean isBlocked;

    private Boolean isDeleted;

    private String lastName;


    private String lastOnlineTime;

    private String messagePermissions;

    private String notificationsSessionId;

    private String onlineStatus;

    private String password;

    private String phone;

    private String photo;


    private String regDate;

    private String city;

    private String country;

    private Integer telegramId;

    private Long personSettingsId;

    private Boolean isPhoto;

    private Integer count;


}