package com.ip_position.ipposition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.repositories.CityRepository;
import com.ip_position.ipposition.services.CityService;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    private Map<String, List<City>> cacheMap;
    private Logger logger;

    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheMap = new HashMap<>();
        logger = Logger.getLogger(CityService.class.getName());
        cityService = new CityService(cityRepository, cacheMap, logger);
    }

    @Test
    void addNewCity_Success() {
        City cityToAdd = new City("Singapore",
                "SG",
                "01",
                "Central Singapore",
                "Singapore",
                "048582");

        when(cityRepository.findCity(cityToAdd)).thenReturn(new ArrayList<>());
        when(cityRepository.save(cityToAdd)).thenReturn(cityToAdd);

        City addedCity = cityService.addNewCity(cityToAdd);

        assertEquals(cityToAdd, addedCity);
        verify(cityRepository, times(1)).findCity(cityToAdd);
        verify(cityRepository, times(1)).save(cityToAdd);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void findCities_CacheHit() {
        City cityToFind = new City("Singapore",
                "SG",
                "01",
                "Central Singapore",
                "Singapore",
                "048582");
        List<City> cachedCities = new ArrayList<>();
        cachedCities.add(new City("Singapore",
                "SG",
                "01",
                "Central Singapore",
                "Singapore",
                "048582"));

        String cacheKey = CacheConfig.CITY_CACHE_START + cityToFind.toString();
        cacheMap.put(cacheKey, cachedCities);

        List<City> foundCities = cityService.findCities(cityToFind);

        assertEquals(cachedCities, foundCities);
        assertEquals(cachedCities, cacheMap.get(cacheKey));
        verify(cityRepository, never()).findCity(cityToFind);
    }

    @Test
    void deleteCity_Success() {
        Long cityId = 1L;

        when(cityRepository.hasReferences(cityId)).thenReturn(false);

        cityService.deleteCity(cityId);

        verify(cityRepository, times(1)).clearRelations(cityId);
        verify(cityRepository, times(1)).deleteById(cityId);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void deleteCity_WithReferences_ExceptionThrown() {
        Long cityId = 1L;

        when(cityRepository.hasReferences(cityId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> cityService.deleteCity(cityId));

        verify(cityRepository, never()).clearRelations(cityId);
        verify(cityRepository, never()).deleteById(cityId);
        assertTrue(cacheMap.isEmpty());
    }
}