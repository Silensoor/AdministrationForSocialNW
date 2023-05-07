package com.example.admin.mappers;

import com.example.admin.dto.request.PersonRq;
import com.example.admin.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "about", defaultValue = "Hallo")
    @Mapping(target = "birthDate", defaultValue = "2023-05-01 10:59:57")
    @Mapping(target = "changePasswordToken", defaultValue = "12345678")
    @Mapping(target = "configurationCode", defaultValue = "12345678")
    @Mapping(target = "deletedTime", defaultValue = "2023-05-01 10:59:57")
    @Mapping(target = "firstName", defaultValue = "Vasiliy")
    @Mapping(target = "isApproved", defaultValue = "true")
    @Mapping(target = "isBlocked", defaultValue = "false")
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "lastName", defaultValue = "Petrov")
    @Mapping(target = "lastOnlineTime", defaultValue = "2023-05-01 10:59:57")
    @Mapping(target = "messagePermissions", defaultValue = "12345678")
    @Mapping(target = "notificationsSessionId", defaultValue = "12345678")
    @Mapping(target = "onlineStatus", defaultValue = "OFFLINE")
    @Mapping(target = "password", defaultValue = "$2a$10$DKfACXByOkjee4VELDw7R.BeslHcGeeLbCK2N8gV3.BaYjSClnObG")
    @Mapping(target = "phone", defaultValue = "222-222-2222")
    @Mapping(target = "photo", defaultValue = "12345678")
    @Mapping(target = "regDate", defaultValue = "2023-05-01 10:59:57")
    @Mapping(target = "city", defaultValue = "Penza")
    @Mapping(target = "country", defaultValue = "Russia")
    @Mapping(target = "telegramId", defaultValue = "12345678L")
    Person toDTO(PersonRq personRq);


    default Timestamp map(String value) {
        return Timestamp.valueOf(value);
    }
}
