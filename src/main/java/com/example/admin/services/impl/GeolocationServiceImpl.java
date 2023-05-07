package com.example.admin.services.impl;

import com.example.admin.model.City;
import com.example.admin.model.Country;
import com.example.admin.model.repository.GeolocationRepository;
import com.example.admin.services.GeolocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {
    private final GeolocationRepository geolocationRepository;
    @Override
    public List<Country> getAllCountry() {
        return geolocationRepository.findAllCountries();
    }

    @Override
    public List<City> getAllCity() {
        return geolocationRepository.findAllCities();
    }
}
