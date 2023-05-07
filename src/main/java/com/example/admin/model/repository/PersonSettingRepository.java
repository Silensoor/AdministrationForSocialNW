package com.example.admin.model.repository;

import com.example.admin.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PersonSettingRepository {
    private final JdbcTemplate jdbcTemplate;

    public void createPersonSetting(Long id) {

        jdbcTemplate.update("insert into person_settings " +
                "(id,comment_comment, friend_birthday, friend_request, " +
                "post_like, message, post_comment, post) " +
                "values (" + id + ",true, true, true, true, true, true, true)");

    }

    public void deleteSettingByPersonId(Long id) {
        jdbcTemplate.update("delete from person_settings where id = ?", id);
    }
}