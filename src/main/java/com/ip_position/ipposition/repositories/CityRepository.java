package com.ip_position.ipposition.repositories;

import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.entity.City;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.name = ?1")
    Optional<City> findCityByCityName(String cityName);

    @Query("SELECT c FROM City c WHERE c.country = :#{#city.country} " +
            "AND c.countryCode = :#{#city.countryCode} " +
            "AND c.region = :#{#city.region} " +
            "AND c.regionName = :#{#city.regionName} " +
            "AND c.name = :#{#city.name} " +
            "AND c.zip = :#{#city.zip}")
    Optional<City> findCityByAll(@Param("city") City city);

    @Query("SELECT COUNT(r) > 0 FROM IpInfo r WHERE r.city.id = ?1")
    boolean hasReferences(Long cityId);

    @Modifying
    @Query(value = "DELETE FROM city_provider_relation WHERE city_id = ?1", nativeQuery = true)
    void clearRelations(Long cityId);
}