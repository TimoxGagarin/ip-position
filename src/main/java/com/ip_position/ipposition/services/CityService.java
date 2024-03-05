package com.ip_position.ipposition.services;

import java.util.Optional;
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

    public City addNewCity(City city) {
        logger.log(Level.INFO, "{0} was added into table City", city.toString().replaceAll("[\n\r]", "_"));
        Optional<City> existingCity = cityRepository.findCityByAll(city);
        if (existingCity.isEmpty()) {
            cityRepository.save(city);
            return city;
        }
        return existingCity.get();
    }

    public City findCityByName(String cityName) {
        Optional<City> city = cityRepository.findCityByCityName(cityName);
        if (city.isPresent())
            return city.get();
        return null;
    }

    public void deleteCity(Long cityId) {
        boolean hasReferences = cityRepository.hasReferences(cityId);

        if (hasReferences) {
            throw new IllegalStateException("City with id " + cityId + " does not exists or has references");
        }
        cityRepository.clearRelations(cityId);
        cityRepository.deleteById(cityId);
        logger.log(Level.INFO, "City with id={0} was deleted from table City", cityId);
    }
}