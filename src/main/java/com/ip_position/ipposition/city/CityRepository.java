package com.ip_position.ipposition.city;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Repository annotation indicating that this interface is a Spring Data repository
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    // Custom query method using JPQL to find a City by its cityName, regionName,
    // and country
    @Query("SELECT c FROM City c WHERE c.cityName = ?1 AND c.regionName = ?2 AND c.country = ?3")
    Optional<City> findCityByCityRegionCountry(String cityName, String regionName, String country);
}