package com.ip_position.ipposition.repositories;

import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.entity.City;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.cityName = ?1 AND c.regionName = ?2 AND c.country = ?3")
    Optional<City> findCityByCityRegionCountry(String cityName, String regionName, String country);
}