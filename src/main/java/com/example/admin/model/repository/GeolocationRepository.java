package com.example.admin.model.repository;

import com.example.admin.model.City;
import com.example.admin.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GeolocationRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Country> findAllCountries() {
        return jdbcTemplate.query("Select * from Countries order by name",
                new Object[]{},
                new BeanPropertyRowMapper<>(Country.class));
    }
    public List<City> findAllCities() {
        return jdbcTemplate.query("Select * from cities order by name",
                new Object[]{},
                new BeanPropertyRowMapper<>(City.class));
    }
}
