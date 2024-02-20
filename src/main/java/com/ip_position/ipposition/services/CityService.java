package com.ip_position.ipposition.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.repositories.CityRepository;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private Logger logger;

    public CityService(CityRepository cityRepository, Logger logger) {
        this.cityRepository = cityRepository;
        this.logger = logger;
    }

    public void addNewCity(City city) {
        logger.log(Level.INFO, "{0} was added into table City", city);
        cityRepository.save(city);
    }
}