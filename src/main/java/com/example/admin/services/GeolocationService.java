package com.example.admin.services;

import com.example.admin.model.City;
import com.example.admin.model.Country;

import java.util.List;

public interface GeolocationService {
    List<Country> getAllCountry();
    List<City> getAllCity();
}
