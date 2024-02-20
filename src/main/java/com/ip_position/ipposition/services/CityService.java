package com.ip_position.ipposition.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ip_position.ipposition.objects.City;
import com.ip_position.ipposition.repositories.CityRepository;

// Service annotation indicating that this class is a Spring service
@Service
public class CityService {
    // Dependency injection of CityRepository and Logger
    private final CityRepository cityRepository;
    private Logger logger;

    // Constructor-based dependency injection
    public CityService(CityRepository cityRepository, Logger logger) {
        this.cityRepository = cityRepository;
        this.logger = logger;
    }

    // Method to add a new City to the database
    public void addNewCity(City city) {
        // Log information about the added City
        logger.log(Level.INFO, "{0} was added into table City", city);

        // Save the City using the injected CityRepository
        cityRepository.save(city);
    }
}