package com.ip_position.ipposition.services;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.repositories.CityRepository;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final Map<String, List<City>> cacheMap;
    private final Logger logger;

    public CityService(CityRepository cityRepository, Map<String, List<City>> cacheMap, Logger logger) {
        this.cityRepository = cityRepository;
        this.cacheMap = cacheMap;
        this.logger = logger;
    }

    public City addNewCity(City city) {
        List<City> cityList = cityRepository.findCity(city);
        if (cityList.isEmpty()) {
            cacheMap.clear();
            cityRepository.save(city);
            return city;
        }
        return cityList.get(0);
    }

    public List<City> findCities(City city) {
        String cacheKey = CacheConfig.CITY_CACHE_START + city.toString();
        if (cacheMap.containsKey(cacheKey)) {
            logger.info(() -> String.format("Cache %s value:%n%s", cacheKey, cacheMap.get(cacheKey).toString()));
            return cacheMap.get(cacheKey);
        }
        List<City> result = cityRepository.findCity(city);
        cacheMap.put(cacheKey, result);
        return result;
    }

    public void deleteCity(@NonNull Long cityId) {
        boolean hasReferences = cityRepository.hasReferences(cityId);

        if (hasReferences) {
            throw new IllegalStateException("City with id " + cityId + " does not exists or has references");
        }

        cacheMap.clear();
        cityRepository.clearRelations(cityId);
        cityRepository.deleteById(cityId);
    }
}